package pageObject;
import commons.BasePage;
import interfaces.ResetPasswordUI;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class ResetPasswordPageObject extends BasePage {
    private final WebDriver driver;
    public ResetPasswordPageObject(WebDriver driver){
        this.driver = driver;
    }
    /**
     * Get title of the form
     * @return String
     */
    @Step("Get title of Reset Password form")
    public String getTitleOfResetPassword(){
        waitElementVisible(driver, ResetPasswordUI.FORGOT_PASSWORD_TITLE);
        return getElementText(driver, ResetPasswordUI.FORGOT_PASSWORD_TITLE);
    }
    /**
     * Get the body text of the form by text
     * @param text
     * @return String
     */
    @Step("Get body text of Reset Password form")
    public String getBodyTextOfResetPassword(String text){
        waitElementVisible(driver, ResetPasswordUI.FORGOT_PASSWORD_BODY_BY_TEXT, text);
        return getElementText(driver, ResetPasswordUI.FORGOT_PASSWORD_BODY_BY_TEXT, text);
    }
}
