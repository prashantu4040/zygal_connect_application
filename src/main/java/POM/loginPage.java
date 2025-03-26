package POM;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class loginPage {
    private WebDriver driver;

    // Login Page Elements
    @FindBy(xpath = "//input[@type='email' and @name='emailorphonenumber']")
    private WebElement userId;

    @FindBy(xpath = "//button[@id='loginButton']")
    private WebElement getOTPButton;

    // OTP Page Elements
    @FindBy(xpath = "//input[@type='password' and @name='otp_0' and @maxlength='1']")
    private WebElement otpField1;

    @FindBy(xpath = "//input[@type='password' and @name='otp_1' and @maxlength='1']")
    private WebElement otpField2;

    @FindBy(xpath = "//input[@type='password' and @name='otp_2' and @maxlength='1']")
    private WebElement otpField3;

    @FindBy(xpath = "//input[@type='password' and @name='otp_3' and @maxlength='1']")
    private WebElement otpField4;

    @FindBy(xpath = "//input[@type='password' and @name='otp_4' and @maxlength='1']")
    private WebElement otpField5;

    @FindBy(xpath = "//input[@type='password' and @name='otp_5' and @maxlength='1']")
    private WebElement otpField6;
    
    
    // Login button
    @FindBy(xpath = "//button[@id='loginButton']")
    private WebElement loginButton;
    
    //get error message on login page
    @FindBy(xpath = "//h3[text()='Error']/following-sibling::div")
    private WebElement getError;

    // Footer WebElement (To validate successful login)
    @FindBy(xpath = "//footer[@id='newmenubarid']")
    private WebElement footer;

    // Constructor
    public loginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Enter email ID
    public void enterUserId(String user) {
        userId.sendKeys(user);
    }

    // Click on Get OTP button
    public void clickOnGetOTP() {
        getOTPButton.click();
    }
    
    public WebElement getOtpField1() {
        return otpField1;
    }


    // Enter OTP
    public void enterOTP(String otp) {
        otpField1.sendKeys(String.valueOf(otp.charAt(0)));
        otpField2.sendKeys(String.valueOf(otp.charAt(1)));
        otpField3.sendKeys(String.valueOf(otp.charAt(2)));
        otpField4.sendKeys(String.valueOf(otp.charAt(3)));
        otpField5.sendKeys(String.valueOf(otp.charAt(4)));
        otpField6.sendKeys(String.valueOf(otp.charAt(5)));
    }

    // Click on Login button
    public void clickOnLogin() {
        loginButton.click();
    }

    // Get error text if any
    public String getErrorText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.visibilityOf(getError));
            return getError.getText();
        } catch (Exception e) {
            return "";
        }
    }

    // Check if the footer is visible (Login Success Validation)
    public boolean isFooterVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(footer));
            return footer.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
