package commons;
import interfaces.BasePageUI;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.PageGeneratorManager;


import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class BasePage {
    public static BasePage BasePageObject() {
        return new BasePage();
    }
    protected void openURL(WebDriver driver, String url) {
        driver.get(url);
        waitForPageLoad(driver);
    }
    public String getPageTitle(WebDriver driver) {
        waitForPageLoad(driver);
        return driver.getTitle();
    }
    protected String getCurrentURL(WebDriver driver) {
        return driver.getCurrentUrl();
    }
    protected String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }
    protected void backToPage(WebDriver driver) {
        driver.navigate().back();
    }
    protected void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }
    protected void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }
    protected Alert waitForAlertPresence(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }
    protected void acceptAlert(WebDriver driver) {
        waitForAlertPresence(driver).accept();
    }
    protected void dismissAlert(WebDriver driver) {
        waitForAlertPresence(driver).dismiss();
    }
    protected String getAlertText(WebDriver driver) {
        return waitForAlertPresence(driver).getText();
    }
    protected String sendKeyTextToAlert(WebDriver driver, String text) {
        waitForAlertPresence(driver).sendKeys(text);
        return text;
    }
    protected void switchToWindowByID(WebDriver driver, String windowID) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!Objects.equals(window, windowID)) {
                driver.switchTo().window(windowID);
                break;
            }
        }
    }
    protected void switchToWindowByTitle(WebDriver driver, String tabTitle) {
        Set<String> allWindowsID = driver.getWindowHandles();
        for (String windowID : allWindowsID) {
            driver.switchTo().window(windowID);
            String actualTitle = driver.getTitle();
            if (Objects.equals(actualTitle, tabTitle)) {
                break;
            }
        }
    }
    protected void closeAllTabWithoutParent(WebDriver driver, String parentID) {
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String windowID : allWindowIDs) {
            if (!Objects.equals(windowID, parentID)) {
                driver.switchTo().window(windowID);
                driver.close();
            }
            driver.switchTo().window(parentID);
        }

    }
    private By getBylocatorType(String locatorType) {
        By by = null;
        if (locatorType.startsWith("id=") || locatorType.startsWith("ID=") || locatorType.startsWith("Id=")) {
            by = By.id(locatorType.substring(3));
        } else if (locatorType.startsWith("class=") || locatorType.startsWith("Class=") || locatorType.startsWith("CLASS=")) {
            by = By.className(locatorType.substring(6));
        } else if (locatorType.startsWith("name=") || locatorType.startsWith("Name=") || locatorType.startsWith("NAME=")) {
            by = By.name(locatorType.substring(5));
        } else if (locatorType.startsWith("xpath=") || locatorType.startsWith("Xpath=") || locatorType.startsWith("XPATH=")) {
            by = By.xpath(locatorType.substring(6));
        } else if (locatorType.startsWith("css=") || locatorType.startsWith("Css=") || locatorType.startsWith("CSS=")) {
            by = By.cssSelector(locatorType.substring(4));
        } else {
            throw new RuntimeException("locatorType type is not support");
        }
        return by;
    }

    private WebElement getElement(WebDriver driver, String locatorType) {
        return driver.findElement(getBylocatorType(locatorType));
    }
    public List<WebElement> getListElement(WebDriver driver, String locatorType) {
        return driver.findElements(getBylocatorType(locatorType));
    }
    public List<WebElement> getListElement(WebDriver driver, String locatorType, String... dynamicValue) {
        return driver.findElements(getBylocatorType(getDynamicXpath(locatorType, dynamicValue)));
    }


    private String getDynamicXpath(String locatorType, String... values) {
        if (locatorType.startsWith("xpath=") || locatorType.startsWith("Xpath=") || locatorType.startsWith("XPATH=")) {
            locatorType = String.format(locatorType, (Object[]) values);
        }
        return locatorType;
    }
    protected void clickToElement(WebDriver driver, String locatorType) {
        getElement(driver, locatorType).click();
    }

    protected void clickToElement(WebDriver driver, String locatorType, String... dynamicValues) {
        getElement(driver, getDynamicXpath(locatorType, dynamicValues)).click();
    }

    protected void sendKeyToElement(WebDriver driver, String locatorType, String text) {
        WebElement element = getElement(driver, locatorType);
        element.clear();
        element.sendKeys(text);
    }
    protected void sendKeyToElement(WebDriver driver, String locatorType, String text, String... dynamicValues) {
        WebElement element = getElement(driver, getDynamicXpath(locatorType, dynamicValues));
        element.clear();
        element.sendKeys(text);

    }
    protected void waitForPageLoad(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    protected String getElementText(WebDriver driver, String locatorType) {
        return getElement(driver, locatorType).getText();
    }
    protected String getElementText(WebDriver driver, String locatorType, String... dynamicValues) {
        return getElement(driver, getDynamicXpath(locatorType, dynamicValues)).getText();
    }

    protected void selectItemInDefaultDropDown(WebDriver driver, String locatorType, String text) {
        Select select = new Select(getElement(driver, locatorType));
        select.selectByVisibleText(text);
    }
    protected void selectItemInDefaultDropDown(WebDriver driver, String locatorType, String text, String... dynamicValue) {
        Select select = new Select(getElement(driver, getDynamicXpath(locatorType, dynamicValue)));
        select.selectByVisibleText(text);
    }

    protected String getFirstSelectedItemDefaultDropDown(WebDriver driver, String locatorType) {
        Select select = new Select(getElement(driver, locatorType));
        return select.getFirstSelectedOption().getText();
    }
    protected boolean isDropDownMultiple(WebDriver driver, String locatorType) {
        Select select = new Select(getElement(driver, locatorType));
        return select.isMultiple();
    }
    protected void selectItemInCustomDropDown(WebDriver driver, String parentLocator, String childLocator, String expectValue) {
        getElement(driver, parentLocator).click();
        sleepInSecond(1);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        List<WebElement> allItems = explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getBylocatorType(childLocator)));
        for (WebElement item : allItems) {
            if (item.getText().equals(expectValue)) {
                scrollToElement(driver, item);
                sleepInSecond(1);
                item.click();
                break;
            }
        }
    }
    protected void selectItemInCustomDropDown(WebDriver driver, String parentLocator, String childLocator, String expectValue, String... dynamicValue) {
        getElement(driver, getDynamicXpath(parentLocator, dynamicValue)).click();
        sleepInSecond(1);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        List<WebElement> allItems = explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getBylocatorType(getDynamicXpath(childLocator, dynamicValue))));
        for (WebElement item : allItems) {
            if (item.getText().equals(expectValue)) {
                scrollToElement(driver, item);
                sleepInSecond(1);
                item.click();
                break;
            }
        }
    }
    protected String getAttributeValue(WebDriver driver, String locatorType, String attributeName) {
        return getElement(driver, locatorType).getDomProperty(attributeName);
    }
    protected String getAttributeValue(WebDriver driver, String locatorType, String attributeName, String... dynamicValues) {
        return getElement(driver, getDynamicXpath(locatorType, dynamicValues)).getDomProperty(attributeName);
    }
    protected String getCSSCValue(WebDriver driver, String locatorType, String propertyName) {
        return getElement(driver, locatorType).getCssValue(propertyName);
    }
    protected String getCSSCValue(WebDriver driver, String locatorType, String propertyName, String... dynamicValues) {
        return getElement(driver, getDynamicXpath(locatorType, dynamicValues)).getCssValue(propertyName);
    }
    protected String getHexaColorFromRGBA(String rgbaValue) {
        return Color.fromString(rgbaValue).asHex();
    }
    private List<WebElement> findMulipleElements(WebDriver driver, String locatorTypes) {
        return driver.findElements(getBylocatorType(locatorTypes));
    }
    protected int getElementsSize(WebDriver driver, String locatorTypes) {
        return findMulipleElements(driver, locatorTypes).size();
    }
    protected int getElementsSize(WebDriver driver, String locatorTypes, String... dynamicValues) {
        return findMulipleElements(driver, getDynamicXpath(locatorTypes, dynamicValues)).size();
    }
    private boolean isElementSelected(WebDriver driver, String locatorType) {
        return getElement(driver, locatorType).isSelected();
    }
    protected void checkTheCheckBox(WebDriver driver, String locatorType) {
        if (!isElementSelected(driver, locatorType)) {
            getElement(driver, locatorType).click();
        }
    }
    protected void checkTheCheckBox(WebDriver driver, String locatorType, String... dynamicValue) {
        if (!isElementSelected(driver, getDynamicXpath(locatorType, dynamicValue))) {
            getElement(driver, getDynamicXpath(locatorType, dynamicValue)).click();
        }
    }

    protected void uncheckTheCheckBox(WebDriver driver, String locatorType) {
        if (isElementSelected(driver, locatorType)) {
            getElement(driver, locatorType).click();
        }
    }
    protected void uncheckTheCheckBox(WebDriver driver, String locatorType, String... dynamicValue) {
        if (isElementSelected(driver, getDynamicXpath(locatorType, dynamicValue))) {
            getElement(driver, getDynamicXpath(locatorType, dynamicValue)).click();
        }
    }
    protected boolean isElementDisplayed(WebDriver driver, String locatorType) {
        return getElement(driver, locatorType).isDisplayed();
    }
    protected boolean isElementDisplayed(WebDriver driver, String locatorType, String... dynamicValues) {
        return getElement(driver, getDynamicXpath(locatorType, dynamicValues)).isDisplayed();
    }
    protected boolean isElementEnabled(WebDriver driver, String locatorType) {
        return getElement(driver, locatorType).isEnabled();
    }
    protected void switchToFrame(WebDriver driver, String locatorType) {
        driver.switchTo().frame(getElement(driver, locatorType));
    }
    protected void switchtoDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }
    protected void moveToElement(WebDriver driver, String locatorType) {
        Actions actions = new Actions(driver);
        actions.moveToElement(getElement(driver, locatorType)).perform();
    }
    protected void moveToElement(WebDriver driver, String locatorType, String... dynamicValues) {
        Actions actions = new Actions(driver);
        actions.moveToElement(getElement(driver, getDynamicXpath(locatorType, dynamicValues))).perform();
    }
    protected void doubleClickOnElements(WebDriver driver, String locatorType) {
        Actions action = new Actions(driver);
        action.doubleClick(getElement(driver, locatorType)).perform();
    }
    protected void rightClick(WebDriver driver, String locatorType) {
        Actions action = new Actions(driver);
        action.contextClick(getElement(driver, locatorType)).perform();
    }
    protected void dragAndDropFromAndTo(WebDriver driver, String locatorTypeFrom, String locatorTypeTo) {
        Actions action = new Actions(driver);
        action.dragAndDrop(getElement(driver, locatorTypeFrom), getElement(driver, locatorTypeTo)).perform();
    }
    protected void pressKeyToElemnt(WebDriver driver, String locatorType, Keys key) {
        Actions action = new Actions(driver);
        action.sendKeys(getElement(driver, locatorType), key).perform();
    }
    protected void pressKeyToElemnt(WebDriver driver, String locatorType, Keys key, String... dynamicValue) {
        Actions action = new Actions(driver);
        action.sendKeys(getElement(driver, getDynamicXpath(locatorType, dynamicValue)), key).perform();
    }
    protected void uploadOneFile(WebDriver driver, String locatorType, String filePath) {
        getElement(driver, locatorType).sendKeys(commons.GlobalConstants.UPLOAD_FILE);
    }
    protected void scrollToElement(WebDriver driver, WebElement locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", locatorType);
    }
    protected void clickToElementByJavascript(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getElement(driver, locatorType);
        if (element != null) {
            jsExecutor.executeScript("arguments[0].click();", element);
        } else {
            throw new NoSuchElementException("Element not found: " + locatorType);
        }
    }
    protected void clickToElementByJavascript(WebDriver driver, String locatorType, String... dynamicValues) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getElement(driver, getDynamicXpath(locatorType, dynamicValues));
        if (element != null) {
            jsExecutor.executeScript("arguments[0].click();", element);
        } else {
            throw new NoSuchElementException("Element not found: " + locatorType);
        }
    }
    protected void hightlightElement(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getElement(driver, locatorType);
        String originalStyle = element.getDomProperty("style");
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
        sleepInSecond(2);
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
    }
    protected void scrollToElementOnTopByJS(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(driver, locatorType));
    }
    protected void scrollToElementOnDownByJS(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(driver, locatorType));
    }
    protected void scrollToBottomPageByJS(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }
    protected void setAttributeInDOM(WebDriver driver, String locatorType, String attributeName, String attributeValue) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].setAttribute('" + attributeName + "', '" + attributeValue + "');", getElement(driver, locatorType));
    }
    protected void removeAttributeInDOM(WebDriver driver, String locatorType, String attributeRemove) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(driver, locatorType));
    }
    protected void removeAttributeInDOM(WebDriver driver, String locatorType, String attributeRemove, String... dynamicValues) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(driver, getDynamicXpath(locatorType, dynamicValues)));
    }
    protected String getAttributeInDOMByJS(WebDriver driver, String locatorType, String attributeName) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return arguments[0].getAttribute('" + attributeName + "');", getElement(driver, locatorType));
    }
    protected String getElementValidationMessage(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(driver, locatorType));
    }
    protected boolean isImageLoaded(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (boolean) jsExecutor.executeScript("return arguments[0].complete " + "&& typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
                getElement(driver, locatorType));
    }
    protected void waitElementVisible(WebDriver driver, String locatorType) {
        waitForPageLoad(driver);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getBylocatorType(locatorType)));
    }
    protected void waitElementVisible(WebDriver driver, String locatorType, String... dynamicValues) {
        waitForPageLoad(driver);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getBylocatorType(getDynamicXpath(locatorType, dynamicValues))));
    }
    protected void waitListElementVisible(WebDriver driver, String locatorType) {
        waitForPageLoad(driver);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        explicitWait.until(ExpectedConditions.visibilityOfAllElements(findMulipleElements(driver, locatorType)));
    }
    protected void waitListElementVisible(WebDriver driver, String locatorType, String... dynamicValues) {
        waitForPageLoad(driver);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        explicitWait.until(ExpectedConditions.visibilityOfAllElements(findMulipleElements(driver, getDynamicXpath(locatorType, dynamicValues))));
    }
    protected void waitElementInvisible(WebDriver driver, String locatorType) {
        waitForPageLoad(driver);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getBylocatorType(locatorType)));
    }
    protected void waitListElementInvisible(WebDriver driver, String locatorType) {
        waitForPageLoad(driver);
        WebDriverWait expilicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        expilicitWait.until(ExpectedConditions.invisibilityOfAllElements(findMulipleElements(driver, locatorType)));
    }
    protected void waitForElementSelected(WebDriver driver, String locatorType) {
        waitForPageLoad(driver);
        WebDriverWait expilicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        expilicitWait.until(ExpectedConditions.elementToBeClickable(getBylocatorType(locatorType)));
    }
    protected void waitForElementClickable(WebDriver driver, String locatorType) {
        waitForPageLoad(driver);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        explicitWait.until(ExpectedConditions.elementToBeClickable(getBylocatorType(locatorType)));
    }
    protected void waitForElementClickable(WebDriver driver, String locatorType, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        explicitWait.until(ExpectedConditions.elementToBeClickable(getBylocatorType(getDynamicXpath(locatorType, dynamicValues))));
    }
    protected boolean isElementClickable(WebDriver driver, String locatorType) {
        try {
            WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
            explicitWait.until(ExpectedConditions.elementToBeClickable(getBylocatorType(locatorType)));
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    public void sleepInSecond(long time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }
    /**
     * Get all cookies at the current page
     * @param driver
     * @return
     */
    public Set<Cookie> getAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }
    /**
     * Set cookies to the current page
     * @param driver
     * @param cookies
     */
    public void setCookies(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
        sleepInSecond(3);
    }
    private long longTimeOut = GlobalConstants.LONG_TIMEOUT;

    /**
     * Inputs text into a textbox located by its name attribute.
     *
     * @param driver WebDriver instance
     * @param texbox   text attribute of the textbox
     * @param text   text to input
     */
    @Step("In the '{1}' field, input the value '{2}'")
    public void inputToTextBoxByText(WebDriver driver, String texbox, String text) {
        waitElementVisible(driver, BasePageUI.TEXTBOX_BY_TEXT, texbox);
        sendKeyToElement(driver, BasePageUI.TEXTBOX_BY_TEXT, text, texbox);
    }

    /**
     * Get the error message of a field its name attribute
     *
     * @param driver WebDriver instance
     * @param name   name attribute of the textbox
     * @return String
     */
    @Step("Get the error message of the field {1}")
    public String getErrorMessageByName(WebDriver driver, String name) {
        waitElementVisible(driver, BasePageUI.ERROR_MESSAGE_BY_TEXTBOX_NAME, name);
        return getElementText(driver, BasePageUI.ERROR_MESSAGE_BY_TEXTBOX_NAME, name);
    }

    /**
     * Click to the Menu tab by Name
     * @param driver
     * @param text
     */
    @SuppressWarnings("unchecked")
    @Step("Click to the {1} tab")
    public <T extends BasePage> T clickToMenuByText(WebDriver driver, String text) {
        waitElementVisible(driver, BasePageUI.MENU_BY_TEXT, text);
        clickToElement(driver, BasePageUI.MENU_BY_TEXT, text);

        if (text.equals("PIM")) {
            return (T) PageGeneratorManager.getPIMPage(driver);
        }

        return (T) this;
    }

    /**
     * Click to Button by text
     * @param driver
     * @param text (text of them locator)
     */
    @Step("Click to {1} button")
    public void clickToButtonByText(WebDriver driver, String text){
        waitElementVisible(driver, BasePageUI.BUTTON_BY_TEXT, text);
        clickToElement(driver, BasePageUI.BUTTON_BY_TEXT, text);
    }
    /**
     * Check whether the success pop up shows
     * @param driver
     * @return boolean
     */
    @Step("Check whether the success pop up show")
    public boolean isSuccessPopUpShow(WebDriver driver){
        waitElementVisible(driver, BasePageUI.SUCCESS_SAVE_POPUP);
        return isElementDisplayed(driver, BasePageUI.SUCCESS_SAVE_POPUP);
    }
    /**
     * Click on Profile Dropdown
     * @param driver
     */
    @Step("Click on Profile Dropdown")
    public void clickOnProfileDropdown(WebDriver driver){
        waitForElementClickable(driver, BasePageUI.PROFILE_DROPDOWN);
        clickToElement(driver, BasePageUI.PROFILE_DROPDOWN);
    }
    /**
     * Click on Profile Option by text
     * @param driver
     * @param text (text of the locator)
     */
    @Step("Click {1} options in the Profile options dropdown")
    public void clickOnProfileOptionByText(WebDriver driver, String text){
        waitForElementClickable(driver, BasePageUI.PROFILE_OPTION_BY_TEXT, text);
        clickToElement(driver, BasePageUI.PROFILE_OPTION_BY_TEXT, text);
    }
    /**
     * Get property of a locator by text box
     * @param driver
     * @param text (text of the TextBox locator)
     * @param property (property that want to get)
     * @return String
     */
    @Step("Get {1} attribute the {2} textbox")
    public String getPropertyOfTextBoxByName(WebDriver driver,String property, String text){
        waitElementVisible(driver, BasePageUI.TEXTBOX_BY_TEXT, text);
        return getAttributeValue(driver, BasePageUI.TEXTBOX_BY_TEXT,property,text);
    }

    /**
     * Check whether element displays
     * @param driver
     * @param text (text of the locator)
     * @return boolean
     */
    @Step("Verify whether the menu tab name {1} displays")
    public boolean isMenuTabDislaysByText(WebDriver driver, String text){
        waitElementVisible(driver, BasePageUI.MENU_BY_TEXT, text);
        return isElementDisplayed(driver, BasePageUI.MENU_BY_TEXT, text);
    }
}


