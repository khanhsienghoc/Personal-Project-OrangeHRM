package commons;
import interfaces.BasePageUI;
import io.qameta.allure.Step;
import org.checkerframework.checker.fenum.qual.SwingTitlePosition;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.PageGeneratorManager;


import java.time.Duration;
import java.util.Collections;
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
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
        element.sendKeys(text);
    }
    protected void sendKeyToElement(WebDriver driver, String locatorType, String text, String... dynamicValues) {
        WebElement element = getElement(driver, getDynamicXpath(locatorType, dynamicValues));
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
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
    protected List<String> getListElementText(WebDriver driver, String locatorType, String...dynamicValue) {
        return Collections.singletonList(getElement(driver, getDynamicXpath(locatorType, dynamicValue)).getText());
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
    private boolean isElementSelected(WebDriver driver, String locatorType, String...dynamicValues) {
        return getElement(driver, getDynamicXpath(locatorType, dynamicValues)).isSelected();
    }
    protected void checkTheCheckBox(WebDriver driver, String locatorType) {
        if (!isElementSelected(driver, locatorType)) {
            getElement(driver, locatorType).click();
        }
    }
    protected void checkTheCheckBox(WebDriver driver, String locatorType, String... dynamicValue) {
        if (!isElementSelected(driver, getDynamicXpath(locatorType, dynamicValue))) {
            //getElement(driver, getDynamicXpath(locatorType, dynamicValue)).click();
            clickToElementByJS(driver, getDynamicXpath(locatorType, dynamicValue));
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
    protected boolean isElementEnabled(WebDriver driver, String locatorType, String...dynamicValue) {
        return getElement(driver, getDynamicXpath(locatorType, dynamicValue)).isEnabled();
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
        getElement(driver, locatorType).sendKeys(filePath);
    }
    protected void uploadOneFile(WebDriver driver, String locatorType, String filePath, String...dynamicValue) {
        getElement(driver, getDynamicXpath(locatorType, dynamicValue)).sendKeys(filePath);
    }
    protected void scrollToElement(WebDriver driver, WebElement locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", locatorType);
    }
    protected void scrollToElement(WebDriver driver, String locatorType, String...dynamicValue) {
        String dynamicXpath = getDynamicXpath(locatorType, dynamicValue);
        WebElement element = getElement(driver, dynamicXpath);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    protected void clickToElementByJS(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getElement(driver, locatorType);
        if (element != null) {
            jsExecutor.executeScript("arguments[0].click();", element);
        } else {
            throw new NoSuchElementException("Element not found: " + locatorType);
        }
    }
    protected void clickToElementByJS(WebDriver driver, String locatorType, String... dynamicValues) {
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
    protected String getAttributeInDOMByJS(WebDriver driver, String locatorType, String attributeName, String...dynamicValue) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        if ("value".equalsIgnoreCase(attributeName)) {
            return (String) jsExecutor.executeScript("return arguments[0].value;", getElement(driver, getDynamicXpath(locatorType, dynamicValue)));
        }
        return (String) jsExecutor.executeScript("return arguments[0].getAttribute(arguments[1]);", getElement(driver, getDynamicXpath(locatorType, dynamicValue)), attributeName);
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
    protected void waitElementPresence(WebDriver driver, String locatorType, String... dynamicValues) {
        waitForPageLoad(driver);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        explicitWait.until(ExpectedConditions.presenceOfElementLocated(getBylocatorType(getDynamicXpath(locatorType, dynamicValues))));
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
    protected void waitListElementInvisible(WebDriver driver, String locatorType, String...dynamicValue) {
        waitForPageLoad(driver);
        WebDriverWait expilicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        expilicitWait.until(ExpectedConditions.invisibilityOfAllElements(findMulipleElements(driver, getDynamicXpath(locatorType, dynamicValue))));
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
    private long longTimeOut = GlobalConstants.LONG_TIMEOUT;

    /**
     * Inputs text into a textbox located by its name attribute.
     *
     * @param driver WebDriver instance
     * @param textbox   text attribute of the textbox
     * @param text   text to input
     */
    @Step("In the '{1}' field, input the value '{2}'")
    public void inputToTextBoxByText(WebDriver driver, String textbox, String text) {
        waitElementVisible(driver, BasePageUI.TEXTBOX_BY_TEXT, textbox);
        sendKeyToElement(driver, BasePageUI.TEXTBOX_BY_TEXT, text, textbox);
    }
    @Step("In the '{1}' field, input the value '{2}'")
    public void inputToTextBoxByName(WebDriver driver, String name, String text) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        waitListElementInvisible(driver, BasePageUI.LOADING_SPINNER);
        waitElementVisible(driver, BasePageUI.TEXTBOX_BY_NAME, name);
        sendKeyToElement(driver, BasePageUI.TEXTBOX_BY_NAME, text, name);
        //sendkeyToTextBoxByJS(driver, BasePageUI.TEXTBOX_BY_NAME, text, name);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }
    /**
     * Get the error message of a field its name attribute
     *
     * @param driver WebDriver instance
     * @param name   name attribute of the textbox
     * @return String
     */
    @Step("Get the error message of the '{1}' field")
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
    @Step("Click to the '{1}' tab")
    public <T extends BasePage> T clickToMenuByText(WebDriver driver, String text) {
        waitElementVisible(driver, BasePageUI.MENU_BY_TEXT, text);
        clickToElement(driver, BasePageUI.MENU_BY_TEXT, text);
        if (text.equals("PIM")) {
            return (T) PageGeneratorManager.getPIMPage(driver);
        }
        if (text.equals("My Info")) {
            return (T) PageGeneratorManager.getMyInfoPage(driver);
        }
        return (T) this;
    }

    /**
     * Click to Button by text
     * @param driver
     * @param text (text of them locator)
     */
    @Step("Click to '{1}' button")
    public void clickToButtonByText(WebDriver driver, String text){
        waitForElementClickable(driver, BasePageUI.BUTTON_BY_TEXT, text);
        clickToElementByJS(driver, BasePageUI.BUTTON_BY_TEXT, text);
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
    @Step("Click '{1}' options in the Profile options dropdown")
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
    @Step("Get '{1}' attribute the '{2}' textbox")
    public String getPropertyOfTextBoxByText(WebDriver driver, String property, String text){
        waitElementVisible(driver, BasePageUI.TEXTBOX_BY_TEXT, text);
        return getAttributeInDOMByJS(driver, BasePageUI.TEXTBOX_BY_TEXT,property,text);
    }
    public String getPropertyOfTextBoxByName(WebDriver driver, String property, String name){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        waitListElementInvisible(driver, BasePageUI.LOADING_SPINNER);
        waitForPageLoad(driver);
        waitElementVisible(driver, BasePageUI.TEXTBOX_BY_NAME, name);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        return getAttributeValue(driver, BasePageUI.TEXTBOX_BY_NAME,property,name);
    }
    /**
     * Check whether element displays
     * @param driver
     * @param text (text of the locator)
     * @return boolean
     */
    @Step("Verify whether the menu tab name '{1}' displays")
    public boolean isMenuTabDislaysByText(WebDriver driver, String text){
        waitElementVisible(driver, BasePageUI.MENU_BY_TEXT, text);
        return isElementDisplayed(driver, BasePageUI.MENU_BY_TEXT, text);
    }

    /**
     * Get Page Header by text
     * @param driver
     * @param text (text of the locator)
     * @return
     */
    @Step("Get the Page header of '{1}' page")
    public String getPageHeaderByText(WebDriver driver, String text){
        waitElementVisible(driver, BasePageUI.HEADER_PAGE_BY_TEXT, text);
        return getElementText(driver, BasePageUI.HEADER_PAGE_BY_TEXT, text);
    }
    /**
     * Get the size of the Menu tab by Text
     * @param driver
     * @param text (text of the locator)
     * @return
     */
    @Step("Get the size of the Menu Tab by Text '{1}'")
    public int getListMenuTabSize(WebDriver driver, String text){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        int size = getElementsSize(driver, BasePageUI.MENU_BY_TEXT, text);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        return size;
    }

    /**
     * In the dropdown, click on the arrow of the dropdown to show all the dropdown value
     * @param driver
     * @param dropdownText (dropdown name)
     * @param expectedValue
     */
    @Step("In the '{1}' dropdown, click on the arrow of the dropdown to show all the dropdown value")
    public void selectValueInDropdownByText(WebDriver driver, String dropdownText, String expectedValue){
        clickToElement(driver, BasePageUI.DROPDOWN_ARROW_BUTTON_BY_TEXT, dropdownText);
        waitElementVisible(driver, BasePageUI.DROPDOWN_LIST_BOX);
        waitElementVisible(driver, BasePageUI.DROPDOWN_VALUE_BY_TEXT, expectedValue);
        clickToElement(driver, BasePageUI.DROPDOWN_VALUE_BY_TEXT, expectedValue);
    }

    /**
     * Click on the radio by text
     * @param driver
     * @param text (the text of the radio button)
     */
   @Step("Click on the radio '{1}' button")
    public void clickToRadioButtonByText(WebDriver driver, String text){
        waitElementVisible(driver, BasePageUI.RADIO_BUTTON_BY_TEXT, text);
        clickToElement(driver, BasePageUI.RADIO_BUTTON_BY_TEXT, text);
    }
    /**
     * In the header, click on the button related to that section
     * @param driver
     * @param header (text of the locator). Example: "Add Attachments", "Edit Attachments"
     * @param buttonText (text of the locator). Example: "Save", "Cancel"
     */
    @Step("In the header '{1}', click on the '{2}' button")
    public void clickOnButtonByHeaderAndByButtonText(WebDriver driver, String header, String buttonText){
        waitElementVisible(driver, BasePageUI.BUTTON_BY_HEADER_AND_BUTTON_TEXT, header, buttonText);
        clickToElement(driver, BasePageUI.BUTTON_BY_HEADER_AND_BUTTON_TEXT, header, buttonText);
    }

    /**
     * Check whether the radio button name is selected
     * @param driver
     * @param text (text of the locator)
     * @return
     */
    @Step("Check whether the radio button name '{1}' is selected")
    public boolean isRadioButtonSelectedByText(WebDriver driver, String text ){
        waitElementPresence(driver, BasePageUI.CHECKED_RADIO_BUTTON_BY_TEXT,text );
        return isElementSelected(driver, BasePageUI.CHECKED_RADIO_BUTTON_BY_TEXT,text);
    }
    /**
     * Upload the attachment with the file name
     * @param driver
     * @param fileName
     */
    @Step("Upload the attachment with the file name is '{1}'")
    public void addAttachment(WebDriver driver, String fileName){
        waitElementPresence(driver, BasePageUI.UPLOAD_FILE);
        uploadOneFile(driver, BasePageUI.UPLOAD_FILE, GlobalConstants.UPLOAD_FILE + fileName);
    }
    /**
     * Input the value text to the Comment text box of the attachment
     * @param driver
     * @param value
     */
    @Step("Input the value '{1}' to the Comment text box of the attachment")
    public void inputToCommentTextArea(WebDriver driver, String value){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        waitListElementInvisible(driver, BasePageUI.LOADING_SPINNER);
        waitElementVisible(driver, BasePageUI.COMMENT_TEXTAREA);
        sendKeyToElement(driver, BasePageUI.COMMENT_TEXTAREA, value);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
    }
    /**
     * Check whether the text box is enabled
     * @param driver
     * @param text
     * @return
     */
    @Step("Check whether the text box with name is '{1}' is enabled")
    public boolean isTextboxEnabledByText(WebDriver driver, String text){
        waitElementVisible(driver, BasePageUI.TEXTBOX_BY_TEXT, text);
        return isElementEnabled(driver, BasePageUI.TEXTBOX_BY_TEXT, text);
    }
    /**
     * Get the description of the uploaded attachment in the field name with the value of the locator
     * @param driver
     * @param fieldName (field name of the table grid). Example: File Name, Date Added
     * @param text (text of the locator)
     * @return
     */
    @Step("Get the description of the uploaded attachment in the field'{1}' with the value '{2}'")
    public String getFileDescriptionByFieldAndByText(WebDriver driver, String fieldName, String text){
        waitElementVisible(driver, BasePageUI.UPLOADED_FILE_DESCRIPTION_BY_FIELD_AND_TEXT, fieldName, text);
        return getElementText(driver, BasePageUI.UPLOADED_FILE_DESCRIPTION_BY_FIELD_AND_TEXT, fieldName,text);
    }
    /**
     * Get the comment of the uploaded attachment in the Description field by text of the field
     * @param driver
     * @param text
     * @return
     */
    @Step("Get the comment of the uploaded attachment in the Description field with the value '{1}'")
    public String getFileCommentByText(WebDriver driver,String text){
        waitElementVisible(driver, BasePageUI.UPLOADED_FILE_COMMENT_TEXT, text);
        return getElementText(driver, BasePageUI.UPLOADED_FILE_COMMENT_TEXT,text);
    }
    /**
     *Click a button in the Action columns to whether edit, delete or download the attachment by name of the attachment and class of the button
     * @param driver
     * @param fileName: file name that need to edit
     * @param className: class of the button present the action (pencil, download, trash)
     */
    @Step("Click to the icon to Edit/Delete/Download of the file name '{1}'")
    public void clickToActionAttachment(WebDriver driver, String fileName, String className){
        waitElementVisible(driver, BasePageUI.EDIT_ATTACHMENT_BY_FILENAME_AND_BUTTONCLASS,fileName, className);
        clickToElement(driver, BasePageUI.EDIT_ATTACHMENT_BY_FILENAME_AND_BUTTONCLASS,fileName, className);
    }
    /**
     * In the Delete Confirm pop up, choose the option by text of the button
     * @param driver
     * @param text
     */
    @Step("In the Delete Confirm pop up, choose '{1}'")
    public void clickCancelOrDeleteInConfirmDeletePopup(WebDriver driver, String text){
        waitElementVisible(driver, BasePageUI.CONFIRM_DELETE_POPUP);
        clickToElement(driver, BasePageUI.BUTTON_IN_CONFIRM_DELETE_POPUP_BY_TEXT, text);
    }
    /**
     * Get the Attachment List size of the by field name and file name
     * @param driver
     * @param field (field name)
     * @param text (file name)
     * @return
     */
    @Step("Get the Attachment List size of the field name '{1}' and the file name is '{2}'")
    public int getListAttachmentSizeByFieldAndText(WebDriver driver, String field, String text){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        waitListElementInvisible(driver, BasePageUI.UPLOADED_FILE_DESCRIPTION_BY_FIELD_AND_TEXT, field, text);
        int size = getElementsSize(driver, BasePageUI.UPLOADED_FILE_DESCRIPTION_BY_FIELD_AND_TEXT, field, text);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        return size;
    }
    /**
     * In every uploaded attachment record, click on the checkbox by file name
     * @param driver
     * @param fileName
     */
    @Step("In every uploaded attachment record, click on the checkbox of the file name is '{1}'")
    public void checkToTheCheckBoxOfAttachment(WebDriver driver, String fileName){
        waitElementPresence(driver, BasePageUI.CHECKBOX_ATTACHMENT_BY_FILENAME, fileName);
        checkTheCheckBox(driver, BasePageUI.CHECKBOX_ATTACHMENT_BY_FILENAME, fileName);
    }
    /**
     * Check whether the 'Delete Selected' button show up
     * @param driver
     * @return true/false
     */
    @Step("Check whether the 'Delete Selected' button show up")
    public boolean isDeleteSelectedButtonDisplay(WebDriver driver){
        waitElementVisible(driver, BasePageUI.DELETE_SELECTED_BUTTON);
        return isElementDisplayed(driver, BasePageUI.DELETE_SELECTED_BUTTON);
    }
    /**
     * After check on the checkbox of the uploaded attachments, a Delete Selected button show, click on the button
     * @param driver
     */
    @Step("After check on the checkbox of the uploaded attachments, a Delete Selected button show, click on the button")
    public void clickOnDeleteSelectedButton(WebDriver driver){
        waitElementVisible(driver, BasePageUI.DELETE_SELECTED_BUTTON);
        clickToElement(driver, BasePageUI.DELETE_SELECTED_BUTTON);
    }
    /**
     * Get number of uploaded attachments
     * @param driver
     * @return number (int)
     */
    @Step("Get number of uploaded attachments")
    public String getNumberOfUploadedAttachment(WebDriver driver){
        waitElementVisible(driver, BasePageUI.NUMBER_OF_UPLOADED_ATTACHMENT);
        return getElementText(driver, BasePageUI.NUMBER_OF_UPLOADED_ATTACHMENT);
    }
}


