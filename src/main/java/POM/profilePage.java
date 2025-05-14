package POM;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class profilePage {
	private WebDriver driver;
	private WebDriverWait wait;
	
	@FindBy(xpath = ("//div[@id ='logoutMain']"))
	private WebElement logOutProfilebtn;
		
	@FindBy(xpath = "//footer")
    private WebElement footer;
	
	@FindBy(xpath = ("//div[@id ='logoutbtn']"))
	private WebElement logoutDialogBoxbtn;
	
	@FindBy(xpath = ("//div[@id='cancelbtn']"))
	private WebElement logoutDialogBoxCancel;
	
	@FindBy(xpath = ("//div[@id='dataHolder']"))
	private WebElement dataHolder;
	
	@FindBy(xpath = "//*[@role='alert']")
    private WebElement logoutToast;
	
	public void clickOnLogoutProfileBtn() {
		logOutProfilebtn.click();
	}

	// Constructor
    public profilePage(WebDriver lastDriver) {
        this.driver = lastDriver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
 // Check if dataHolder is visible
    public boolean isDataHolderVisible() {
        return dataHolder.isDisplayed();
    }

 // Method to check if footer is displayed
    public boolean isFooterVisible() {
        return footer.isDisplayed();
    }
    
    public boolean isLogoutSectionVisible() {
        return logOutProfilebtn.isDisplayed();
    }
    
    public void clickOnLogoutbtn() {
    	logoutDialogBoxbtn.click();
    }
    
    public boolean isLogoutToastVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(logoutToast));
            return logoutToast.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
