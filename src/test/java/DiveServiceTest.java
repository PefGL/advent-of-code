import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class DiveServiceTest {

    private final DiveService diveService = new DiveService();

    @Test
    public void position_retourne_0_quand_fichier_vide() throws IOException {
        int attendu = 0;
        File inputs = TestUtil.construireInput("");

        int position = diveService.recupererPosition(inputs);

        Assert.assertEquals(attendu, position);
    }

    @Test
    public void position_retourne_0_quand_profondeur_1_et_horizontal_0() throws IOException {
        int attendu = 0;
        File inputs = TestUtil.construireInput("forward 0","down 1");

        int position = diveService.recupererPosition(inputs);

        Assert.assertEquals(attendu, position);
    }

    @Test
    public void position_retourne_1_quand_profondeur_1_et_horizontal_1() throws IOException {
        int attendu = 1;
        File inputs = TestUtil.construireInput("forward 1","down 1");

        int position = diveService.recupererPosition(inputs);

        Assert.assertEquals(attendu, position);
    }
}