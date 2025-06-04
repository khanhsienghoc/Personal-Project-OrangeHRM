package admin;

import commons.BaseTest;
import commons.EnvironmentConfigManager;
import commons.GlobalConstants;
import commons.TestGuard;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObject.LoginPageObject;
import pageObject.PageGeneratorManager;
import pageObject.ResetPasswordPageObject;
import reportConfigs.AllureTestListener;

@Listeners({AllureTestNg.class, AllureTestListener.class})
public class User_02_Admin_ForgotPassword extends BaseTest {
    @Parameters({"browser","environment"})
    @BeforeClass
    public void beforeClass(String browserName, String environmentName){
        log.info("Pre-conditon: Open Browser "+ browserName + " and navigate to the URL in " + environmentName + " environment");
        log.info("Pre-condition: Open Browser "+ browserName + " and navigate to the URL");
        driver = getBrowserDriver(browserName, environmentName);
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
    @Severity(SeverityLevel.TRIVIAL)
    @Test
    public void Login_02_VerifyPlaceholders(){
        log.info("Login_02_VerifyPlaceholders - Step 01 - Verify the placeholder of the Username field");
        Assertions.assertEquals("Username", resetPasswordPage.getPropertyOfTextBoxByText(driver, "placeholder","Username"));
    }
    @Description("Verify navigate to Reset password page and text")
    @Severity(SeverityLevel.TRIVIAL)
    @Test
    public void Login_03_ForgotPasswordLink(){
        log.info("Login_03_ForgotPasswordLink - Step 01 - Verify the title of the Reset Password page");
        Assertions.assertEquals("Reset Password", resetPasswordPage.getTitleOfResetPassword());

        log.info("Login_03_ForgotPasswordLink - Step 02 - Verify the body text of the Reset Password page");
        Assertions.assertEquals("Please enter your username to identify your account to reset your password", resetPasswordPage.getBodyTextOfResetPassword("Please enter your username"));

        log.info("Login_03_ForgotPasswordLink - Step 03 - Input username");
        resetPasswordPage.inputToTextBoxByText(driver, "Username", GlobalConstants.TESTING_ADMIN_USERNAME);
        resetPasswordPage.inputToTextBoxByText(driver, "Username", config.getAdminUserName());

        log.info("Login_03_ForgotPasswordLink - Step 04 - Leave the UserName empty and click Reset Password");
        resetPasswordPage.clickToButtonByText(driver,"Reset Password");

        log.info("Login_03_ForgotPasswordLink - Step 05 - Verify the title when reset password link sent to email successfully");
        Assertions.assertEquals("Reset Password link sent successfully", resetPasswordPage.getTitleOfResetPassword());

        log.info("Login_03_ForgotPasswordLink - Step 06 - Verify the body text when reset password link sent to email successfully");
        Assertions.assertEquals("A reset password link has been sent to you via email.", resetPasswordPage.getBodyTextOfResetPassword("A reset password"));
        Assertions.assertEquals("You can follow that link and select a new password.", resetPasswordPage.getBodyTextOfResetPassword("select a new password."));
        Assertions.assertEquals("Note:\nIf the email does not arrive, please contact your OrangeHRM Administrator.", resetPasswordPage.getBodyTextOfResetPassword("If the email does not arrive"));
    }
    @Description("Verify database")
    @Severity(SeverityLevel.TRIVIAL)
    @Test (groups = "databaseTesting")
    public void Login_Verify_DB() {
        TestGuard.skipIfDBDisabled();
        System.out.println("✅ DB test logic");
    }

    @AfterTest
    public void afterTest(){
        closeBrowserAndDriver();
    }
    private WebDriver driver;
    private LoginPageObject loginPage;
    private ResetPasswordPageObject resetPasswordPage;
    EnvironmentConfigManager config = EnvironmentConfigManager.getInstance();
}
