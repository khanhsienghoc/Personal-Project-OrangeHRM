package pageObject;

import commons.BasePage;
import interfaces.LoginPageUI;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class LoginPageObject extends BasePage {
    private final WebDriver driver;

    public LoginPageObject(WebDriver driver){
        this.driver = driver;
    }
    @Step("Click Login button")
    public void clickToLoginButton(){
        waitForElementClickable(driver, LoginPageUI.LOGIN_BUTTON);
        clickToElement(driver, LoginPageUI.LOGIN_BUTTON);
    }




}
