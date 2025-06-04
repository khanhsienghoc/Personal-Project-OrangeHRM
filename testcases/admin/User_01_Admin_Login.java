package admin;

import commons.BaseTest;
import commons.EnvironmentConfigManager;
import commons.GlobalConstants;
import io.qameta.allure.testng.AllureTestNg;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObject.DashboardPageObject;
import pageObject.LoginPageObject;
import pageObject.PageGeneratorManager;
import reportConfigs.AllureTestListener;
import ultilities.DataUltilities;

@Listeners({AllureTestNg.class, AllureTestListener.class})
public class User_01_Admin_Login extends BaseTest {
    @Parameters({"browser","environment"})
    @BeforeClass
    public void beforeClass(String browserName, String environmentName){
        log.info("Pre-conditon: Open Browser "+ browserName + " and navigate to the URL in " + environmentName + " environment");
        driver = getBrowserDriver(browserName, environmentName);
        config = EnvironmentConfigManager.getInstance();
        loginPage = PageGeneratorManager.getLoginPage(driver);
        fakeData = DataUltilities.getData();

        invalidUsername = "invalidUsername" + fakeData.getUsername();
        invalidPassword = fakeData.getInvalidPassword();
    }
    @Test
    public void Login_01_Admin_Empty_Username_And_Password(){
        log.info("Login_01_Admin_Empty_Username_And_Password - Step_01 - Leave the Username and Password empty");
        loginPage.inputToTextBoxByText(driver, "Username","" );
        loginPage.inputToTextBoxByText(driver, "Password","" );

        log.info("Login_01_Admin_Empty_Username_And_Password - Step_02 - Click Login button");
        loginPage.clickToLoginButton();

        log.info("Login_01_Admin_Empty_Username_And_Password - Step_03 - Verify the error message when leave the username and password empty");
        Assertions.assertEquals(GlobalConstants.REQUIRED_ERROR_MESSAGE, loginPage.getErrorMessageByName(driver, "Username"));
        Assertions.assertEquals(GlobalConstants.REQUIRED_ERROR_MESSAGE, loginPage.getErrorMessageByName(driver, "Password"));
    }
    @Test
    public void Login_02_Admin_InvalidCredentials(){
        log.info("Login_02_Admin_InvalidCredentials - Step_01 - Unsuccessful login with invalid credentials");
        loginPage.inputToTextBoxByText(driver, "Username",invalidUsername );
        loginPage.inputToTextBoxByText(driver, "Password",invalidPassword );

        log.info("Login_02_Admin_InvalidCredentials - Step_02 - Click Login button");
        loginPage.clickToLoginButton();

        log.info("Login_02_Admin_InvalidCredentials - Step_03 - Verify the error message is: '"+ GlobalConstants.INVALID_CREDENTIALS_MESSAGE + "'");
        Assertions.assertEquals(GlobalConstants.INVALID_CREDENTIALS_MESSAGE, loginPage.getErrorText());
    }
    @Test
    public void Login_03_Admin_EmptyUsername(){
        log.info("Login_03_Admin_EmptyUsername - Step_01 - Login with empty username");
        loginPage.inputToTextBoxByText(driver, "Username","" );
        loginPage.inputToTextBoxByText(driver, "Password",config.getAdminPassword());

        log.info("Login_03_Admin_EmptyUsername - Step_02 - Click Login button");
        loginPage.clickToLoginButton();

        log.info("Login_03_Admin_EmptyUsername - Step_03 - Verify the error message is: Verify the error message when leave the username empty");
        Assertions.assertEquals(GlobalConstants.REQUIRED_ERROR_MESSAGE, loginPage.getErrorMessageByName(driver, "Username"));
    }
    @Test
    public void Login_04_Admin_EmptyPassword(){
        log.info("Login_04_Admin_EmptyPassword - Step_01 - Login with empty username");
        loginPage.inputToTextBoxByText(driver, "Username",config.getAdminUserName() );
        loginPage.inputToTextBoxByText(driver, "Password","");

        log.info("Login_04_Admin_EmptyPassword - Step_02 - Click Login button");
        loginPage.clickToLoginButton();

        log.info("Login_04_Admin_EmptyPassword - Step_03 - Verify the error message is: Verify the error message when leave the password empty");
        Assertions.assertEquals(GlobalConstants.REQUIRED_ERROR_MESSAGE, loginPage.getErrorMessageByName(driver, "Password"));
    }
    @Test
    public void Login_05_Admin_VerifyPlaceholders(){
        log.info("Login_05_Admin_VerifyPlaceholders - Step_01 - Verify the placeholder of the username textbox is 'Username'");
        Assertions.assertEquals("Username", loginPage.getPropertyOfTextBoxByName(driver, "placeholder","username"));

        log.info("Login_05_Admin_VerifyPlaceholders - Step_02 - Verify the placeholder of the password textbox is 'Password'");
        Assertions.assertEquals("Password", loginPage.getPropertyOfTextBoxByName(driver, "placeholder","password"));
    }
    @Test
    public void Login_06_Admin_SuccessLogin(){
        log.info("Login_06_Admin_SuccessLogin - Step_01 - Input the username");
        loginPage.inputToTextBoxByText(driver, "Username",config.getAdminUserName() );
        System.out.println("Admin username : " + config.getAdminUserName());

        log.info("Login_06_Admin_SuccessLogin - Step_02 - Input the password");
        loginPage.inputToTextBoxByText(driver, "Password",config.getAdminPassword());
        System.out.println("Admin username : " + config.getAdminPassword());

        log.info("Login_06_Admin_SuccessLogin - Step_03 - Click Login button");
        loginPage.clickToLoginButton();

        log.info("Login_06_Admin_SuccessLogin - Step_04 - Verify the page header 'Dashboard'");
        homePage = PageGeneratorManager.getDashboardPage(driver);
        Assertions.assertEquals("Dashboard", homePage.getPageHeaderByText(driver,"Dashboard"));
    }
    @Test
    public void Login_07_Admin_VerifyTabDisplays(){

        log.info("Login_07_Admin_VerifyTabDisplays - Step_01 - Verify the 'PIM' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"PIM"));
                
        log.info("Login_07_Admin_VerifyTabDisplays - Step_02 - Verify the 'Leave' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Leave"));

        log.info("Login_07_Admin_VerifyTabDisplays - Step_03 - Verify the 'Time' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Time"));

