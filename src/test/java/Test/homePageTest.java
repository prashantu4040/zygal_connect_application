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
        WebDriver driver = (WebDriver) context.getAttribute("WebDriver");

        // If driver is null, fail the test
        if (driver == null) {
            Assert.fail("WebDriver not found. Login test may have failed.");
        }

        homePage home = new homePage(driver);
        boolean isLoaded = home.isHomePageLoaded();
        Assert.assertTrue(isLoaded, "Home Page did not load successfully.");
    }
}
