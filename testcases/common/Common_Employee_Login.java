package common;

import commons.BaseTest;
import commons.GlobalConstants;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObject.*;
import ultilities.DataUltilities;

import java.util.Set;

public class Common_Employee_Login extends BaseTest {
    @Parameters("browser")
    @BeforeTest
    public void beforeClass(String browserName) {
        log.info("Pre-conditon- Step 01 - Open Browser " + browserName + " and navigate to the URL");

        driver = getBrowserDriver(browserName);
        loginPage = PageGeneratorManager.getLoginPage(driver);
        fakeDate = DataUltilities.getData();

        username = fakeDate.getUsername();
        password = fakeDate.getPassword();
        firstName = fakeDate.getFirstName();
        middleName = fakeDate.getMiddleName();
        lastName = fakeDate.getLastName();

        log.info("Pre-conditon - Step 02 - Input username of the admin with value: " + GlobalConstants.ADMIN_USERNAME);
        loginPage.inputToTextBoxByText(driver, "Username", GlobalConstants.ADMIN_USERNAME);

        log.info("Pre-conditon - Step 03 - Input password of the admin with value: " + GlobalConstants.ADMIN_PASSWORD);
        loginPage.inputToTextBoxByText(driver, "Password", GlobalConstants.ADMIN_PASSWORD);

        log.info("Pre-conditon - Step 04 - Click Login button");
        loginPage.clickToLoginButton();
        homePage = PageGeneratorManager.getHomePage(driver);

        log.info("Pre-conditon - Step 05 - Verify the page title");
        Assertions.assertEquals("OrangeHRM", loginPage.getPageTitle(driver));

        log.info("Pre-conditon - Step 06 - Click the PIM tab");
        pimPage = homePage.clickToMenuByText(driver, "PIM");

        log.info("Pre-conditon - Step 07 - Click Add button to add new employee");
        pimPage.clickToButtonByText(driver, "Add");
        getAddEmployeePage= PageGeneratorManager.getAddEmployeePage(driver);

        log.info("Pre-conditon - Step 08 - Input Employee First Name with value: '"+ firstName +"'" );
        getAddEmployeePage.InputEmployeeInformationByName("firstName", firstName);

        log.info("Pre-conditon - Step 09 - Input Employee Middle Name with value: '" + middleName + "'");
        getAddEmployeePage.InputEmployeeInformationByName("middleName", middleName);

        log.info("Pre-conditon - Step 10 - Input Employee Last Name with value: '" + lastName + "'");
        getAddEmployeePage.InputEmployeeInformationByName("lastName", lastName);

        log.info("Pre-conditon - Step 11 - Click on the toggle 'Create Login Details'");
        getAddEmployeePage.checkToCreateLoginDetailsToggle();

        log.info("Pre-conditon - Step 12 - Input Username with value: " + username);
        getAddEmployeePage.inputToTextBoxByText(driver, "Username", username);

        log.info("Pre-conditon - Step 13 - Input Password and Confirm Password with value: " + password);
        getAddEmployeePage.inputToTextBoxByText(driver, "Password", password);
        getAddEmployeePage.inputToTextBoxByText(driver, "Confirm Password", password);

        log.info("Pre-conditon - Step 14 - Click Save Button");
        getAddEmployeePage.clickToButtonByText(driver, "Save");
        pimPage = PageGeneratorManager.getPIMPage(driver);

        log.info("Pre-conditon - Step 15 - Verify a success pop up show");
        Assertions.assertTrue(pimPage.isSuccessPopUpShow(driver));

        log.info("Pre-conditon - Step 16 - Click on Profile Options and click Logout");
        pimPage.clickOnProfileDropdown(driver);
        pimPage.clickOnProfileOptionByText(driver, "Logout");
        loginPage = PageGeneratorManager.getLoginPage(driver);

        log.info("Pre-conditon - Step 17 - Input username of the employee with value: " +username);
        loginPage.inputToTextBoxByText(driver, "Username", username);

        log.info("Pre-conditon - Step 18 - Input password of the employee with value: "+ password);
        loginPage.inputToTextBoxByText(driver, "Password", password);

        log.info("Pre-conditon - Step 19 - Click Login");
        loginPage.clickToLoginButton();
        homePage = PageGeneratorManager.getHomePage(driver);

        log.info("Pre-conditon - Step 20 - Verify Page Title");
        Assertions.assertEquals("OrangeHRM", homePage.getPageTitle(driver));

        log.info("Pre-conditon - Step 21 - After login successfully, get cookies");
        LoggedCookkies = homePage.getAllCookies(driver);

        log.info("Pre-conditon - Step 22 - Click Profile Option");
        pimPage.clickOnProfileDropdown(driver);

        log.info("Pre-conditon - Step 23 - Click Logout");
        pimPage.clickOnProfileOptionByText(driver, "Logout");

    }
    @AfterTest(alwaysRun = true)
    public void afterTest(){
        closeBrowserAndDriver();
    }
    private WebDriver driver;
    public static String username, password;
    private LoginPageObject loginPage;
    private HomePageObject homePage;
    private PIMPageObject pimPage;
    private AddEmployeePageObject getAddEmployeePage;
    private String firstName, middleName, lastName;
    public static Set<Cookie> LoggedCookkies;
    private DataUltilities fakeDate;

}
