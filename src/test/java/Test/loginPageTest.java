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
     * @throws InterruptedException 
     */
    @Test(description = "User Login with Invalid Credentials", priority = 2)
    public void loginWithInvalidCredentialsTest() throws IOException, InterruptedException {
        WebDriver driver = browserLaunch.openBrowser(); // New independent browser instance

        try {
            loginPage zygalLoginPage = new loginPage(driver);

            // **Step 1: Test Invalid Email**
            String invalidUserEmail = parameterization.getData("loginData", 2, 0);
            zygalLoginPage.enterUserId(invalidUserEmail);
            zygalLoginPage.clickOnGetOTP();

            String errorMessage = zygalLoginPage.getErrorText();
            Assert.assertFalse(errorMessage.isEmpty(), "Expected login error message was not displayed for invalid email!");

            // Log step in the test report
            testReportListener.logSubStep("", "Invalid Email Attempt", "PASSED", "Error Message -> "+zygalLoginPage.getErrorText());

            // **Step 2: Not registered email with zygal platform**
            zygalLoginPage.clearUserEmailField();
            
            String notRegisteredEmail = parameterization.getData("loginData", 3, 0);
            zygalLoginPage.enterUserId(notRegisteredEmail);
            zygalLoginPage.clickOnGetOTP();
            
            Thread.sleep(1000);
            errorMessage = zygalLoginPage.getErrorText();
            Assert.assertFalse(errorMessage.isEmpty(), "Expected login error message was not displayed for invalid email!");

            // Log step in the test report
            testReportListener.logSubStep("", "Not Registered Email Attempt", "PASSED", "Error Message -> "+zygalLoginPage.getErrorText());
            
         // **Step 3: Test Invalid OTP**
            zygalLoginPage.clearUserEmailField();
            
            String validUsername = parameterization.getData("loginData", 4, 0); // Correct email
            String invalidOtp = parameterization.getData("loginData", 2, 1); // Incorrect OTP

            zygalLoginPage.enterUserId(validUsername);
            zygalLoginPage.clickOnGetOTP();
            Thread.sleep(200);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(zygalLoginPage.getOtpField1()));

            zygalLoginPage.enterOTP(invalidOtp);
            zygalLoginPage.clickOnLogin();

            errorMessage = zygalLoginPage.getErrorText();
            Assert.assertFalse(errorMessage.isEmpty(), "Expected error message was not displayed for invalid OTP!");

            testReportListener.logSubStep("", "Invalid OTP Attempt", "PASSED", "Error Message -> "+zygalLoginPage.getErrorText());
            
            //**Step 4: Account block after 5 attempts of wrong otp entering**
       
            for (int i = 1; i <= 5; i++) {
                zygalLoginPage.enterOTP(invalidOtp);
                zygalLoginPage.clickOnLogin();
                Thread.sleep(500);
                errorMessage = zygalLoginPage.getErrorText();
                if (i == 5) {
                    testReportListener.logSubStep("","Account Blocked After Multiple Wrong OTPs", "PASSED", errorMessage);
                }
            }
            

            // **Step 5: Click "Go to Sign In" and verify navigation**
            zygalLoginPage.closeErrorToast(); // Close error toast message
            zygalLoginPage.clickGoToSignIn(); // Click "Go to Sign In" button

            boolean isOnGetOTPPage = zygalLoginPage.isOnGetOTPPage();
            Assert.assertTrue(isOnGetOTPPage, "Navigation to Get OTP page failed after clicking Go to Sign In");

            testReportListener.logSubStep("", "Click Go to Sign In", "PASSED", "Navigated back to Get OTP page successfully");
            
            //**Step 6: Verify account block will not be able to get otp or shouldn't navigate to otp page**//
            zygalLoginPage.enterUserId(validUsername);
            Thread.sleep(100);
            zygalLoginPage.clickOnGetOTP();

            String expectedErrorMessage = "You have reached the maximum login attempts for the day. Please try again after 24 hours.";
            String actualErrorMessage = zygalLoginPage.getErrorText();

            Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message does not match after re-entering the email!");

            testReportListener.logSubStep("", "Account block error message on Get OTP page", "PASSED", "Error Message -> " + actualErrorMessage);

        } finally {
            // driver.quit(); // Close the browser after test execution
        }
    }
}
