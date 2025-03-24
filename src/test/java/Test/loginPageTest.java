package Test;

import java.io.IOException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import POM.loginPage;
import Utility.parameterization;

public class loginPageTest extends baseTest {

    @BeforeMethod
    public void browserLaunch() {
        driver = Utility.browserLaunch.openBrowser();
    }

    @Test(description = "User Login with Valid Credentials")
    public void loginWithValidCredentialsTest() throws IOException, InterruptedException {
        loginPage zygalLoginPage = new loginPage(driver);

        // Fetch test data from parameterization class
        String username = parameterization.getData("loginData", 1, 0);
        String otp = parameterization.getData("loginData", 1, 1);

        // Perform login steps using POM methods
        zygalLoginPage.enterUserId(username);
        zygalLoginPage.clickOnGetOTP();

        // Adding wait to simulate OTP retrieval
        Thread.sleep(2000); 

        zygalLoginPage.enterOTP(otp);
        zygalLoginPage.clickOnLogin();

    }
}
