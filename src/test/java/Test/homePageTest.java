package Test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import POM.homePage;

public class homePageTest {

	@Test(description = "Verify Home Page Loads Successfully", dependsOnMethods = "Test.loginPageTest.loginWithValidCredentialsTest")
	public void verifyHomePageLoads(ITestContext context) {
		// Retrieve the WebDriver from loginPageTest
		WebDriver driver = (WebDriver) context.getAttribute("validWebDriver");

		// If driver is null, fail the test
		if (driver == null) {
			Assert.fail("WebDriver not found. Login test may have failed.");
		}

		homePage home = new homePage(driver);
		boolean isLoaded = home.isHomePageLoaded();
		Assert.assertTrue(isLoaded, "Home Page did not load successfully.");
	}
	
	@Test(description ="Navigate to Profile Page", dependsOnMethods = "verifyHomePageLoads")
	public void goToProfilePage(ITestContext context) {
		WebDriver driver = (WebDriver) context.getAttribute("validWebDriver");
		homePage home = new homePage(driver);
		home.clickOnProfileBtn();
		
	    boolean isLoaded = home.isProfilePageLoaded();
	    Assert.assertTrue(isLoaded, "Profile Page did not load successfully (Footer still visible).");
	}
}
