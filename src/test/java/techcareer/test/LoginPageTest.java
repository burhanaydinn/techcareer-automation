package techcareer.test;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import techcareer.base.BaseTest;
import techcareer.page.LoginPage;
import techcareer.utils.CsvHelper;
import techcareer.utils.ExcelHelper;
@DisplayName("Cimri.com Login Page Tests")
@Owner("Burhan Aydın")
public class LoginPageTest extends BaseTest {
    LoginPage loginPage;

    public LoginPageTest() {
        loginPage = new LoginPage();
    }

    @Test
    @DisplayName("PozitifLoginSenaryosu")
    @Description("Pozitif login senaryosu")
    public void positiveLoginTest() {
        loginPage.moveToLogin()
                .enterEmail(CsvHelper.getInstance().getValueWithRowAndColumn("cimri.csv", 0, 1,true))
                .enterPassword(CsvHelper.getInstance().getValueWithRowAndColumn("cimri.csv", 0, 2,true))
                .clickLogin()
                .validateLogin();
    }

    @Test
    @DisplayName("NegatifLoginSenaryosu")
    @Description("Negatif login senaryosu")
    public void negativeLoginTest() {
        loginPage.moveToLogin()
                .enterEmail(ExcelHelper.getInstance().getValueFromCell("cimri.xlsx", 1, 0,true))
                .clickLogin()
                .validateNegativeLogin();
    }

}
