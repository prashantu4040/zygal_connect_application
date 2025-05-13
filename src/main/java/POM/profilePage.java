package POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class profilePage {
	private WebDriver driver;
	
	@FindBy(xpath = ("//div[@id ='logoutMain']"))
	private WebElement logOutProfilebtn;
	
	@FindBy(xpath = ("//div[contains(@class, 'w-full') and contains(@class, 'h-full') and contains(@class, 'rounded-full') and contains(@class, 'flex') and contains(@class, 'justify-center') and contains(@class, 'items-center') and contains(@class, 'absolute') and contains(@class, 'inset-0')]"))
	private WebElement logoutDialogBox;
	
	@FindBy(xpath = ("//div[@id ='logoutbtn']"))
	private WebElement logoutDialogBoxbtn;
	
	
	
	@FindBy(xpath = (""))
	private WebElement logoutDialogBoxCancel;
	
	
	public void clickOnLogoutBtn() {
		logOutProfilebtn.click();
	}

	// Constructor
    public profilePage(WebDriver lastDriver) {
        this.driver = lastDriver;
        PageFactory.initElements(driver, this);
    }
}
