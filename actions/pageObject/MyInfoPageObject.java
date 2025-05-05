package pageObject;

import commons.BasePage;
import commons.GlobalConstants;
import interfaces.BasePageUI;
import interfaces.MyInfoPageUI;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class MyInfoPageObject extends BasePage {
    private final WebDriver driver;

    public MyInfoPageObject(WebDriver driver){
        this.driver = driver;
    }
    public  <T extends BasePage> T  clickOnLeftTabByText(String text){
        waitElementVisible(driver, MyInfoPageUI.HYPERLINK_LEFT_TAB_BY_TEXT, text);
        clickToElement(driver, MyInfoPageUI.HYPERLINK_LEFT_TAB_BY_TEXT, text);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.SHORT_TIMEOUT));
        waitListElementInvisible(driver, BasePageUI.LOADING_SPINNER);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
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
