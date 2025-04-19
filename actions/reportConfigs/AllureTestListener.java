package reportConfigs;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import io.qameta.allure.Attachment;

import java.io.File;

public class AllureTestListener implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    // Screenshot attachments for Allure
    @Attachment(value = "Screenshot of {0}", type = "image/png")
    public static byte[] saveScreenshotPNG(String testName, WebDriver driver) {
        return (byte[]) ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }


    @Override
    public void onTestFailure(ITestResult iTestResult) {
        Object testClass = iTestResult.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriverInstance();
        if (driver != null) {
            if (iTestResult != null && iTestResult.getTestContext() != null) {
                saveScreenshotPNG(iTestResult.getName(), driver);
            }
        } else {
            System.out.println("🚨 WebDriver is null, skipping screenshot.");
        }

    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("=== TEST SUITE STARTED ===");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("⚠️ Test Skipped: " + iTestResult.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }

    @Override
    public void onFinish(ITestContext arg0) {
        System.out.println("=== TEST SUITE FINISHED ===");

    }

    @Override
    public void onTestStart(ITestResult arg0) {
        System.out.println("TEST STARTED"+ arg0.getName());
    }

    @Override
    public void onTestSuccess(ITestResult arg0) {
        System.out.println("✅ Test Passed: " + arg0.getName());
    }


}