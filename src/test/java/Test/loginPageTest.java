package Test;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
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
	public void loginWithValidCredentialsTest() throws EncryptedDocumentException, IOException, InterruptedException {
		loginPage zygalLoginPage = new loginPage(driver);
		Thread.sleep(1000);		
		String username = parameterization.getData("loginData", 1, 0);
		String password = parameterization.getData("loginData", 1, 1);
		zygalLoginPage.enteruserId(username);
		zygalLoginPage.enterpassword(password);
		zygalLoginPage.enterCaptcha();
		zygalLoginPage.ClickOnSubmit();
		//System.out.println(zygalLoginPage.getErrorText());
	}

}
