package techcareer.test;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import techcareer.base.BaseTest;
import techcareer.page.HomePage;
@DisplayName("Cimri.com Home Page Tests")
@Owner("Burhan Aydın")
public class HomePageTest extends BaseTest {
    private HomePage homePage;
    public HomePageTest(){
        homePage = new HomePage();
    }

    @Test
    public void productTest(){
        homePage.acceptCookie()
                .searchActivity()
                .selectActivity()
                .getActivityPrice()
                .showStore()
                .compareStorePrice();
    }
}
