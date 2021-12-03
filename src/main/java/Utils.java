import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Utils {

    public File lireFichier(final String nomFichier) throws URISyntaxException {
        URL res = getClass().getClassLoader().getResource(nomFichier);
        return Paths.get(res.toURI()).toFile();
    }
}
