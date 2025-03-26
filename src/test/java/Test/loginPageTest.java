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

public class loginPageTest extends baseTest {

	@BeforeMethod
	public void browserLaunch() {
		driver = Utility.browserLaunch.openBrowser();
	}

	@Test(description = "User Login with Valid Credentials")
	public void loginWithValidCredentialsTest() throws IOException {
		loginPage zygalLoginPage = new loginPage(driver);

		// Fetch test data from parameterization class
		String username = parameterization.getData("loginData", 1, 0);
		String otp = parameterization.getData("loginData", 1, 1);

		// Perform login steps using POM methods
		zygalLoginPage.enterUserId(username);
		zygalLoginPage.clickOnGetOTP();

		String errorMessage = zygalLoginPage.getErrorText();
		if (!errorMessage.isEmpty()) {
			System.out.println("Login Error: " + errorMessage);
			Assert.fail("Test failed due to login error: " + errorMessage);
		} else {
			// Use Explicit Wait to ensure OTP field is visible
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(zygalLoginPage.getOtpField1()));

			zygalLoginPage.enterOTP(otp);
			zygalLoginPage.clickOnLogin();

			 errorMessage = zygalLoginPage.getErrorText();
			    if (!errorMessage.isEmpty()) {
			        Assert.fail("Test failed due to OTP error: " + errorMessage);
			    }
			    
			// Verify login success by checking if the footer is visible
			boolean loginSuccess = zygalLoginPage.isFooterVisible();
			Assert.assertTrue(loginSuccess, "Login Test Failed! Footer is not visible.");
		}
	}
}
