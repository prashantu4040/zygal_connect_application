package POM;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class profilePage {
	
	@FindBy(xpath = ("//h1[contains(text(), 'Log out')]"))
	private WebElement logOutProfilebtn;
	
	@FindBy(xpath = ("//div[contains(@class, 'w-full') and contains(@class, 'h-full') and contains(@class, 'rounded-full') and contains(@class, 'flex') and contains(@class, 'justify-center') and contains(@class, 'items-center') and contains(@class, 'absolute') and contains(@class, 'inset-0')]"))
	private WebElement logoutDialogBox;
	
	@FindBy(xpath = (""))
	private WebElement logoutDialogBoxbtn;
	
	@FindBy(xpath = (""))
	private WebElement logoutDialogBoxCancel;

}
