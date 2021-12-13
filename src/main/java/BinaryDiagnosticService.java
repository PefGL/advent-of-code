import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryDiagnosticService {

    public int calculerConsommation(final File inputs) throws URISyntaxException, IOException {
        List<String> lignesRapport = recupererLignesRapport(inputs);
        List<ColonneRapport> colonneRapport = recupererColonnesRapport(lignesRapport);
        return calculerConsommation(recupererBitsLesPlusCommuns(colonneRapport), recupererBitsLesMoinsCommuns(colonneRapport));
    }

    public int calculerLifeSuppportRating(final File inputs) throws IOException {
        List<String> lignesRapport = recupererLignesRapport(inputs);
        return calculerOxygenGeneratorRating(lignesRapport, 0) * calculerCo2ScrubberRating(lignesRapport, 0);
    }

    private int calculerOxygenGeneratorRating(final List<String> lignesRapport, final Integer indiceColonne) {
        return calculerRating(lignesRapport, indiceColonne, "OXYGEN");
    }

    private int calculerCo2ScrubberRating(final List<String> lignesRapport, final Integer indiceColonne) {
        return calculerRating(lignesRapport, indiceColonne, "CO2");
    }

    private int calculerRating(final List<String> lignesRapport, final Integer indiceColonne, String type) {
        List<ColonneRapport> colonneRapports = recupererColonnesRapport(lignesRapport);
        List<String> nouvellesLignes = lignesRapport.stream()//
            .filter(ligne -> LectureRapportStrategy.of(type).apply(ligne, colonneRapports, indiceColonne))////
            .toList();
        if (nouvellesLignes.size() > 1) {
            return calculerRating(nouvellesLignes, indiceColonne + 1, type);
        }
        return Integer.parseInt(nouvellesLignes.get(0), 2);
    }

    private List<ColonneRapport> recupererColonnesRapport(final List<String> lignesRapport) {
        int nombreColonnes = recupererNombreColonnes(lignesRapport);
        List<ColonneRapport> colonnesRapport = new ArrayList<>();
        for (int indiceDansLigneRapport = 0; indiceDansLigneRapport < nombreColonnes; indiceDansLigneRapport++) {
            int nombreBits0 = 0;
            int nombreBits1 = 0;
            for (String ligneRapport : lignesRapport) {
                if (ligneRapport.charAt(indiceDansLigneRapport) == '1') {
                    nombreBits1++;
                } else {
                    nombreBits0++;
                }
            }
            colonnesRapport.add(new ColonneRapport(indiceDansLigneRapport, nombreBits0, nombreBits1));
        }
        return colonnesRapport;
    }

    private int recupererNombreColonnes(final List<String> lignesRapport) {
        return lignesRapport.get(0).length();
    }

    private String recupererBitsLesMoinsCommuns(final List<ColonneRapport> colonneRapport) {
        return colonneRapport.stream().map(ColonneRapport::bitLeMoinsCommun).collect(Collectors.joining(""));
    }

    private String recupererBitsLesPlusCommuns(final List<ColonneRapport> colonneRapport) {
        return colonneRapport.stream().map(ColonneRapport::bitLePlusCommun).collect(Collectors.joining(""));
    }

    private int calculerConsommation(final String bitsLesPlusCommuns, final String bitsLesMoinsCommuns) {
        return Integer.parseInt(bitsLesPlusCommuns, 2) * Integer.parseInt(bitsLesMoinsCommuns, 2);
    }

    private List<String> recupererLignesRapport(final File inputs) throws IOException {
        return Files.lines(Paths.get(inputs.toURI()))//
            .map(String::toString)//
            .toList();
    }

    private record ColonneRapport(int indice, int nombreBit0, int nombreBit1) {

        public String bitLePlusCommun() {
            if (nombreBit0 > nombreBit1) {
                return "0";
            }
            return "1";
        }

        public boolean bitsPlusEtMoinsCommunsEgaux(){
            return bitLeMoinsCommun().equals(bitLePlusCommun());
        }

        public String bitLeMoinsCommun() {
            if (nombreBit0 > nombreBit1) {
                return "1";
            }
            return "0";
        }
    }

    private enum LectureRapportStrategy {
        OXYGEN("OXYGEN", (ligne, colonnesRapport, indiceColonne) ->colonnesRapport.get(indiceColonne).bitsPlusEtMoinsCommunsEgaux() ? ligne.startsWith("1") : colonnesRapport.get(indiceColonne)
            .bitLePlusCommun().equals(String.valueOf(ligne.charAt(indiceColonne)))), //
        CO2("CO2", (ligne, colonnesRapport, indiceColonne) -> colonnesRapport.get(indiceColonne).bitLePlusCommun()
            .equals(colonnesRapport.get(indiceColonne).bitLeMoinsCommun()) ? ligne.startsWith("0") : colonnesRapport.get(indiceColonne)
            .bitLeMoinsCommun().equals(String.valueOf(ligne.charAt(indiceColonne))));

        private final String type;
        private final TriFunction<String, List<ColonneRapport>, Integer, Boolean> function;

        LectureRapportStrategy(final String type, final TriFunction<String, List<ColonneRapport>, Integer, Boolean> function) {
            this.type = type;
            this.function = function;
        }

        public static LectureRapportStrategy of(final String type) {
            return Arrays.stream(LectureRapportStrategy.values())//
                .filter(lectureRapportStrategy -> lectureRapportStrategy.type.equals(type))//
                .findFirst()//
                .orElseThrow();
        }

        public boolean apply(final String ligne, final List<ColonneRapport> colonnesRapport, Integer indiceColonne) {
            return function.apply(ligne, colonnesRapport, indiceColonne);
        }
    }

    @FunctionalInterface
    public interface TriFunction<T, U, V, R> {
        R apply(T premierArgument, U secondArguement, V troisiemeArgument);
    }
}
