package Test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import POM.profilePage;

public class profilePageTest {

	@Test(description = " Verify profile page loads", dependsOnMethods = "Test.homePageTest.goToProfilePage")
	public void verifyProfilePageLoad(ITestContext context) throws InterruptedException {
		WebDriver driver = (WebDriver) context.getAttribute("validWebDriver");
		profilePage profile = new profilePage(driver);
		Assert.assertTrue(profile.isDataHolderVisible(), "DataHolder section is not visible");
		Assert.assertTrue(profile.isFooterVisible(), "Footer is not visible");
		Assert.assertTrue(profile.isLogoutSectionVisible(), "Logout button inside footer is not visible");
		profile.clickOnLogoutProfileBtn();
		Thread.sleep(500);
	}
	
	@Test(description = "Logged out successfully", dependsOnMethods = "verifyProfilePageLoad")
	public void verifySuccessfulLogout(ITestContext context) {
		WebDriver driver = (WebDriver) context.getAttribute("validWebDriver");
		profilePage profile = new profilePage(driver); 
		profile.clickOnLogoutbtn();
		Assert.assertTrue(profile.isLogoutToastVisible(), "Logout toast message not visible.");
	}
}
