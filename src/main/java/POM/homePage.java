package POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class homePage {
    private WebDriver driver;

    // Home Page Elements
    @FindBy(xpath = "//header[contains(@class,'w-full h-fit flex items-center')]")
    private WebElement header;

    @FindBy(xpath = "//footer[contains(@id,'newmenubarid')]")
    private WebElement footer;
    
    @FindBy(xpath = "//div[@id = 'homeProfileBtn']")
    private WebElement homeProfileBtn;

    // Constructor
    public homePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

 // Used in verifyHomePageLoads()
    public boolean isHomePageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOf(footer));
            return true;  // Footer is visible → Home Page is loaded
        } catch (Exception e) {
            return false;
        }
    }

    // Used in goToProfilePage()
    public boolean isProfilePageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            return wait.until(ExpectedConditions.invisibilityOf(footer)); 
            // Footer is not visible → Profile Page is assumed loaded
        } catch (Exception e) {
            return false;
        }
    }

    
    public void clickOnProfileBtn() {
    	homeProfileBtn.click();
    }
}
