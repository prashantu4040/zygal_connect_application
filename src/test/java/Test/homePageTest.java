package Test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import POM.homePage;

public class homePageTest {
	
	@Test(description = "Perform scroll up or down")
	public void performScroll() {
		
	}

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
	
	@Test(description ="Verify cameras are selected by default")
	public void verifyCamerasselected () {
		
	}
	
	@Test(description = "Navigation to Spaces")
	public void verifyNavToSpaces() {
		
	}
	
	@Test(description = "Navigate back to home page")
	public void navBackToHome() {
		
	}
	
	@Test(description = "Navigate back to previous page (Back btn)")
	public void verifyBackBtn() {
		
	}

	@Test(description = "Verify Navigation from Home to AI Solution")
	public void verifyNavToAISolution() {

	}

	@Test(description = "Verify Navigation from Home to Collections")
	public void verifyNavToCollections() {

	}

	@Test(description = "Verify Navigation from Home to Zygal Services")
	public void verifyNavToZygalServices() {

	}
}
