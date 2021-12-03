import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtil {

    public static File construireInput(CharSequence... chiffres) throws IOException {
        File input = new File("temp");
        Files.write(Paths.get(input.getName()), String.join("\n", chiffres).getBytes());
        return input;
    }
}