        log.info("Login_07_Admin_VerifyTabDisplays - Step_04 - Verify the 'Recruitment' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Recruitment"));

        log.info("Login_07_Admin_VerifyTabDisplays - Step_05 - Verify the 'My Info' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"My Info"));

        log.info("Login_07_Admin_VerifyTabDisplays - Step_06 - Verify the 'Performance' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Performance"));

        log.info("Login_07_Admin_VerifyTabDisplays - Step_07 - Verify the 'Dashboard' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Dashboard"));

        log.info("Login_07_Admin_VerifyTabDisplays - Step_08 - Verify the 'Directory' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Directory"));

        log.info("Login_07_Admin_VerifyTabDisplays - Step_09 - Verify the 'Maintenance' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Maintenance"));

        log.info("Login_07_Admin_VerifyTabDisplays - Step_10 - Verify the 'Claim' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Claim"));

        log.info("Login_07_Admin_VerifyTabDisplays - Step_11 - Verify the 'Buzz' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Buzz"));
    }
    @AfterClass(alwaysRun = true)
    public void afterClass(){
        closeBrowserAndDriver();
    }
    private WebDriver driver;
    private LoginPageObject loginPage;
    private DataUltilities fakeData;
    private String invalidUsername, invalidPassword;
    private String username, password;
    private DashboardPageObject homePage;
    EnvironmentConfigManager config = EnvironmentConfigManager.getInstance();
}
