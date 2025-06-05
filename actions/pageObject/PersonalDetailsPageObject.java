package pageObject;

import commons.BasePage;
import dataObject.PersonalDetailsData;
import interfaces.MyInfoPageUI;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

public class PersonalDetailsPageObject extends BasePage {
    private final WebDriver driver;

    public PersonalDetailsPageObject(WebDriver driver){
        this.driver = driver;
    }
    /**
     * Get the selected value from the Nationality dropdown by visible text.
     *
     * @param text The visible text representing the nationality option.
     * @return The chosen nationality value displayed in the dropdown.
     */
    @Step("Get the chosen value from Nationality dropdown by text '{0}'")
    public String getChosenValueFromNationalityDropdownByText(String text){
        waitElementVisible(driver, MyInfoPageUI.NATIONALITY_CHOSEN_VALUE, text);
        return getElementText(driver, MyInfoPageUI.NATIONALITY_CHOSEN_VALUE, text);
    }
    /**
     * Fills out the personal details form with the provided data.
     * This method populates all personal information fields including:
     * first name, middle name, last name, other ID, license expiry date,
     * nationality, marital status, and gender.
     *
     * @param data The PersonalDetailsData object containing all personal information to be filled.
     */
    @Step("Fill personal details form with data: First Name '{data.firstName}', Middle Name '{data.middleName}', Last Name '{data.lastName}', Other ID '{data.otherID}', License Expiry Date '{data.licenseExpiryDate}', Nationality '{data.nationality}', Marital Status '{data.maritalStatus}', Gender '{data.gender}'")
    public void fillPersonalDetailsForm(PersonalDetailsData data) {
        log.info("Input First Name with value '{}'", data.getFirstName());
        inputToTextBoxByName(driver, "firstName", data.getFirstName());

        log.info("Input Middle Name with value '{}'", data.getMiddleName());
        inputToTextBoxByName(driver, "middleName", data.getMiddleName());

        log.info("Input Last Name with value '{}'", data.getLastName());
        inputToTextBoxByName(driver, "lastName", data.getLastName());

        log.info("Input Other ID with value '{}'", data.getOtherID());
        inputToTextBoxByText(driver, "Other Id", data.getOtherID());

        log.info("Input License Expiry Date with value '{}'", data.getLicenseExpiryDate());
        inputToTextBoxByText(driver, "License Expiry Date", data.getLicenseExpiryDate());

        log.info("Choose Nationality with value '{}'", data.getNationality());
        selectValueInDropdownByText(driver, "Nationality", data.getNationality());

        log.info("Choose Marital Status with value '{}'", data.getMaritalStatus());
        selectValueInDropdownByText(driver, "Marital Status", data.getMaritalStatus());

        log.info("Choose Gender with value '{}'", data.getGender());
        clickToRadioButtonByText(driver, data.getGender());
    }
    /**
     * Verifies that all personal details displayed on the form match the expected data.
     * This method validates all personal information fields including:
     * first name, middle name, last name, other ID, license expiry date,
     * nationality, and gender by comparing actual values with expected values.
     *
     * @param data The PersonalDetailsData object containing expected values for verification.
     * @throws AssertionError if any of the actual values don't match the expected values.
     */
    @Step("Verify personal details form with expected data: First Name '{data.firstName}', Middle Name '{data.middleName}', Last Name '{data.lastName}', Other ID '{data.otherID}', License Expiry Date '{data.licenseExpiryDate}', Nationality '{data.nationality}', Gender '{data.gender}'")
    public void verifyPersonalDetails(PersonalDetailsData data){
        log.info("Verify the First Name value is '{}'", data.getFirstName());
        Assertions.assertEquals( data.getFirstName(),getPropertyOfTextBoxByName(driver, "value", "firstName"));

        log.info("Verify the Middle Name value is '{}'", data.getMiddleName());
        Assertions.assertEquals(data.getMiddleName(),getPropertyOfTextBoxByName(driver, "value", "middleName"));

        log.info("Verify the Last Name value is '{}'", data.getLastName());
        Assertions.assertEquals(data.getLastName(),getPropertyOfTextBoxByName(driver, "value", "lastName"));

        log.info("Verify the Other ID value is '{}'", data.getOtherID());
        Assertions.assertEquals(data.getOtherID(),getPropertyOfTextBoxByText(driver, "value", "Other Id"));

        log.info("Verify the License Expiry Date value is '{}'", data.getLicenseExpiryDate());
        Assertions.assertEquals(data.getLicenseExpiryDate(),getPropertyOfTextBoxByText(driver, "value", "License Expiry Date"));

        log.info("Verify the Nationality value is '{}'", data.getNationality());
        Assertions.assertEquals(data.getNationality(),getChosenValueFromNationalityDropdownByText("Nationality"));

        log.info("Verify the Gender value is '{}'", data.getGender());
        Assertions.assertTrue(isRadioButtonSelectedByText(driver, data.getGender()), data.getGender());
    }
}