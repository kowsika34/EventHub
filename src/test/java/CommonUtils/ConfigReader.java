package CommonUtils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    static Properties properties = new Properties();

    static {

        try {

            InputStream input =ConfigReader.class
                               .getClassLoader()
                               .getResourceAsStream("config.properties");

            properties.load(input);

        } catch (Exception e) {

            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getProperty(String key) {

        return properties.getProperty(key);
    }
}