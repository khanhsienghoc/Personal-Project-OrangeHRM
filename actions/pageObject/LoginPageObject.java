package pageObject;

import commons.BasePage;
import interfaces.LoginPageUI;
import org.openqa.selenium.WebDriver;

public class LoginPageObject extends BasePage {
    private final WebDriver driver;

    public LoginPageObject(WebDriver driver){
        this.driver = driver;
    }
    public void clickToLoginButton(){
        waitForElementClickable(driver, LoginPageUI.LOGIN_BUTTON);
        clickToElement(driver, LoginPageUI.LOGIN_BUTTON);
    }




}
