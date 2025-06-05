package admin;

import commons.BaseTest;
import commons.EnvironmentConfigManager;
import commons.GlobalConstants;
import dataAccessObject.EmployeeDataAccess;
import database.BaseDBHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.testng.AllureTestNg;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObject.*;
import reportConfigs.AllureTestListener;
import ultilities.DataUltilities;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static commons.TestGuard.skipIfDBDisabled;

@Listeners({AllureTestNg.class, AllureTestListener.class})
public class User_03_Admin_Add_New_Employee extends BaseTest {
    @Parameters({"browser","environment"})
    @BeforeClass
    public void beforeClass(String browserName, String environmentName){
        log.info("Pre-condition - Step_01 - Open Browser "+ browserName + " and navigate to the URL in " + environmentName + " environment");
        driver = getBrowserDriver(browserName, environmentName);
        config = EnvironmentConfigManager.getInstance();
        employeeDAo = EmployeeDataAccess.employeeData();
        loginPage = PageGeneratorManager.getLoginPage(driver);

        log.info("Pre-condition - Step_01 - Login with Admin username: '" + config.getAdminPassword() + "and Admin password: '" + config.getAdminPassword() + "and click Login button");
        loginPage.login(config.getAdminUserName(),config.getAdminPassword() );
        dashboardPage = PageGeneratorManager.getDashboardPage(driver);

        log.info("Pre-condition - Step_02 - Verify the page header 'Dashboard'");
        Assertions.assertEquals("Dashboard", dashboardPage.getPageHeaderByText(driver,"Dashboard"));

        log.info("Pre-condition - Step_03 - Click to PIM");
        pimPage = dashboardPage.clickToMenuByText(driver, "PIM");

        log.info("Pre-condition - Step_04 - Click Add button");
        pimPage.clickToButtonByText(driver, "Add");
        getAddEmployeePage= PageGeneratorManager.getAddEmployeePage(driver);

        fakeData = DataUltilities.getData();
        firstName = fakeData.getFirstName();
        middleName = fakeData.getMiddleName();
        lastName = fakeData.getLastName();
        username = fakeData.getUsername();
        password = fakeData.getValidPassword();
        invalidPassword = fakeData.getInvalidPassword();
        employeeID = fakeData.getEmployeeID();
    }
    @Description("Verify error message when leave required fields empty")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Admin_AddNewEmployee_01_EmptyFields() {
        log.info("AddNewEmployee_01_EmptyFields - Step_01 - Click on the 'Create Login Details' toggle");
        getAddEmployeePage.checkToCreateLoginDetailsToggle();

        log.info("AddNewEmployee_01_EmptyFields - Step_02 - Click on Save button without inputting any text in First Name and Last Name fields");
        getAddEmployeePage.clickToButtonByText(driver, "Save");

        log.info("AddNewEmployee_01_EmptyFields - Step_03 - Verify the error message in the First Name field");
        Assertions.assertEquals(GlobalConstants.REQUIRED_ERROR_MESSAGE, getAddEmployeePage.getErrorMessageOfTextBoxByName("firstName"));

        log.info("AddNewEmployee_01_EmptyFields - Step_04 - Verify the error message in the Last Name field");
        Assertions.assertEquals(GlobalConstants.REQUIRED_ERROR_MESSAGE, getAddEmployeePage.getErrorMessageOfTextBoxByName("lastName"));

        log.info("AddNewEmployee_01_EmptyFields - Step_05 - Verify the error message in the Username field");
        Assertions.assertEquals(GlobalConstants.REQUIRED_ERROR_MESSAGE, getAddEmployeePage.getErrorMessageByName(driver, "Username"));

        log.info("AddNewEmployee_01_EmptyFields - Step_06 - Verify the error message in the Password field");
        Assertions.assertEquals(GlobalConstants.REQUIRED_ERROR_MESSAGE, getAddEmployeePage.getErrorMessageByName(driver, "Password"));

        log.info("AddNewEmployee_01_EmptyFields - Step_07 - Verify the error message in the Confirm Password field");
        Assertions.assertEquals("Passwords do not match", getAddEmployeePage.getErrorMessageByName(driver, "Confirm Password"));
    }
    @Description("Verify placeholder First Name, Middle Name and Last Name fields")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Admin_AddNewEmployee_02_VerifyPlaceHolders() {
        Map<String, String> expectedFields = Map.of(
                "firstName", "First Name",
                "middleName","Middle Name",
                "lastName", "Last Name"
        );
        AtomicInteger index = new AtomicInteger(0);
        log.info("AddNewEmployee_02_VerifyPlaceHolders -Verify the placeholder of textboxes");
        expectedFields.forEach((fieldName, expectedValue) -> {
            int curentIndex = index.getAndIncrement() + 1;
            log.info("Edit_02_Employee_VerifyPersonalDetails - Step_0"+curentIndex+" - Verify the '"+expectedValue+"' field matched with the placeholder with value '"+ expectedValue+"'");
            Assertions.assertEquals(expectedValue, getAddEmployeePage.getPropertyOfTextBoxByName(driver, "placeholder",fieldName));
        });
    }
    @Description("verify input the unmatched password in Confirm Password field")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void Admin_AddNewEmployee_03_NotMatchConfirmPassword() {
        log.info("Admin_AddNewEmployee_03_NotMatchConfirmPassword - Step_01 - Input Employee Ìnformation");
        getAddEmployeePage.fillEmployeeInformation(firstName, middleName, lastName, employeeID);

        log.info("Admin_AddNewEmployee_03_NotMatchConfirmPassword - Step_02 - Input username with value '" + username + "', password with value '" + password + "' and confirm password with invalid value '"+ invalidPassword + "'");
        getAddEmployeePage.createLoginDetails(username, password, invalidPassword);

        log.info("Admin_AddNewEmployee_03_NotMatchConfirmPassword - Step_03 - Click Save button");
        getAddEmployeePage.clickToButtonByText(driver, "Save");

        log.info("Admin_AddNewEmployee_03_NotMatchConfirmPassword - Step_04 - Verify the error message in the Confirm Password field");
        Assertions.assertEquals("Passwords do not match", loginPage.getErrorMessageByName(driver, "Confirm Password"));
    }
    @Description("verify upload invalid files in the Employee avatar")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Admin_AddNewEmployee_04_VerifyUploadInvalidFile() {
        List<String> invalidFiles = Arrays.asList(docFile, txtFile);
        for(String file : invalidFiles){
            int index = invalidFiles.indexOf(file) + 1;
            log.info("Admin_AddNewEmployee_04_VerifyUploadInvalidFile - Step_01."+index+ "- Upload " + file + " file");
            getAddEmployeePage.uploadEmployeeAvatar(GlobalConstants.UPLOAD_FILE + file);

            log.info("Admin_AddNewEmployee_04_VerifyUploadInvalidFile - Step_02."+index+ "- Verify the error message: 'File type not allowed'");
            Assertions.assertEquals("File type not allowed", getAddEmployeePage.getErrorMessageForEmployeeAvatar());
        }
    }
    @Description("verify upload more than 1MB files in the Employee avatar")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Admin_AddNewEmployee_05_VerifyUploadInvalidFileMoreThan1MB() {
        List<String> invalidFiles = Arrays.asList(pngFileMoreThan1MB, gifFileMoreThan1MB, jpgFileMoreThan1MB);
        for(String file : invalidFiles){
            int index = invalidFiles.indexOf(file) + 1;
            log.info("Admin_AddNewEmployee_05_VerifyUploadInvalidFileMoreThan1MB - Step_01."+index+ "- Upload " + file + " file");
            getAddEmployeePage.uploadEmployeeAvatar(GlobalConstants.UPLOAD_FILE + file);

            log.info("Admin_AddNewEmployee_05_VerifyUploadInvalidFileMoreThan1MB - Step_02."+index+ "- Verify the error message: 'File type not allowed'");
            Assertions.assertEquals("Attachment Size Exceeded", getAddEmployeePage.getErrorMessageForEmployeeAvatar());
        }
    }
    @Description("Verify upload difference type Image")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Admin_AddNewEmployee_06_VerifyUploadValidTypeOfImage() {
        List<String> validFiles = Arrays.asList(gifFile, pngFile, jpegFile);
        for(String file : validFiles){
            int index = validFiles.indexOf(file) + 1;
            log.info("Admin_AddNewEmployee_06_VerifyUploadValidTypeOfImage - Step_01."+index+ "- Upload " + file + " file");
            getAddEmployeePage.uploadEmployeeAvatar(GlobalConstants.UPLOAD_FILE + file);

            log.info("Admin_AddNewEmployee_06_VerifyUploadValidTypeOfImage - Step_02."+index+ "- Verify the " + file +" is uploaded");
            Assertions.assertTrue(getAddEmployeePage.isEmployeeImageUploaded());

            log.info("Admin_AddNewEmployee_06_VerifyUploadValidTypeOfImage - Step_03."+index+ "- Verify there is no error show");
            Assertions.assertTrue(getAddEmployeePage.getSizeOfErrorInEmployeeAvatar() < 1);
        }
    }
    @Description("Verify add new employee successfully")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Admin_AddNewEmployee_07_AddNewEmployeeSuccess() {
        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step_01 - Click PIM");
        pimPage = getAddEmployeePage.clickToMenuByText(driver, "PIM");

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step_02 - Click Add");
        pimPage.clickToButtonByText(driver, "Add");
        getAddEmployeePage= PageGeneratorManager.getAddEmployeePage(driver);

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step_03 - Input Employee Information include First Name, Middle Name, Last Name and Employee ID");
        getAddEmployeePage.fillEmployeeInformation(firstName, middleName, lastName, employeeID);

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step_04 - Click 'Create Login Details' toggle");
        getAddEmployeePage.checkToCreateLoginDetailsToggle();

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step_05 - Input username with value '{}', password and confirm password with value '{}'", username, password);
        getAddEmployeePage.createLoginDetails(username, password, password);

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step_06 - Upload an employee avatar");
        getAddEmployeePage.uploadEmployeeAvatar(GlobalConstants.UPLOAD_FILE + gifFile);

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step_07 - Verify the employee avatar");
        Assertions.assertTrue(getAddEmployeePage.isEmployeeImageUploaded());

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step_08 - Click Save button");
        getAddEmployeePage.clickToButtonByText(driver, "Save");
        getPersonalPage = PageGeneratorManager.getPersonalDetails(driver);

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step_09 - Verify success message show");
        pimPage.verifySuccessMessage(driver);

        Map<String, String> expectedFields = Map.of(
                firstName,"firstName",
                middleName, "middleName",
                lastName, "lastName"
        );
        AtomicInteger index = new AtomicInteger(1);
        expectedFields.forEach((expectedField, fieldName) -> {
            log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step_10." + index + " Verify the " + fieldName +" matched the value '"+expectedField + "'" );
            Assertions.assertEquals(expectedField, getPersonalPage.getPropertyOfTextBoxByName(driver, "value",fieldName));
        });

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step_11 - Verify Employee ID value is: "+ employeeID);
        Assertions.assertEquals(String.valueOf(employeeID), getPersonalPage.getPropertyOfTextBoxByText(driver, "value","Employee Id"));
    }
    @Description("Verify the employee data exist on database")
    @Severity(SeverityLevel.NORMAL)
    @Test()
    public void Admin_AddNewEmployee_08_IsEmployeeExist() throws SQLException {
        skipIfDBDisabled();
        BaseDBHelper.connect();
        log.info("Admin_AddNewEmployee_08_IsEmployeeExist - Step_01 - Verify the employee record exists in database with employee id:" + employeeID);
        boolean isExist = employeeDAo.isEmployeeExist(employeeID);
        Assertions.assertTrue(isExist, "Employee record does NOT exist in the database");
    }
    @AfterTest(alwaysRun = true)
    public void afterTest(){
        log.info("Cleaning up: Closing browser and driver");
        closeBrowserAndDriver();
    }
    private WebDriver driver;
    private LoginPageObject loginPage;
    private DashboardPageObject dashboardPage;
    private PIMPageObject pimPage;
    private AddEmployeePageObject getAddEmployeePage;
    private PersonalDetailsPageObject getPersonalPage;
    private String firstName, middleName, lastName, username, password, invalidPassword;
    private DataUltilities fakeData;
    private String docFile = "DocFile.doc";
    private String txtFile = "TxtFile.txt";
    private String gifFile = "GifImage.gif";
    private String pngFile = "PngImage.png";
    private String jpegFile = "JpegImage.jpeg";
    private String pngFileMoreThan1MB = "PngImageMoreThan1MB.png";
    private String gifFileMoreThan1MB = "GifImageMoreThan1MB.gif";
    private String jpgFileMoreThan1MB = "JpgImageMoreThan1MB.jpg";
    private String employeeID;
    EnvironmentConfigManager config = EnvironmentConfigManager.getInstance();
    EmployeeDataAccess employeeDAo = new EmployeeDataAccess();

}

