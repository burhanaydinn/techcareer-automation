package techcareer.base;

import io.github.sukgu.Shadow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import techcareer.browser.BrowserType;
import techcareer.browser.Chrome;
import techcareer.browser.Firefox;
import techcareer.configuration.Configuration;

import java.io.IOException;
import java.time.Duration;

public class BrowserManager {
    protected static final Logger logger = LogManager.getLogger(BrowserManager.class);
    private static BrowserManager instance;
    private RemoteWebDriver remoteWebDriver;
    private WebDriverWait webDriverWait;

    private BrowserManager() {

    }

    public static BrowserManager getInstance() {
        if (instance == null) {
            instance = new BrowserManager();
        }
        return instance;
    }

    public void createChromeDriver() throws IOException {
        remoteWebDriver = Chrome.getInstance().getChromeBrowser();
        logger.info("Browser chrome olarak açıldı...");
        logger.info("{} adresine gidiliyor.", Configuration.getInstance().getBaseUrl());
        remoteWebDriver.navigate().to(Configuration.getInstance().getBaseUrl());
    }

    public void createFirefoxDriver() throws IOException {
        remoteWebDriver = Firefox.getInstance().getFirefoxBrowser();
        logger.info("Browser firefox olarak açıldı...");
        logger.info("{} adresine gidiliyor.", Configuration.getInstance().getBaseUrl());
        remoteWebDriver.navigate().to(Configuration.getInstance().getBaseUrl());
    }

    public void createDriver() throws IOException {
        if (Configuration.getInstance().getBrowserType().equalsIgnoreCase(BrowserType.CHROME.getBrowserName())) {
            createChromeDriver();
        } else if (Configuration.getInstance().getBrowserType().equalsIgnoreCase(BrowserType.FIREFOX.getBrowserName())) {
            createFirefoxDriver();
        } else {
            throw new IllegalArgumentException("Geçersiz tarayıcı tipi: " + Configuration.getInstance().getBrowserType());
        }
    }

    public RemoteWebDriver getWebDriver() {
        return remoteWebDriver;
    }

    public WebDriverWait getWebDriverWait() {
        if (webDriverWait == null) {
            webDriverWait = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(Configuration.getInstance().getTimeoutSeconds()));
            webDriverWait.withTimeout(Duration.ofSeconds(Configuration.getInstance().getTimeoutSeconds()))
                    .pollingEvery(Duration.ofMillis(Configuration.getInstance().getTimeoutMillis()))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class);
        }
        return webDriverWait;
    }

    public void resetWebDriverWait() {
        webDriverWait = null;
    }

    public Shadow getShadowDriver() {
        Shadow shadow = new Shadow(getWebDriver());
        try {
            shadow.setExplicitWait(Configuration.getInstance().getTimeoutSeconds(), 1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            Assertions.fail("Shadow driver oluşturulamadı.");
        }
        return shadow;
    }

    public void closeService() {
        if (Configuration.getInstance().getBrowserType().equalsIgnoreCase(BrowserType.CHROME.getBrowserName())) {
            if (Chrome.getInstance().getChromeDriverService().isRunning()) {
                Chrome.getInstance().getChromeDriverService().stop();
            }
        } else if (Configuration.getInstance().getBrowserType().equalsIgnoreCase(BrowserType.FIREFOX.getBrowserName())) {
            if (Firefox.getInstance().getGeckoDriverService().isRunning()) {
                Firefox.getInstance().getGeckoDriverService().stop();
            }
        }
    }
}
