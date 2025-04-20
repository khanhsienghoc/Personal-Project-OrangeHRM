package admin;

import commons.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObject.LoginPageObject;
import pageObject.PageGeneratorManager;

public class User01_Admin_Login extends BaseTest {
    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName){
        log.info("Pre-conditon: Open Browser "+ browserName + "and navigate to the URL");
        driver = getBrowserDriver(browserName);
        loginPage = PageGeneratorManager.getLoginPage(driver);
        username = "Admin";
        password = "admin123";

    }
    @Test
    public void Login_01_Admin_Empty_Username_And_Password(){
        log.info("Login_01 : Leave the Username and Password empty");
        loginPage.inputToTextBoxByName(driver, "username","" );
        loginPage.inputToTextBoxByName(driver, "password","" );

        log.info("Login_01: Click Login button");
        loginPage.clickToLoginButton();

        log.info("Login_01: Verify the error message when leave the username and password empty");
        Assertions.assertEquals("Required", loginPage.getErrorMessageByName(driver, "Username"));
        Assertions.assertEquals("Required", loginPage.getErrorMessageByName(driver, "Password"));

    }
    @AfterClass(alwaysRun = true)
    public void afterClass(){
        closeBrowserAndDriver();
    }
    private WebDriver driver;
    private String username, password;
    private LoginPageObject loginPage;
}
