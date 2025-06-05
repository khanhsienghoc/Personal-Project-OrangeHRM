package interfaces;

public class MyInfoPageUI {
    public static final String HYPERLINK_LEFT_TAB_BY_TEXT = "xpath=//a[contains(., '%s')]";
    public static final String DROPDOWN_CHOSEN_VALUE_BY_TEXT = "xpath=//label[text()='%s']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']";
}
