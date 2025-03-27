package POM;

import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object Model (POM) class for Login Page. This class contains WebElements
 * and methods for user authentication.
 */
public class loginPage {
	private WebDriver driver;

	// ==============================
	// Locators - Login Page Elements
	// ==============================

	/** Email or phone number input field */
	@FindBy(xpath = "//input[@type='email' and @name='emailorphonenumber']")
	private WebElement userId;

	/** "Get OTP" button */
	@FindBy(xpath = "//button[@id='loginButton']")
	private WebElement getOTPButton;

	// ==============================
	// Locators - OTP Input Fields
	// ==============================

	/** OTP input fields (each digit has a separate input box) */
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

	/** "Login" button */
	@FindBy(xpath = "//button[@id='loginButton']")
	private WebElement loginButton;

	/** Error message displayed when login fails */
	@FindBy(xpath = "//h3[text()='Error']/following-sibling::div")
	private WebElement getError;

	/** Footer element used to verify successful login */
	@FindBy(xpath = "//footer[@id='newmenubarid']")
	private WebElement footer;
	
	/** Go to sign page button on login page**/
	@FindBy(xpath ="//p[contains(text(), 'Go to Sign In page')]")
	private WebElement goToSignPage;
	
	/** Close button for error toast message */
	@FindBy(xpath = "//button[contains(@class, 'inline-flex') and contains(@class, 'justify-center') and contains(@class, 'items-center')]")
	private WebElement closeToastButton;

	// ==============================
	// Constructor
	// ==============================

	/**
	 * Constructor initializes the Page Object Model for loginPage.
	 * 
	 * @param driver WebDriver instance used for interacting with the web elements.
	 */
	public loginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// ==============================
	// Methods - Login Actions
	// ==============================

	/**
	 * Enters the user ID (email or phone number) in the input field.
	 * 
	 * @param user The email or phone number of the user.
	 */
	public void enterUserId(String user) {
		userId.sendKeys(user);
	}

	/**
	 * Clicks on the "Get OTP" button to request an OTP for login.
	 */
	public void clickOnGetOTP() {
		getOTPButton.click();
	}

	/**
	 * Returns the first OTP input field WebElement.
	 * 
	 * @return WebElement for the first OTP field.
	 */
	public WebElement getOtpField1() {
		return otpField1;
	}

	/**
	 * Enters the OTP into the respective OTP input fields.
	 * 
	 * @param otp The six-digit OTP received by the user.
	 */
	public void enterOTP(String otp) {
		otpField1.sendKeys(String.valueOf(otp.charAt(0)));
		otpField2.sendKeys(String.valueOf(otp.charAt(1)));
		otpField3.sendKeys(String.valueOf(otp.charAt(2)));
		otpField4.sendKeys(String.valueOf(otp.charAt(3)));
		otpField5.sendKeys(String.valueOf(otp.charAt(4)));
		otpField6.sendKeys(String.valueOf(otp.charAt(5)));
	}

	/**
	 * Clicks on the "Login" button to proceed with authentication.
	 */
	public void clickOnLogin() {
		loginButton.click();
	}

	/**
	 * Retrieves the error message displayed on the login page, if any.
	 * 
	 * @return The error message text if visible, otherwise an empty string.
	 */
	public String getErrorText() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
			wait.until(ExpectedConditions.visibilityOf(getError));
			return getError.getText();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Verifies whether the footer element is visible, indicating a successful
	 * login.
	 * 
	 * @return true if the footer is displayed, false otherwise.
	 */
	public boolean isFooterVisible() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(footer));
			return footer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// this will clear the user email field
	public void clearUseEmailField() {
		userId.click();  // Click to focus (if required)
		userId.clear();  // Use clear() method
		userId.sendKeys(Keys.CONTROL + "a");  // Select all text
		userId.sendKeys(Keys.DELETE);  // Delete selected text
	}

	
	public String getUserIdFieldValue() {
	    String value = userId.getDomAttribute("value"); 
	    return (value != null) ? value : userId.getDomProperty("value");
	}

	
	public void clickGoToSignIn() {
		goToSignPage.click();
	}
	
	public boolean isOnGetOTPPage() {
	    return getOTPButton.isDisplayed();  // Check if the "Get OTP" button is visible
	}
	
	public void closeErrorToast() {
	    if (closeToastButton.isDisplayed()) {
	        closeToastButton.click();
	    }
	}

}
