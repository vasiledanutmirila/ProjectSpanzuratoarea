package pa.project.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SpanzuratoareaLogger {

    public Logger logger;
    public FileHandler fileHandler;

    public SpanzuratoareaLogger(String file_name) throws IOException {
        File file = new File(file_name);
        if(!file.exists()) {
            file.createNewFile();
        }

        fileHandler = new FileHandler(file_name, true);
        logger = Logger.getLogger("spanzuratoarea");
        logger.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
    }
}
