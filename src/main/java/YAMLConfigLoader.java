
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;


import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class YAMLConfigLoader {

    protected static Logger logger = Logger.getLogger("YAMLConfigLoader");

    static Map<String, String> ymlPropertiesMap = null;

    static {
        loadYaml();
    }

    public static void loadYaml() {

        logger.info("Loading Yaml file on startup");
        final String fileName = "./src/main/resources/config.yml";

        Yaml yaml = new Yaml();

        try {
            InputStream ios = new FileInputStream(new File(fileName));
            ymlPropertiesMap = (Map<String, String>) yaml.load(ios);

        } catch (Exception e) {
            logger.error("Error while loading yaml file" + ExceptionUtils.getMessage(e));
        }

    }

    public static String getProperty(String property) {
        return ymlPropertiesMap.get(property);

    }

}