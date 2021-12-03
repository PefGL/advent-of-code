import java.io.IOException;
import java.net.URISyntaxException;

public class AdventApp {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Utils utils
             = new Utils();
        SonarService sonarService = new SonarService();
        int sortieSonar = sonarService.calculerNombreMesuresQuiAugmentent(utils.lireFichier("input_sonar"));
        System.out.println(sortieSonar);


        DiveService diveService = new DiveService();
        int sortieDive = diveService.calculerValeurPlongee(utils.lireFichier("input_dive"));
        System.out.println(sortieDive);

        BinaryDiagnosticService binaryDiagnosticService = new BinaryDiagnosticService();
        int consommation = binaryDiagnosticService.calculerConsommation(utils.lireFichier("input_consommation"));
        System.out.println(consommation);
    }
}
