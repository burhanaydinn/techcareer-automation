package techcareer.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import techcareer.base.BaseTest;
import techcareer.test.LoginPageTest;
import techcareer.utils.Methods;

public class LoginPage extends BaseTest {
    protected static final Logger logger = LogManager.getLogger(LoginPageTest.class);

    public LoginPage moveToLogin() {
        logger.info("Giriş ekranına gidiliyor.");
        Methods.getInstance().waitSeconds(1);
        Methods.getInstance().click("GirisYapDropdown");
        Methods.getInstance().waitSeconds(3);
        Methods.getInstance().click("GirisYap");
        return this;
    }

    public LoginPage enterEmail(String email) {
        Methods.getInstance().sendKeys("KullaniciAdi", email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        Methods.getInstance().sendKeys("Sifre", password);
        return this;
    }

    public LoginPage clickLogin() {
        Methods.getInstance().waitSeconds(2);
        Methods.getInstance().click("LoginButton");
        return this;
    }

    public LoginPage validateLogin() {
        Methods.getInstance().click("MyAccount");
        if (Methods.getInstance().isElementVisible("MyAccount")) {
            logger.info("Başarı ile giriş yapılmıştır.");
        } else {
            String msg = "Giriş başarılı bir şekilde yapılamamıştır. Test Sonlandırılıyor";
            logger.error(msg);
            Assertions.fail(msg);
        }
        return this;
    }

    public LoginPage validateNegativeLogin() {
        Methods.getInstance().click("MailAlert");
        Methods.getInstance().click("PasswordAlert");
        if (Methods.getInstance().isElementVisible("MailAlert") ||Methods.getInstance().isElementVisible("PasswordAlert")) {
            logger.info("Negatif login uyarıları başarı ile alınmıştır.");
        } else {
            String msg = "Negatif login uyarıları başarılı bir şekilde alınamamıştır. Test Sonlandırılıyor";
            logger.error(msg);
            Assertions.fail(msg);
        }
        return this;
    }
}
