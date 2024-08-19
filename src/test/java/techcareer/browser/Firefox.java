package techcareer.browser;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;
import techcareer.configuration.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class Firefox {
    protected static final Logger logger = LogManager.getLogger(Firefox.class);
    private static Firefox instance;
    private final FirefoxOptions options = new FirefoxOptions();
    @Getter
    private GeckoDriverService geckoDriverService;

    private Firefox() {

    }

    public static Firefox getInstance() {
        if (instance == null) {
            instance = new Firefox();
        }
        return instance;
    }

    public RemoteWebDriver getFirefoxBrowser() throws IOException {
        geckoDriverService = new GeckoDriverService.Builder()
                .usingDriverExecutable(getFirefoxDriverPath())
                .usingAnyFreePort()
                .build();
        geckoDriverService.start();
        logger.info("Browser firefox olarak açılıyor...");

        return new RemoteWebDriver(geckoDriverService.getUrl(), getOptions());
    }

    private File getFirefoxDriverPath() throws NoSuchFileException {
        File geckoPath = null;
        if (Configuration.getInstance().getOsType().contains("windows")) {
            geckoPath = new File("src/test/resources/drivers/geckodriver.exe");
        } else if (Configuration.getInstance().getOsType().contains("mac")
                || Configuration.getInstance().getOsType().equalsIgnoreCase("unix")) {
            geckoPath = new File("src/test/resources/drivers/geckodriver");
        }
        if (geckoPath.exists()) {
            return geckoPath;
        } else {
            throw new NoSuchFileException("Gecko driver seçilememiştir, bunun sebebi dosyanın olmaması olabilir.\nResources klasörü altına driverınızın ismi Windows için :\"geckodriver.exe\" Mac ve Linux için: \"geckodriver\" olacak şekilde atınız.");
        }
    }

    private Capabilities getOptions() {
        options.addArguments("--kiosk");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-fullscreen");
        //options.setBrowserVersion("LATEST");
        return options;
    }

}
