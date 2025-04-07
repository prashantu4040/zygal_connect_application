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

    // Constructor
    public homePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isHomePageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOf(footer));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
