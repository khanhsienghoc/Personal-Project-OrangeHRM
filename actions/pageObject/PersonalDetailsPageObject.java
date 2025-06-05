package pageObject;

import commons.BasePage;
import dataObject.PersonalDetailsData;
import interfaces.MyInfoPageUI;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class PersonalDetailsPageObject extends BasePage {
    private final WebDriver driver;
    private static PersonalDetailsData data;
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
    public String getChosenValueFromDropdownByText(String text){
        waitElementVisible(driver, MyInfoPageUI.DROPDOWN_CHOSEN_VALUE_BY_TEXT, text);
        return getElementText(driver, MyInfoPageUI.DROPDOWN_CHOSEN_VALUE_BY_TEXT, text);
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
        Map<String, String> textFieldsByName = Map.of(
                "firstName",data.getFirstName(),
                "middleName", data.getMiddleName(),
                "lastName", data.getLastName()
        );
        textFieldsByName.forEach((field, value) ->{
            log.info("Input {} field with value '{}'", field, value);
            inputToTextBoxByName(driver, field, value);
        });
        Map<String, String> textFieldsByText = Map.of(
                "Other Id",data.getOtherID(),
                "License Expiry Date", data.getLicenseExpiryDate()
        );
        textFieldsByText.forEach((field, value) ->{
            log.info("Input {} field with value '{}'", field, value);
            inputToTextBoxByText(driver, field, value);
        });
        Map<String, String> dropdowns = Map.of(
                "Nationality", data.getNationality(),
                "Marital Status", data.getMaritalStatus()
        );
        dropdowns.forEach((field, value) ->{
            log.info("Choose {} dropdown with value '{}'", field, value);
            selectValueInDropdownByText(driver, field, value);
        });
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
        Map<String, String> textFieldsByName = Map.of(
                "firstName",data.getFirstName(),
                "middleName", data.getMiddleName(),
                "lastName", data.getLastName()
        );
        textFieldsByName.forEach((field, value) ->{
            log.info("Verify the {} value is '{}'", field, value);
            Assertions.assertEquals(value,getPropertyOfTextBoxByName(driver, "value", field));
        });
        Map<String, String> textFieldsByText = Map.of(
                "Other Id",data.getOtherID(),
                "License Expiry Date", data.getLicenseExpiryDate()
        );
        textFieldsByText.forEach((field, value) ->{
            log.info("Verify the {} value is '{}'", field, value);
            Assertions.assertEquals(value,getPropertyOfTextBoxByText(driver, "value", field));
        });
        Map<String, String> dropdowns = Map.of(
                "Nationality", data.getNationality(),
                "Marital Status", data.getMaritalStatus()
        );
        dropdowns.forEach((field, value) ->{
            log.info("Verify the {} value is '{}'", field, value);
            Assertions.assertEquals(value,getChosenValueFromDropdownByText(field));
        });
        log.info("Verify the Gender value is '{}'", data.getGender());
        Assertions.assertTrue(isRadioButtonSelectedByText(driver, data.getGender()), data.getGender());
    }
}