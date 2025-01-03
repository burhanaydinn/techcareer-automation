package techcareer.browser;

import com.google.common.base.Stopwatch;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import techcareer.configuration.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class Chrome {
    protected static final Logger logger = LogManager.getLogger(Chrome.class);
    private static Chrome instance;
    private final ChromeOptions options = new ChromeOptions();
    @Getter
    private ChromeDriverService chromeDriverService;

    private Chrome() {

    }

    public static Chrome getInstance() {
        if (instance == null) {
            instance = new Chrome();
        }
        return instance;
    }

    public RemoteWebDriver getChromeBrowser() throws IOException {
        chromeDriverService = new ChromeDriverService.Builder()
                .usingDriverExecutable(getChromeDriverPath())
                .usingAnyFreePort()
                .build();
        chromeDriverService.start();
        logger.info("Browser chrome olarak açılıyor...");

        return new RemoteWebDriver(chromeDriverService.getUrl(), getOptions());
    }

    private File getChromeDriverPath() throws NoSuchFileException {
        File chromePath = null;
        if (Configuration.getInstance().getOsType().contains("windows")) {
            chromePath = new File("src/test/resources/drivers/chromedriver.exe");
        } else if (Configuration.getInstance().getOsType().contains("mac")
                || Configuration.getInstance().getOsType().equalsIgnoreCase("unix")) {
            chromePath = new File("src/test/resources/drivers/chromedriver");
        }
        if (chromePath.exists()) {
            return chromePath;
        } else {
            throw new NoSuchFileException("Chrome driver secilememistir, bunun sebebi dosyanin olmamasi olabilir.\nResources klasoru altina driverinizin ismi Windows için :\"chromedriver.exe\" Mac ve Linux icin: \"chromedriver\" olacak sekilde atiniz.");
        }
    }

    private Capabilities getOptions() {
        options.addArguments("test-type");
        options.addArguments("disable-popup-blocking");
        options.addArguments("disable-notifications");
        options.addArguments("ignore-certificate-errors");
        options.addArguments("disable-translate");
        options.addArguments("start-maximized");
        options.addArguments("disable-automatic-password-saving");
        options.addArguments("allow-silent-push");
        options.addArguments("disable-infobars");
        options.addArguments("--remote-allow-origins=*");
        options.setBrowserVersion("LATEST");
        return options;
    }

}
