package techcareer.page;

import techcareer.base.BaseTest;
import techcareer.utils.CsvHelper;
import techcareer.utils.Methods;

public class HomePage extends BaseTest {

    private String eventPrice = null;

    public HomePage acceptCookie(){
        if(Methods.getInstance().isElementVisibleWithoutWait("AllowAll")){
            Methods.getInstance().click("AllowAll");
        }
        return this;
    }

    public HomePage searchActivity(){
        Methods.getInstance().findElement("CimriLogo");
        Methods.getInstance().click("SearchBoxBar");
        Methods.getInstance().findElement("SearchInput");
        Methods.getInstance().sendKeys("SearchInput", CsvHelper.getInstance().getValueWithRowAndColumn("cimri.csv",0,0,true));
        Methods.getInstance().waitSeconds(3);
        Methods.getInstance().sendEnter("SearchInput");
        return this;
    }

    public HomePage selectActivity(){
        Methods.getInstance().findElement("ProductSelect");
        Methods.getInstance().click("ProductSelect");
        return this;
    }

    public HomePage getActivityPrice() {
        eventPrice = Methods.getInstance().getText("ProductPrice");
        return this;
    }

    public HomePage showStore() {
        Methods.getInstance().click("GoToStore");
        Methods.getInstance().switchToNewTab();
        Methods.getInstance().waitSeconds(3);
        Methods.getInstance().findElement("StoreProductPrice");
        return this;
    }

    public HomePage login(){
        Methods.getInstance().sendKeys("KullaniciAdi", CsvHelper.getInstance().getValueWithRowAndColumn("cimri.csv",0,1,true));
        Methods.getInstance().sendKeys("Sifre", CsvHelper.getInstance().getValueWithRowAndColumn("cimri.csv",0,2,true));
        Methods.getInstance().waitSeconds(2);
        Methods.getInstance().click("LoginButton");
        return this;
    }

    public HomePage compareStorePrice() {
        Methods.getInstance().waitDisplayed("ProductName");
        String price = Methods.getInstance().getText("StoreProductPrice");
        if(eventPrice.contains(price)){
            logger.info("Fiyatlar uyuşmaktadır.");
        } else {
            logger.info("Fiyatlar sepet ile etkinlik sayfasında uyuşmamaktadır.");
        }
        return this;

    }

    public HomePage popupClose() {
        try {
            Methods.getInstance().click("PopUp");
        } catch (Exception e ){
            logger.info("Popup görüntülenmedi.");
        }
        return this;
    }

}
