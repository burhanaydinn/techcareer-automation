package techcareer.base;

import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    private static Stopwatch stopwatch;

    @BeforeEach
    public void setup() throws IOException {
        try {
            stopwatch = Stopwatch.createStarted();
            BrowserManager.getInstance().createDriver();
        } catch (Exception e) {
            throw e;
        }
    }

    @AfterEach
    public void tearDown() {
        if (BrowserManager.getInstance().getWebDriver() != null) {
            BrowserManager.getInstance().getWebDriver().quit();
            BrowserManager.getInstance().resetWebDriverWait();
            BrowserManager.getInstance().closeService();

            logger.info("Browser kapatıldı...Test sonlandırıldı.");
            logger.info("Test {} saniye sürmüştür.", stopwatch.elapsed(TimeUnit.SECONDS));
        }
    }
}
