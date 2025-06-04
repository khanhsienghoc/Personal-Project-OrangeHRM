package employee;

import common.Common_Employee_Login;
import commons.BaseTest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.DashboardPageObject;
import pageObject.LoginPageObject;
import pageObject.PageGeneratorManager;
import ultilities.DataUltilities;

public class User_01_Employee_Login extends BaseTest {
    @Parameters({"browser","environment"})
    @BeforeClass
    public void beforeClass(String browserName, String environmentName){
        log.info("Pre-conditon: Open Browser "+ browserName + " and navigate to the URL");
        driver = getBrowserDriver(browserName,environmentName);
        loginPage = PageGeneratorManager.getLoginPage(driver);
        fakeData = DataUltilities.getData();

        invalidPassword = fakeData.getInvalidPassword();
        invalidUsername = fakeData.getInvalidUsername();
    }
    @Description("Verify login with valid employee username and invalid password")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Login_01_Employee_ValidUsername_And_InvalidPassword(){
        log.info("Login_01_Employee_ValidUsername_And_InvalidPassword - Step 01 - Input valid username");
        loginPage.inputToTextBoxByText(driver,"Username", Common_Employee_Login.username );

        log.info("Login_01_Employee_ValidUsername_And_InvalidPassword - Step 02 - Input invalid password");
        loginPage.inputToTextBoxByText(driver,"Password", invalidPassword);

        log.info("Login_01_Employee_ValidUsername_And_InvalidPassword - Step 03 - Click Login");
        loginPage.clickToLoginButton();

        log.info("Login_01_Employee_ValidUsername_And_InvalidPassword - Step 03 - Verify the 'Invalid credentials' error message displays");
        Assertions.assertTrue(loginPage.isLoginFailedErrorMessage());
    }
    @Description("Verify login with invalid employee username and valid password")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Login_02_Employee_InvalidUsername_And_ValidPassword(){
        log.info("Login_02_Employee_InvalidUsername_And_ValidPassword - Step 01 - Input invalid username");
        loginPage.inputToTextBoxByText(driver,"Username", invalidUsername);

        log.info("Login_02_Employee_InvalidUsername_And_ValidPassword - Step 02 - Input valid password");
        loginPage.inputToTextBoxByText(driver,"Password", Common_Employee_Login.password );

        log.info("Login_02_Employee_InvalidUsername_And_ValidPassword - Step 03 - Click Login");
        loginPage.clickToLoginButton();

        log.info("Login_02_Employee_InvalidUsername_And_ValidPassword - Step 03 - Verify the 'Invalid credentials' error message displays");
        Assertions.assertTrue(loginPage.isLoginFailedErrorMessage());
    }
    @Description("Verify login with invalid employee username and invalid password")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Login_03_Employee_InvalidUsername_And_InvalidPassword(){
        log.info("Login_03_Employee_InvalidUsername_And_InvalidPassword - Step 01 - Input invalid username");
        loginPage.inputToTextBoxByText(driver,"Username", invalidUsername);

        log.info("Login_03_Employee_InvalidUsername_And_InvalidPassword - Step 02 - Input invalid password");
        loginPage.inputToTextBoxByText(driver,"Password", invalidPassword);

        log.info("Login_03_Employee_InvalidUsername_And_InvalidPassword - Step 03 - Click Login");
        loginPage.clickToLoginButton();

        log.info("Login_03_Employee_InvalidUsername_And_InvalidPassword - Step 03 - Verify the 'Invalid credentials' error message displays");
        Assertions.assertTrue(loginPage.isLoginFailedErrorMessage());
    }
    @Description("Verify login with valid employee username and valid password")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void Login_04_Employee_ValidCredentials(){
        log.info("Login_04_Employee_ValidCredentials - Step 01 - Input valid username");
        loginPage.inputToTextBoxByText(driver,"Username", Common_Employee_Login.username);

        log.info("Login_04_Employee_ValidCredentials - Step 02 - Input valid password");
        loginPage.inputToTextBoxByText(driver,"Password", Common_Employee_Login.password);

        log.info("Login_04_Employee_ValidCredentials - Step 03 - Click Login");
        loginPage.clickToLoginButton();
        homePage = PageGeneratorManager.getDashboardPage(driver);

        log.info("Login_04_Employee_ValidCredentials - Step 03 - Verify the page header 'Dashboard'");
        Assertions.assertEquals("Dashboard", homePage.getPageHeaderByText(driver,"Dashboard"));
    }
    @Description("Verify that some left tab displayed when login as Employee role")
    @Severity(SeverityLevel.NORMAL)
    @Test(dependsOnMethods = "Login_04_Employee_ValidCredentials")
    public void Login_05_Employee_VerifyTabDisplayed(){
        log.info("Login_05_Employee_VerifyTabDisplayed - Step 01 - Verify the 'Leave' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Leave"));

        log.info("Login_05_Employee_VerifyTabDisplayed - Step 02 - Verify the 'Time' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Time"));

        log.info("Login_05_Employee_VerifyTabDisplayed - Step 03 - Verify the 'My Info' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"My Info"));

        log.info("Login_05_Employee_VerifyTabDisplayed - Step 04 - Verify the 'Performance' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Performance"));

        log.info("Login_05_Employee_VerifyTabDisplayed - Step 05 - Verify the 'Dashboard' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Dashboard"));

        log.info("Login_05_Employee_VerifyTabDisplayed - Step 06 - Verify the 'Directory' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Directory"));

        log.info("Login_05_Employee_VerifyTabDisplayed - Step 07 - Verify the 'Claim' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Claim"));

        log.info("Login_05_Employee_VerifyTabDisplayed - Step 08 - Verify the 'Buzz' tab show");
        Assertions.assertTrue(homePage.isMenuTabDislaysByText(driver,"Buzz"));
    }
    @Description("Verify that some left tab undisplayed when login as Employee role")
    @Severity(SeverityLevel.NORMAL)
    @Test(dependsOnMethods = "Login_04_Employee_ValidCredentials")
    public void Login_06_Employee_VerifyTabUndisplayed(){
        log.info("Login_06_Employee_VerifyTabUndisplayed - Step 01 - Verify the 'PIM' undisplayed");
        Assertions.assertTrue(homePage.getListMenuTabSize(driver,"PIM") < 1);

        log.info("Login_06_Employee_VerifyTabUndisplayed - Step 01 - Verify the 'Recruitment' undisplayed");
        Assertions.assertTrue(homePage.getListMenuTabSize(driver,"Recruitment") < 1);

        log.info("Login_06_Employee_VerifyTabUndisplayed - Step 01 - Verify the 'Maintenance' undisplayed");
        Assertions.assertTrue(homePage.getListMenuTabSize(driver,"Maintenance") < 1);

    }
    @AfterClass(alwaysRun = true)
    public void afterClass(){
        closeBrowserAndDriver();
    }
    private WebDriver driver;
    private LoginPageObject loginPage;
    private DashboardPageObject homePage;
    private String invalidPassword, invalidUsername;
    private DataUltilities fakeData;
}
