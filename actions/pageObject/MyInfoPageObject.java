package pageObject;

import commons.BasePage;
import interfaces.BasePageUI;
import interfaces.MyInfoPageUI;
import org.openqa.selenium.WebDriver;

public class MyInfoPageObject extends BasePage {
    private final WebDriver driver;

    public MyInfoPageObject(WebDriver driver){
        this.driver = driver;
    }
    public  <T extends BasePage> T  clickOnLeftTabByText(String text){
        waitElementVisible(driver, MyInfoPageUI.HYPERLINK_LEFT_TAB_BY_TEXT, text);
        clickToElement(driver, MyInfoPageUI.HYPERLINK_LEFT_TAB_BY_TEXT, text);
        if (text.equals("Personal Details")) {
            return (T) PageGeneratorManager.getPersonalDetails(driver);
        }
        return (T) this;
    }
    public String getLeftTabTitleByText(String text){
        waitElementVisible(driver, MyInfoPageUI.HYPERLINK_LEFT_TAB_BY_TEXT, text);
        return getElementText(driver, MyInfoPageUI.HYPERLINK_LEFT_TAB_BY_TEXT, text);
    }
}
