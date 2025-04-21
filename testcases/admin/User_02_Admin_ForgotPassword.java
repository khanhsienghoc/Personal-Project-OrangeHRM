package admin;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.LoginPageObject;
import pageObject.PageGeneratorManager;
import pageObject.ResetPasswordPageObject;

public class User_02_Admin_ForgotPassword extends BaseTest {
    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName){
        log.info("Pre-conditon: Open Browser "+ browserName + " and navigate to the URL");
        driver = getBrowserDriver(browserName);
        loginPage = PageGeneratorManager.getLoginPage(driver);
    }
    @Description("Verify username is empty")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void Login_01_EmptyUsername(){
        log.info("Login_01_EmptyUsername - Step 01 - Click 'Forgot Your Password?' Hyperlink");
        resetPasswordPage = loginPage.clickForgotPasswordHyperlink();

        log.info("Login_01_EmptyUsername - Step 02 - Leave the UserName empty and click Reset Password");
        resetPasswordPage.clickToButtonByText(driver,"Reset Password");

        log.info("Login_01_EmptyUsername - Step 03 - Verify that an error message show for Username field");
        Assertions.assertEquals(GlobalConstants.REQUIRED_ERROR_MESSAGE, resetPasswordPage.getErrorMessageByName(driver,"Username"));
    }
    @Description("Verify placeholder of the Username field")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void Login_02_VerifyPlaceholders(){
        log.info("Login_02_VerifyPlaceholders - Step 01 - Verify the placeholder of the Username field");
        Assertions.assertEquals("Username", resetPasswordPage.getPropertyOfTextBoxByName(driver, "placeholder","Username"));
    }
    @Description("Verify navigate to Reset password page and text")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void Login_03_ForgotPasswordLink(){
        log.info("Login_03_ForgotPasswordLink - Step 01 - Verify the title of the Reset Password page");
        Assertions.assertEquals("Reset Password", resetPasswordPage.getTitleOfResetPassword());

        log.info("Login_03_ForgotPasswordLink - Step 02 - Verify the body text of the Reset Password page");
        Assertions.assertEquals("Please enter your username to identify your account to reset your password", resetPasswordPage.getBodyTextOfResetPassword());
    }
    private WebDriver driver;
    private LoginPageObject loginPage;
    private ResetPasswordPageObject resetPasswordPage;
}
