package admin;

import commons.BaseTest;
import commons.EnvironmentConfigManager;
import commons.GlobalConstants;
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
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Listeners({AllureTestNg.class, AllureTestListener.class})
public class User_02_Admin_ForgotPassword extends BaseTest {

    private static final String RESET_PASSWORD_TITLE = "Reset Password";
    private static final String SUCCESS_TITLE = "Reset Password link sent successfully";
    private static final String INITIAL_BODY_TEXT = "Please enter your username to identify your account to reset your password";
    private static final String SUCCESS_EMAIL_TEXT = "A reset password link has been sent to you via email.";
    private static final String FOLLOW_LINK_TEXT = "You can follow that link and select a new password.";
    private static final String CONTACT_ADMIN_TEXT = "Note:\nIf the email does not arrive, please contact your OrangeHRM Administrator.";

    @Parameters({"browser","environment"})
    @BeforeClass
    public void beforeClass(String browserName, String environmentName){
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
        Assertions.assertEquals(GlobalConstants.REQUIRED_ERROR_MESSAGE, resetPasswordPage.getErrorMessageByName(driver,"Username"),"Username field should show required error message when empty");
    }
    @Description("Verify placeholder of the Username field")
    @Severity(SeverityLevel.TRIVIAL)
    @Test
    public void Login_02_VerifyPlaceholders(){
        log.info("Login_02_VerifyPlaceholders - Step 01 - Verify the placeholder of the Username field");
        Assertions.assertEquals("Username", resetPasswordPage.getPropertyOfTextBoxByText(driver, "placeholder","Username"),"Username field placeholder should be 'Username'");
    }
    @Description("Verify reset password page navigation, content, and successful password reset flow")
    @Severity(SeverityLevel.TRIVIAL)
    @Test
    public void Login_03_ForgotPasswordLink(){
        log.info("Login_03_ForgotPasswordLink - Step 01 - Verify the title of the Reset Password page");
        Assertions.assertEquals(RESET_PASSWORD_TITLE, resetPasswordPage.getTitleOfResetPassword(), "Reset Password page title should be correct");

        log.info("Login_03_ForgotPasswordLink - Step 02 - Verify the body text of the Reset Password page");
        Assertions.assertEquals(INITIAL_BODY_TEXT, resetPasswordPage.getBodyTextOfResetPassword("Please enter your username"),"Reset Password page should display correct instruction text");

        log.info("Login_03_ForgotPasswordLink - Step 03 - Input username");
        resetPasswordPage.inputToTextBoxByText(driver, "Username", config.getAdminUserName());

        log.info("Login_03_ForgotPasswordLink - Step 04 - Leave the UserName empty and click Reset Password");
        resetPasswordPage.clickToButtonByText(driver,"Reset Password");

        log.info("Login_03_ForgotPasswordLink - Step 05 - Verify the title when reset password link sent to email successfully");
        Assertions.assertEquals(SUCCESS_TITLE , resetPasswordPage.getTitleOfResetPassword(), "Success page should display correct title");

        log.info("Login_03_ForgotPasswordLink - Step 06 - Verify the body text when reset password link sent to email successfully");
        Map<String, String> messages = Map.of(
                SUCCESS_EMAIL_TEXT, "A reset password",
                FOLLOW_LINK_TEXT , "select a new password.",
                CONTACT_ADMIN_TEXT, "If the email does not arrive"
        );
        AtomicInteger index = new AtomicInteger(0);
        log.info("AddNewEmployee_02_VerifyPlaceHolders -Verify the placeholder of textboxes");
        messages.forEach((message, textOfField) -> {
            int curentIndex = index.getAndIncrement() + 1;
            log.info("Login_03_ForgotPasswordLink - Step 06.{} - Verify the message '{}' show",curentIndex,message);
            Assertions.assertEquals(message , resetPasswordPage.getBodyTextOfResetPassword(textOfField),"Success page should show");
        });
    }
    @AfterTest(alwaysRun = true)
    public void afterTest(){
        log.info("Cleaning up: Closing browser and driver");
        closeBrowserAndDriver();
    }
    private WebDriver driver;
    private LoginPageObject loginPage;
    private ResetPasswordPageObject resetPasswordPage;
    EnvironmentConfigManager config = EnvironmentConfigManager.getInstance();
}
