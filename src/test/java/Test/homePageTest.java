package Test;

import org.testng.Assert;
import org.testng.annotations.Test;
import POM.homePage;

public class homePageTest extends baseTest {

    @Test(description = "Verify Home Page Loads Successfully", dependsOnMethods = "Test.loginPageTest.loginWithValidCredentialsTest")
    public void verifyHomePageLoads() {
        homePage homePage = new homePage(driver);
        boolean isLoaded = homePage.isHomePageLoaded();
        Assert.assertTrue(isLoaded, "Home Page did not load successfully");
        
    }
}
