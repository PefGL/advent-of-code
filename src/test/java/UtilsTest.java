import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    private final Utils utils = new Utils();

    @Test
    public void lecture_fichier() {
        try {
            utils.lireFichier("input_test");
        } catch (URISyntaxException e) {
            Assert.fail();
        }
    }
}
