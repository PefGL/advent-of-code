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
    public void position_retourne_900_quand_avancer_5_descendre_5_avancer_8_monter_3_descendre_8_avancer_2() throws IOException {
        int attendu = 900;
        File inputs = TestUtil.construireInput("forward 5","down 5","forward 8","up 3", "down 8", "forward 2");

        int position = diveService.recupererPosition(inputs);

        Assert.assertEquals(attendu, position);
    }









}