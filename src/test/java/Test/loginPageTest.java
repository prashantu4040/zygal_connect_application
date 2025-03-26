package Test;

import java.io.IOException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import POM.loginPage;
import Utility.parameterization;
import java.time.Duration;

/**
 * This class contains test cases for login functionality using Selenium WebDriver and TestNG.
 */
public class loginPageTest extends baseTest {

    /**
     * This method initializes the browser before each test case execution.
     * It calls the `openBrowser()` method from the Utility class to launch the browser.
     */
    @BeforeMethod
    public void browserLaunch() {
        driver = Utility.browserLaunch.openBrowser();
    }

    /**
     * This test verifies that a user can log in with valid credentials.
     * It retrieves test data from an external source, performs login, and validates the outcome.
     *
     * @throws IOException if there's an issue reading test data from the external source.
     */
    @Test(description = "User Login with Valid Credentials")
    public void loginWithValidCredentialsTest() throws IOException {
        loginPage zygalLoginPage = new loginPage(driver);

        // Fetching test data (username & OTP) from the parameterization utility
        String username = parameterization.getData("loginData", 1, 0);
        String otp = parameterization.getData("loginData", 1, 1);

        // Entering the username and clicking on "Get OTP"
        zygalLoginPage.enterUserId(username);
        zygalLoginPage.clickOnGetOTP();

        // Checking if an error message appears after requesting OTP
        String errorMessage = zygalLoginPage.getErrorText();
        if (!errorMessage.isEmpty()) {
            Assert.fail("Test failed due to login error: " + errorMessage);
        } else {
            // Using explicit wait to ensure the OTP input field is visible before proceeding
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(zygalLoginPage.getOtpField1()));

            // Entering OTP and clicking the login button
            zygalLoginPage.enterOTP(otp);
            zygalLoginPage.clickOnLogin();

            // Checking if an error message appears after entering OTP
            errorMessage = zygalLoginPage.getErrorText();
            if (!errorMessage.isEmpty()) {
                Assert.fail("Test failed due to OTP error: " + errorMessage);
            }

            // Verifying login success by checking if the footer is visible (indicating successful login)
            boolean loginSuccess = zygalLoginPage.isFooterVisible();
            Assert.assertTrue(loginSuccess, "Login Test Failed! Footer is not visible.");
        }
    }
}
