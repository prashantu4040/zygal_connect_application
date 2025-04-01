package Test;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import POM.loginPage;
import Utility.parameterization;
import Utility.browserLaunch;

/**
 * This class contains test cases for login functionality using Selenium
 * WebDriver and TestNG.
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

				// If login succeeds, store driver in the TestNG context for use in the next
				// test class
				context.setAttribute("validWebDriver", driver);
			}
		} catch (Exception e) {
			driver.quit();
			throw e;
		}
	}

	/**
	 * Test: Login with invalid credentials.
	 * 
	 * @throws InterruptedException
	 */
	@Test(priority = 2)
	public void loginWithInvalidCredentialsTest(ITestContext context) throws IOException, InterruptedException {
		WebDriver driver = browserLaunch.openBrowser(); // New independent browser instance
		context.setAttribute("WebDriver", driver);
	}
	
	@Test(description = "Verify Login with Invalid Email", dependsOnMethods  = "loginWithInvalidCredentialsTest")
	public void loginWithInvalidEmail(ITestContext context) throws EncryptedDocumentException, IOException {
		WebDriver driver = (WebDriver) context.getAttribute("WebDriver");
		loginPage zygalLoginPage = new loginPage(driver);
		
		String userEmail = parameterization.getData("loginData", 2, 0);
		zygalLoginPage.enterUserId(userEmail);
		zygalLoginPage.clickOnGetOTP();
		String errorMessage = zygalLoginPage.getErrorText();
		Assert.assertFalse(errorMessage.isEmpty(), "Failed to verify invalid email --> "+ errorMessage);
		
	}
	
	@Test(description = "Verify Login with Not Registred Email", dependsOnMethods = "loginWithInvalidEmail")
	public void loginWithNotRegisteredEmail(ITestContext context) throws EncryptedDocumentException, IOException {
		WebDriver driver = (WebDriver) context.getAttribute("WebDriver");
		loginPage zygalLoginPage = new loginPage(driver);
		
		zygalLoginPage.clearUserEmailField();
		String userEmail = parameterization.getData("loginData", 3, 0);
		zygalLoginPage.enterUserId(userEmail);
		String errorMessage = zygalLoginPage.getErrorText();
		Assert.assertFalse(errorMessage.isEmpty(), "Failed to verify not Registered email --> " + errorMessage);
		
	}
	
	@Test(description = "Verify Login with Invalid OTP", dependsOnMethods = "loginWithNotRegisteredEmail")
	public void loginwithInvalidOTP(ITestContext context) throws EncryptedDocumentException, IOException {
		WebDriver driver = (WebDriver) context.getAttribute("WebDriver");
		loginPage zygalLoginPage = new loginPage(driver);
		
		zygalLoginPage.clearUserEmailField();
		String userEmail = parameterization.getData("loginData", 4, 0);
		String otp = parameterization.getData("loginData", 2, 1);
		zygalLoginPage.enterUserId(userEmail);
		zygalLoginPage.clickOnGetOTP();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(zygalLoginPage.getOtpField1()));
		zygalLoginPage.enterOTP(otp);
		zygalLoginPage.clickOnLogin();
		
		String errorMessage = zygalLoginPage.getErrorText();
		Assert.assertFalse(errorMessage.isEmpty(), "Faled to verify invalid otp --> " + errorMessage);
	}
	
	@Test(description = "Verify account block after attempting wrong OTP for 5 times", dependsOnMethods = "loginwithInvalidOTP")
	public void verifyAccountBlock(ITestContext context) throws EncryptedDocumentException, IOException, InterruptedException {
		WebDriver driver = (WebDriver) context.getAttribute("WebDriver");
		loginPage zygalLoginPage = new loginPage(driver);
		
		String otp = parameterization.getData("loginData", 2, 1);
		
		for (int i = 1; i<=5; i++) {
			zygalLoginPage.enterOTP(otp);
			Thread.sleep(1000);
			zygalLoginPage.clickOnLogin();
			Thread.sleep(1000);
			zygalLoginPage.closeErrorToast();
		}
		String errorMessage = zygalLoginPage.getErrorText();
		if (!errorMessage.equals("You have reached the maximum login attempts for the day. Please try again after 24 hours.")) {
			Assert.assertFalse(errorMessage.isEmpty(), "Account block verification failed --> " + errorMessage);
		}
	}
	
	@Test(description = "Verify Go To Sign In page Navigation", dependsOnMethods = "verifyAccountBlock")
	public void verifyGoToSignPage(ITestContext context) {
		WebDriver driver = (WebDriver) context.getAttribute("WebDriver");
		loginPage zygalLoginPage = new loginPage(driver);
		zygalLoginPage.clickGoToSignIn();
		
		String errorMessage = zygalLoginPage.getErrorText();
		boolean isOnGetOTPPage = zygalLoginPage.isOnGetOTPPage();
		Assert.assertTrue(isOnGetOTPPage, "Navigation to Get OTP page failed after clicking Go to Sign In --> " + errorMessage);
	}
	
	
	@Test(description = "Verify that navigation and getOTP blocked for blocked account", dependsOnMethods = "verifyGoToSignPage")
	public void verifyAccountBlockOnGetOtpPage(ITestContext context) throws EncryptedDocumentException, IOException {
		WebDriver driver = (WebDriver) context.getAttribute("WebDriver");
		loginPage zygalLoginPage = new loginPage(driver);
		
		String userEmail = parameterization.getData("logindata", 4, 0);
		zygalLoginPage.enterUserId(userEmail);
		zygalLoginPage.clickOnGetOTP();
		String errorMessage = zygalLoginPage.getErrorText();
		Assert.assertFalse(errorMessage.isEmpty(), "Failed to stop navigation for blocked account --> " + errorMessage);
	}
}
