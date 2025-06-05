package employee;

import common.Common_Employee_Login;
import commons.BaseTest;
import dataObject.PersonalDetailsData;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObject.*;
import reportConfigs.AllureTestListener;
import ultilities.DataUltilities;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Listeners({AllureTestNg.class, AllureTestListener.class})
public class User_02_Employee_Edit_PersonalDetails extends BaseTest {
    @Parameters({"browser","environment"})
    @BeforeClass
    public void beforeClass(String browserName, String environmentName){
        log.info("Pre-condition: Open Browser "+ browserName + " and navigate to the URL in " + environmentName + " environment");
        log.info("Pre-condition: Open Browser "+ browserName + " and navigate to the URL");
        driver = getBrowserDriver(browserName, environmentName);

        log.info("Pre-condition: Input username with value: " + Common_Employee_Login.username);
        loginPage = PageGeneratorManager.getLoginPage(driver);
        loginPage.inputToTextBoxByText(driver, "Username", Common_Employee_Login.username);

        log.info("Pre-condition: Input password with value: " + Common_Employee_Login.password);
        loginPage.inputToTextBoxByText(driver, "Password", Common_Employee_Login.password);

        log.info("Pre-condition: Click Login");
        loginPage.clickToLoginButton();
        homePage = PageGeneratorManager.getDashboardPage(driver);
        initializeTestData();
    }
    private void initializeTestData() {
        DataUltilities fakeData = DataUltilities.getData();
        testData = new PersonalDetailsData(
                fakeData.getFirstName(),
                fakeData.getMiddleName(),
                fakeData.getLastName(),
                fakeData.getOtherID(),
                fakeData.getDate(),
                fakeData.getNationality(),
                fakeData.getMaritalStatus(),
                fakeData.getGender(),
                fakeData.getBloodType()
        );
        comment1 = fakeData.getComment();
        comment2 = fakeData.getComment();
        currentDate = fakeData.getCurrentDate();
        updatedComment = fakeData.getComment();
    }
    @Description("Verify employee user is able to view Personal Details")
    @Severity(SeverityLevel.TRIVIAL)
    @Test
    public void Edit_01_Employee_ViewPersonalDetail(){
        log.info("Edit_01_Employee_ViewPersonalDetail - Step_01: Click on My Info");
        myInfo = loginPage.clickToMenuByText(driver,"My Info");

        log.info("Edit_01_Employee_ViewPersonalDetail - Step_02: Click on Personal Details");
        getPersonalDetails = myInfo.clickOnLeftTabByText("Personal Details");

        log.info("Edit_01_Employee_ViewPersonalDetail - Step_03: Verify View the Personal Details tab and title");
        Assertions.assertEquals("Personal Details", myInfo.getLeftTabTitleByText("Personal Details"),
                "Personal Details tab should be displayed");

        log.info("Edit_01_Employee_ViewPersonalDetail - Step_04: Verify the title of the form");
        Assertions.assertEquals("Personal Details", myInfo.getPageHeaderByText(driver,"Personal Details"),
                "Personal Details page header should be displayed correctly");
    }
    @Description("Verify employee user cannot edit restricted fields")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void Edit_02_Employee_VerifyPersonalDetails(){
        Map<String, String> expectedFields = Map.of(
                "firstName", Common_Employee_Login.firstName,
                "middleName", Common_Employee_Login.middleName,
                "lastName", Common_Employee_Login.lastName
        );
        AtomicInteger index = new AtomicInteger(0);
        log.info("Edit_02_Employee_VerifyPersonalDetails - Verifying personal details fields");
        expectedFields.forEach((fieldName, expectedValue) -> {
            int curentIndex = index.getAndIncrement() + 1;
            log.info("Edit_02_Employee_VerifyPersonalDetails - Step_0"+curentIndex+" - Verify the '"+fieldName+"' field matched with expected field with value '"+ expectedValue+"'");
            String actualValue = getPersonalDetails.getPropertyOfTextBoxByName(driver, "value", fieldName);
            Assertions.assertEquals(expectedValue, actualValue, fieldName + " should match expected value");
        });
    }
    @Description("Verify employee user cannot edit restricted fields")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void Edit_03_Employee_DisabledFields(){
        log.info("Edit_03_Employee_DisabledFields - Step_01: Verify 'Employee ID' field is disabled");
        Assertions.assertFalse(getPersonalDetails.isTextboxEnabledByText(driver,"Employee Id"),
                "Employee ID field should be disabled");

        log.info("Edit_03_Employee_DisabledFields - Step_02: Verify 'Driver's License Number' field is disabled");
        Assertions.assertFalse(getPersonalDetails.isTextboxEnabledByText(driver,"Driver's License Number"),
                "Driver's License Number field should be disabled");

        log.info("Edit_03_Employee_DisabledFields - Step_03: Verify 'Date of Birth' field is disabled");
        Assertions.assertFalse(getPersonalDetails.isTextboxEnabledByText(driver,"Date of Birth"),
                "Date of Birth field should be disabled");
    }
    @Description("Verify employee user can edit Personal Details")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Edit_04_Employee_EditPersonalDetail(){
        log.info("Edit_04_Employee_EditPersonalDetail - Step_01: Filling personal details form with new data");
        getPersonalDetails.fillPersonalDetailsForm(testData);

        log.info("Edit_04_Employee_EditPersonalDetail - Step_02: Click Save Button");
        getPersonalDetails.clickOnButtonByHeaderAndByButtonText(driver, "Personal Details", "Save");

        log.info("Edit_04_Employee_EditPersonalDetail - Step_03: Verify a success pop up show");
        getPersonalDetails.verifySuccessMessage(driver);

        log.info("Edit_04_Employee_EditPersonalDetail - Step_04: Verify Personal Data");
        getPersonalDetails.verifyPersonalDetails(testData);
    }
    @Description("Verify employee user can edit Custom Fields")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Edit_05_Employee_EditCustomDetails(){
        log.info("Edit_05_Employee_EditCustomDetails - Step_01: Choose 'Blood Type' dropdown with value: "+ testData.getBloodType());
        getPersonalDetails.selectValueInDropdownByText(driver, "Blood Type",testData.getBloodType());

        log.info("Edit_05_Employee_EditCustomDetails - Step_02: Click Save Button");
        getPersonalDetails.clickOnButtonByHeaderAndByButtonText(driver, "Custom Fields", "Save");

        log.info("Edit_05_Employee_EditCustomDetails - Step_3: Verify a success pop up show");
        getPersonalDetails.verifySuccessMessage(driver);

        log.info("Edit_04_Employee_EditPersonalDetail - Step_4: Verify the Blood type dropdown show the latest value: " + testData.getBloodType());
        Assertions.assertEquals(testData.getBloodType(),getPersonalDetails.getChosenValueFromNationalityDropdownByText("Blood Type"));
    }
    @Description("Verify employee user can edit Custom Fields")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Edit_06_Employee_AddAttachmentsMoreThan1MB(){
        log.info("Edit_06_Employee_AddAttachmentsMoreThan1MB - Step_01: Click Add button in the Attachments section");
        getPersonalDetails.clickOnButtonByHeaderAndByButtonText(driver,"Attachments", "Add");

        log.info("Edit_06_Employee_AddAttachmentsMoreThan1MB - Step_02: Upload an attachment with more than 1 MB");
        getPersonalDetails.addAttachment(driver, fileMoreThan1MB);

        log.info("Edit_06_Employee_AddAttachmentsMoreThan1MB - Step_3: Verify an error message show 'Attachment Size Exceeded'");
        Assertions.assertEquals("Attachment Size Exceeded",getPersonalDetails.getErrorMessageByName(driver,"Select File"),
                "Should display attachment size exceeded error for files > 1MB");
    }
    @Description("Verify employee can add attachment")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void Edit_07_Employee_AddAttachments(){
        log.info("Edit_07_Employee_AddAttachments - Step_01: Upload a valid attachment with attachment name: " + fileLessThan1MB);
        getPersonalDetails.addAttachment(driver, fileLessThan1MB);

        log.info("Edit_07_Employee_AddAttachments - Step_2: Add Comment with value: "+ comment1+"\n"+comment2);
        getPersonalDetails.inputToCommentTextArea(driver, comment1+"\n"+comment2);

        log.info("Edit_07_Employee_AddAttachments - Step_3: Click Save button");
        getPersonalDetails.clickOnButtonByHeaderAndByButtonText(driver, "Add Attachment", "Save");

        log.info("Edit_07_Employee_AddAttachments - Step_4: Verify a success pop up show");
        getPersonalDetails.verifySuccessMessage(driver);

        log.info("Edit_07_Employee_AddAttachments - Step_5: Verify the attachment name after uploaded");
        Assertions.assertEquals(fileLessThan1MB, getPersonalDetails.getFileDescriptionByFieldAndByText(driver,"File Name",fileLessThan1MB ));

        log.info("Edit_07_Employee_AddAttachments - Step_6: Verify the attachment description after uploaded");
        Assertions.assertEquals(comment1+" "+comment2,getPersonalDetails.getFileDescriptionByFieldAndByText(driver,"Description",comment1));

        log.info("Edit_07_Employee_AddAttachments - Step_7: Verify the attachment added in the current date: " + currentDate);
        Assertions.assertEquals(currentDate, getPersonalDetails.getFileDescriptionByFieldAndByText(driver,"Date Added",currentDate));

        log.info("Edit_07_Employee_AddAttachments - Step_8: Verify the attachment added by the employee username: " + Common_Employee_Login.username);
        Assertions.assertEquals(Common_Employee_Login.username, getPersonalDetails.getFileDescriptionByFieldAndByText(driver,"Added By",Common_Employee_Login.username));
    }
    @Description("Verify employee can edit attachment")
    @Severity(SeverityLevel.NORMAL)
    @Test()
    public void Edit_08_Employee_EditAttachments(){
        log.info("Edit_08_Employee_EditAttachments - Step_01: Click to the Edit button");
        getPersonalDetails.clickToActionAttachment(driver, fileLessThan1MB, "pencil");

        log.info("Edit_08_Employee_EditAttachments - Step_2: Upload attachment name: " + txtFile);
        getPersonalDetails.addAttachment(driver, txtFile);

        log.info("Edit_08_Employee_EditAttachments - Step_2: Add Comment with value: "+updatedComment );
        getPersonalDetails.inputToCommentTextArea(driver, updatedComment);

        log.info("Edit_08_Employee_EditAttachments - Step_3: Click Save button");
        getPersonalDetails.clickOnButtonByHeaderAndByButtonText(driver, "Edit Attachment", "Save");

        log.info("Edit_08_Employee_EditAttachments - Step_4: Verify a success pop up show");
        getPersonalDetails.verifySuccessMessage(driver);

        log.info("Edit_08_Employee_EditAttachments - Step_5: Verify the attachment name after uploaded");
        Assertions.assertEquals(txtFile, getPersonalDetails.getFileDescriptionByFieldAndByText(driver,"File Name",txtFile ));

        log.info("Edit_08_Employee_EditAttachments - Step_6: Verify the attachment description after uploaded");
        Assertions.assertEquals(updatedComment,getPersonalDetails.getFileDescriptionByFieldAndByText(driver,"Description",updatedComment));

        log.info("Edit_08_Employee_EditAttachments - Step_7: Verify the attachment added in the current date: " + currentDate);
        Assertions.assertEquals(currentDate, getPersonalDetails.getFileDescriptionByFieldAndByText(driver,"Date Added",currentDate));

        log.info("Edit_08_Employee_EditAttachments - Step_8: Verify the attachment added by the employee username: " + Common_Employee_Login.username);
        Assertions.assertEquals(Common_Employee_Login.username, getPersonalDetails.getFileDescriptionByFieldAndByText(driver,"Added By",Common_Employee_Login.username));
    }
    @Description("Verify employee can delete an attachment")
    @Severity(SeverityLevel.CRITICAL)
    @Test()
    public void Edit_09_Employee_DeleteAnAttachment(){
        log.info("Edit_09_Employee_DeleteAnAttachment - Step_01: Click to the Delete button of the attachment name: "+ txtFile );
        getPersonalDetails.clickToActionAttachment(driver, txtFile, "trash");

        log.info("Edit_09_Employee_DeleteAnAttachment - Step_01: Click to the Yes, Delete button");
        getPersonalDetails.clickCancelOrDeleteInConfirmDeletePopup(driver,"Yes, Delete");

        log.info("Edit_09_Employee_DeleteAnAttachment - Step_4: Verify a success pop up show");
        getPersonalDetails.verifySuccessMessage(driver);

        log.info("Edit_09_Employee_DeleteAnAttachment - Step_4: Verify the attachment disappear");
        Assertions.assertTrue(getPersonalDetails.getListAttachmentSizeByFieldAndText(driver,"File Name", txtFile) < 1);
        Assertions.assertTrue(getPersonalDetails.getListAttachmentSizeByFieldAndText(driver,"Description", updatedComment) < 1);
    }
    @Description("Verify employee can delete an attachment")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void Edit_10_Employee_DeleteMultipleAttachments(){
        List<String> fileListName = Arrays.asList(new String[]{docFile, gifImage,  jpegImage, pngImage, xlsxFile});
        for (String file : fileListName){
                int index = fileListName.indexOf(file) + 1;
                log.info("Edit_10_Employee_DeleteMultipleAttachments - Step_01." + index + ": Click Add Attachment");
                getPersonalDetails.clickOnButtonByHeaderAndByButtonText(driver, "Attachments", "Add");

                log.info("Edit_10_Employee_DeleteMultipleAttachments - Step_02." + index + ": Add new attachment with file name: " + file);
                getPersonalDetails.addAttachment(driver, file);

                log.info("Edit_10_Employee_DeleteMultipleAttachments - Step_03." + index + ": Click Save button");
                getPersonalDetails.clickOnButtonByHeaderAndByButtonText(driver, "Add Attachment", "Save");

                log.info("Edit_10_Employee_DeleteMultipleAttachments - Step_04." + index + ": Verify a success pop up show");
                getPersonalDetails.verifySuccessMessage(driver);
            }
        log.info("Edit_10_Employee_DeleteMultipleAttachments - Step_05: Verify the number of uploaded attachment is " + fileListName.size());
        Assertions.assertEquals("("+fileListName.size()+") Records Found", getPersonalDetails.getNumberOfUploadedAttachment(driver));

        for (String file: fileListName){
            int index = fileListName.indexOf(file) + 1;
            log.info("Edit_10_Employee_DeleteMultipleAttachments - Step_06."+index+": Check to the checkbox of the file name: " + file);
            getPersonalDetails.checkToTheCheckBoxOfAttachment(driver, file);
        }
        log.info("Edit_10_Employee_DeleteMultipleAttachments - Step_07: Verify the Delete Selected show up");
        Assertions.assertTrue(getPersonalDetails.isDeleteSelectedButtonDisplay(driver));

        log.info("Edit_10_Employee_DeleteMultipleAttachments - Step_08: Click on 'Delete Selected' button");
        getPersonalDetails.clickOnDeleteSelectedButton(driver);

        log.info("Edit_10_Employee_DeleteMultipleAttachments - Step_09: Click on 'Yes, Delete' in the pop up");
        getPersonalDetails.clickCancelOrDeleteInConfirmDeletePopup(driver,"Yes, Delete");

        log.info("Edit_10_Employee_DeleteMultipleAttachments - Step_10: Verify a success pop up show");
        getPersonalDetails.verifySuccessMessage(driver);

        log.info("Edit_10_Employee_DeleteMultipleAttachments - Step_11: Verify the number of uploaded attachment show 'No Records Found'");
        Assertions.assertEquals("No Records Found", getPersonalDetails.getNumberOfUploadedAttachment(driver));

        for (String file: fileListName){
            int index = fileListName.indexOf(file) + 1;
            log.info("Edit_10_Employee_DeleteMultipleAttachments - Step_12."+index+": Verify the File Name: "+"'" + file + "'" + " is disappeared");
            Assertions.assertTrue(getPersonalDetails.getListAttachmentSizeByFieldAndText(driver,"File Name", file) < 1);
        }
    }
    @AfterClass (alwaysRun = true)
    public void afterClass(){
        log.info("Cleaning up: Closing browser and driver");
        closeBrowserAndDriver();
    }
    private WebDriver driver;
    private LoginPageObject loginPage;
    private MyInfoPageObject myInfo;
    private PersonalDetailsPageObject getPersonalDetails;
    private String comment1, comment2, currentDate, updatedComment;
    private DashboardPageObject homePage;
    private PersonalDetailsData testData;
    String fileMoreThan1MB = "FileMoreThan1MB.pdf";
    String fileLessThan1MB = "FileLessThan1MB.jpeg";
    String txtFile = "TxtFile.txt";
    String docFile = "DocFile.doc";
    String gifImage = "GifImage.gif";
    String jpegImage = "JpegImage.jpeg";
    String pngImage = "PngImage.png";
    String xlsxFile = "XlsxFile.xlsx";
}
