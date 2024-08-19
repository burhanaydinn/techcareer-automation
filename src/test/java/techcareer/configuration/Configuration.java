package techcareer.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Properties;


public class Configuration {
    protected static final Logger logger = LogManager.getLogger(Configuration.class);
    private static final String CONFIG_FILE_NAME = "src/test/resources/config.properties";
    private static Configuration instance;
    private Properties properties;
    private final String browserType = "browserType";

    private int timeoutSeconds;
    private int timeoutMillis;

    private Configuration() {
        configRead();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }


    private void configRead() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE_NAME))) {
            properties = new Properties();
            properties.load(reader);
        } catch (NoSuchFileException e) {
            logger.info("Properties dosyası bulunamadı. Path = {} ", CONFIG_FILE_NAME);
            logger.error("Hata Mesajı: {}", e.getMessage(), e);
            throw new RuntimeException("Properties dosyası bulunamadı.", e);
        } catch (IOException e) {
            throw new RuntimeException("Properties dosyası okunurken bir hata meydana gelmiştir.", e);
        }
    }

    public String getOsType() {
        final String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("mac") ? "mac" : osName.contains("win") ? "windows" : osName.contains("nix") || osName.contains("nux") || osName.contains("aix") ? "unix" : "Unknown";
    }

    public String getBaseUrl() {
        return getString("baseUrl");
    }

    public int getTimeoutSeconds() {
        return Integer.parseInt(getString("timeoutSeconds"));
    }

    public int getTimeoutMillis() {
        return Integer.parseInt(getString("timeoutMillis"));
    }

    public String getBrowserType(){
        return getString(browserType);

    }

    private String getString(String value) {
        try {
            return (String) properties.get(value);
        } catch (Exception e) {
            return "null";
        }
    }

    public String getElementsPath() {
        return getString("elementsPath");
    }
}
