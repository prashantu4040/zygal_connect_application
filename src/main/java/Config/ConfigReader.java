package Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            // Load the config.properties file during class initialization
            FileInputStream file = new FileInputStream("src/main/resources/config.properties");
            properties = new Properties();
            properties.load(file); // Load properties from the file
            file.close(); // Close the file input stream
        } catch (IOException e) {
            e.printStackTrace();
            // Throw a runtime exception if the config file fails to load
            throw new RuntimeException("Failed to load config.properties file.");
        }
    }

    /**
     * Retrieves the value of a property from the config.properties file.
     * 
     * @param key The property key whose value needs to be fetched.
     * @return The value corresponding to the provided key.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
