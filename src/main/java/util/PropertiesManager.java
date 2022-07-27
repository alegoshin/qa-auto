package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {

    public static Properties getProperty(String propertiesFileName) {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertiesManager.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            properties.load(inputStream);
            return properties;
        } catch (IOException ioe) {
            throw new RuntimeException("Unable to load property file: " + propertiesFileName + ".\n" + ioe.getMessage());
        }
    }
}
