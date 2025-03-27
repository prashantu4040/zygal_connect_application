package Test;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import POM.loginPage;
import Utility.parameterization;
import Utility.browserLaunch;
import Utility.testReportListener;

/**
 * This class contains test cases for login functionality using Selenium WebDriver and TestNG.
 */
public class loginPageTest {

    /**
     * Common method to perform login steps.
     */
    public String performLogin(WebDriver driver, String username, String otp) {
        loginPage zygalLoginPage = new loginPage(driver);

        // Entering username and clicking on "Get OTP"
        zygalLoginPage.enterUserId(username);
        zygalLoginPage.clickOnGetOTP();

        // Checking for error message after requesting OTP
        String errorMessage = zygalLoginPage.getErrorText();
        if (!errorMessage.isEmpty()) {
            return errorMessage;
        }

        // Wait for OTP field to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(zygalLoginPage.getOtpField1()));

        // Entering OTP and clicking login
        zygalLoginPage.enterOTP(otp);
        zygalLoginPage.clickOnLogin();

        // Checking for error message after entering OTP
        return zygalLoginPage.getErrorText();
    }

    /**
     * Test: Login with valid credentials.
     */
    @Test(description = "User Login with Valid Credentials", priority = 1)
    public void loginWithValidCredentialsTest(ITestContext context) throws IOException {
        WebDriver driver = browserLaunch.openBrowser(); // New browser instance

        try {
            String username = parameterization.getData("loginData", 1, 0);
            String otp = parameterization.getData("loginData", 1, 1);

            // Perform login and capture any error message
            String errorMessage = performLogin(driver, username, otp);

            if (!errorMessage.isEmpty()) {
                Assert.fail("Test failed due to login error: " + errorMessage);
            } else {
                loginPage zygalLoginPage = new loginPage(driver);
                Assert.assertTrue(zygalLoginPage.isFooterVisible(), "Login Test Failed! Footer is not visible.");

                // If login succeeds, store driver in the TestNG context for use in the next test class
                context.setAttribute("WebDriver", driver);
            }
        } catch (Exception e) {
            driver.quit();
            throw e;
        }
    }

    /**
     * Test: Login with invalid credentials.
     */
    @Test(description = "User Login with Invalid Credentials", priority = 2)
    public void loginWithInvalidCredentialsTest() throws IOException {
        WebDriver driver = browserLaunch.openBrowser(); // New independent browser instance

        try {
            loginPage zygalLoginPage = new loginPage(driver);

            // **Step 1: Test Invalid Email**
            String invalidUsername = parameterization.getData("loginData", 2, 0);
            zygalLoginPage.enterUserId(invalidUsername);
            zygalLoginPage.clickOnGetOTP();

            String errorMessage = zygalLoginPage.getErrorText();
            Assert.assertFalse(errorMessage.isEmpty(), "Expected login error message was not displayed for invalid email!");

            // Log step in the test report
            testReportListener.logSubStep("User Login with Invalid Credentials", "Invalid Email Attempt", "PASSED", "Error displayed correctly");

            // **Step 2: Clear the input field and verify it's empty**
            zygalLoginPage.clearUserIdField();
            String fieldValue = zygalLoginPage.getUserIdFieldValue();
            Assert.assertTrue(fieldValue.isEmpty(), "User ID field is not cleared before entering valid email!");

            testReportListener.logSubStep("User Login with Invalid Credentials", "Clear User ID Field", "PASSED", "Field cleared successfully");

            // **Step 3: Test Invalid OTP**
            String validUsername = parameterization.getData("loginData", 1, 0); // Correct email
            String invalidOtp = parameterization.getData("loginData", 2, 1); // Incorrect OTP

            zygalLoginPage.enterUserId(validUsername);
            zygalLoginPage.clickOnGetOTP();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(zygalLoginPage.getOtpField1()));

            zygalLoginPage.enterOTP(invalidOtp);
            zygalLoginPage.clickOnLogin();

            errorMessage = zygalLoginPage.getErrorText();
            Assert.assertFalse(errorMessage.isEmpty(), "Expected error message was not displayed for invalid OTP!");

            testReportListener.logSubStep("User Login with Invalid Credentials", "Invalid OTP Attempt", "PASSED", "Error displayed correctly for invalid OTP");

            // **Step 4: Click "Go to Sign In" and verify navigation**
            zygalLoginPage.closeErrorToast(); // Close error toast message
            zygalLoginPage.clickGoToSignIn(); // Click "Go to Sign In" button

            boolean isOnGetOTPPage = zygalLoginPage.isOnGetOTPPage();
            Assert.assertTrue(isOnGetOTPPage, "Navigation to Get OTP page failed after clicking 'Go to Sign In'!");

            testReportListener.logSubStep("User Login with Invalid Credentials", "Click 'Go to Sign In'", "PASSED", "Navigated back to Get OTP page successfully");

        } finally {
            driver.quit(); // Close the browser after test execution
        }
    }
}
