import java.io.IOException;
import java.net.URISyntaxException;

public class AdventApp {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Metier day1 = new Metier();
        int sortie = day1.nombreMesuresQuiAugmentent(day1.lireFichier("input"));
        System.out.println(sortie);
    }
}
