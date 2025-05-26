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
    /**
     * Click to Login Button
     *
     */
    @Step("Click Login button")
    public void clickToLoginButton(){
        waitForElementClickable(driver, LoginPageUI.LOGIN_BUTTON);
        clickToElement(driver, LoginPageUI.LOGIN_BUTTON);
    }
    /**
     * Click 'Forgot Your Password?' Hyperlink
     *
     */
    @Step("Click 'Forgot your password?'")
    public ResetPasswordPageObject clickForgotPasswordHyperlink(){
        waitForElementClickable(driver, LoginPageUI.FORGOT_PASSWORD_HYPERLINK);
        clickToElement(driver, LoginPageUI.FORGOT_PASSWORD_HYPERLINK);
        return PageGeneratorManager.getResetPassPage(driver);
    }
    /**
     * Get the Login failed error message
     * @return boolean
     */
    @Step("Get the Failed Login error message")
    public boolean isLoginFailedErrorMessage(){
        waitElementVisible(driver, LoginPageUI.INVALID_CREDENTIALS_ERROR_MESSAGE);
        return isElementDisplayed(driver, LoginPageUI.INVALID_CREDENTIALS_ERROR_MESSAGE);
    }

    /**
     * Get the error text
     * @return String
     */
    @Step("Get the text of the error")
    public String getErrorText(){
        waitElementVisible(driver, LoginPageUI.INVALID_CREDENTIALS_ERROR_MESSAGE);
        return getElementText(driver, LoginPageUI.INVALID_CREDENTIALS_ERROR_MESSAGE);
    }
}
