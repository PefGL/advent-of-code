import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

public class SonarServiceTest {

    private final SonarService sonarService = new SonarService();

    @Test
    public void retourne_1_si_fichier_avec_1_2_3_4() throws IOException {
        Integer attendu = 1;
        File input = TestUtil.construireInput("1", "2", "3", "4");

        Integer obtenu = sonarService.calculerNombreMesuresQuiAugmentent(input);

        assertEquals(attendu, obtenu);
    }

    @Test
    public void retourne_2_si_fichier_avec_1_2_3_4_5() throws IOException {
        Integer attendu = 2;
        File input = TestUtil.construireInput("1", "2", "3", "4", "5");

        Integer obtenu = sonarService.calculerNombreMesuresQuiAugmentent(input);

        assertEquals(attendu, obtenu);
    }

    @Test
    public void retourne_1_si_fichier_avec_1_2_3_4_1() throws IOException {
        Integer attendu = 1;
        File input = TestUtil.construireInput("1", "2", "3", "4", "1");

        Integer obtenu = sonarService.calculerNombreMesuresQuiAugmentent(input);

        assertEquals(attendu, obtenu);
    }

    @Test
    public void retourne_0_fichier_avec_1_1_1_1() throws IOException {
        Integer attendu = 0;
        File input = TestUtil.construireInput("1", "1", "1", "1");

        Integer obtenu = sonarService.calculerNombreMesuresQuiAugmentent(input);

        assertEquals(attendu, obtenu);
    }

    @Test
    public void retourne_0_fichier_avec_1() throws IOException {
        Integer attendu = 0;
        File input = TestUtil.construireInput("1");

        Integer obtenu = sonarService.calculerNombreMesuresQuiAugmentent(input);

        assertEquals(attendu, obtenu);
    }

    @Test
    public void lecture_fichier() {
        try {
            sonarService.lireFichier("input_test");
        } catch (URISyntaxException e) {
            Assert.fail();
        }
    }


}
