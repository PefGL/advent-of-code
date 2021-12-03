import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryDiagnosticService {

    public int calculerConsommation(final File inputs) throws URISyntaxException, IOException {
        List<String> lignesRapport = recupererLignesRapport(inputs);
        List<ColonneRapport> colonneRapport = recupererColonnesRapport(lignesRapport);
        return calculerConsommation(recupererBitsLesPlusCommuns(colonneRapport), recupererBitsLesMoinsCommuns(colonneRapport));
    }

    private List<ColonneRapport> recupererColonnesRapport(final List<String> lignesRapport) {
        int tailleRapport = lignesRapport.get(0).length();
        List<ColonneRapport> colonnesRapport = new ArrayList<>();
        for (int indiceDansLigneRapport = 0; indiceDansLigneRapport < tailleRapport; indiceDansLigneRapport++) {
            int nombreBits0 = 0;
            int nombreBits1 = 0;
            for (String ligneRapport : lignesRapport) {
                if (ligneRapport.charAt(indiceDansLigneRapport) == '1') {
                    nombreBits0++;
                } else {
                    nombreBits1++;
                }
            }
            colonnesRapport.add(new ColonneRapport(indiceDansLigneRapport, nombreBits0, nombreBits1));
        }
        return colonnesRapport;
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

        public String bitLeMoinsCommun() {
            if (nombreBit0 > nombreBit1) {
                return "1";
            }
            return "0";
        }
    }
}
