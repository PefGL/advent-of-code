import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SonarService {

    public int calculerNombreMesuresQuiAugmentent(final File inputs) throws IOException {
        List<Integer> chiffresInput = recupererListeDEntiersDepuisFichier(inputs);
        return compterPlagesDeChiffresQuiAugmentent(chiffresInput);
    }

    private int compterPlagesDeChiffresQuiAugmentent(final List<Integer> chiffresInput) {
        int compteurPlageQuiAugmente = -1;
        int precedenteValeurDePlage = -1;
        for (int indexCourant = 0; indexCourant < chiffresInput.size(); indexCourant++) {
            int valeurDePlage = recupererValeurDePlageCourante(indexCourant, chiffresInput);
            if (estUnePlageQuiAugmente(precedenteValeurDePlage, valeurDePlage)) {
                compteurPlageQuiAugmente++;
            }
            precedenteValeurDePlage = valeurDePlage;
        }
        return compteurPlageQuiAugmente == -1 ? 0 : compteurPlageQuiAugmente;
    }

    private boolean estUnePlageQuiAugmente(final int precedenteValeurDePlage, final int valeurDePlage) {
        return valeurDePlage != -1 && precedenteValeurDePlage < valeurDePlage;
    }

    private int recupererValeurDePlageCourante(final int indexCourant, final List<Integer> chiffresInput) {
        List<Integer> sousListePourPlage = chiffresInput.subList(indexCourant, chiffresInput.size());
        if (sousListePourPlage.size() < 3) {
            return -1;
        }
        return chiffresInput.subList(indexCourant, indexCourant + 3).stream()//
            .reduce(0, Integer::sum);
    }

    private List<Integer> recupererListeDEntiersDepuisFichier(final File inputs) throws IOException {
        return Files.lines(Paths.get(inputs.toURI()))//
            .map(Integer::parseInt)//
            .toList();
    }
}
