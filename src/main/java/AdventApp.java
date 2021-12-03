import java.io.IOException;
import java.net.URISyntaxException;

public class AdventApp {

    public static void main(String[] args) throws IOException, URISyntaxException {
        SonarService day1 = new SonarService();
        int sortie = day1.calculerNombreMesuresQuiAugmentent(day1.lireFichier("input_sonar"));
        System.out.println(sortie);
    }
}
