import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

public class BinaryDiagnosticServiceTest {

    private BinaryDiagnosticService binaryDiagnosticService = new BinaryDiagnosticService();

    @Test
    public void position_retourne_198_quand_00100_11110_10110_10111_10101_01111_00111_11100_10000_11001_00010() throws IOException, URISyntaxException {
        int consommationAttendue = 198;
        File inputs = TestUtil.construireInput("00100", "11110", "10110", "10111", "10101", "01111", "00111", "11100", "10000", "11001", "00010",
            "01010");

        int consommationObtenue = binaryDiagnosticService.calculerConsommation(inputs);

        Assert.assertEquals(consommationAttendue,consommationObtenue );
    }
}