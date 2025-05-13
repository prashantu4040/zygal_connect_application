package Test;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import POM.profilePage;

public class profilePageTest {

	@Test(description = " Verify profile page loads", dependsOnMethods = "Test.homePageTest.goToProfilePage")
	public void verifyProfilePageLoad(ITestContext context) {
		WebDriver driver = (WebDriver) context.getAttribute("validWebDriver");
		profilePage profile = new profilePage(driver); 
		profile.clickOnLogoutBtn();
	}
}
