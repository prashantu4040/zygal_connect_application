package POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class emptyState {
	private WebDriver driver;
	
	@FindBy()
	private WebElement noDataCamImage;
	
	@FindBy()
	private WebElement noDataSpacesImage;
	
	@FindBy()
	private WebElement countOfCamera;
	
	@FindBy()
	private WebElement countOfSpaces;
	
	@FindBy()
	private WebElement camSearch;
	
	@FindBy()
	private WebElement spacesSearch;
	
	@FindBy()
	private WebElement aiSolution;
	
	@FindBy()
	private WebElement collection;
	
	@FindBy()
	private WebElement systemStatus;
	
	@FindBy()
	private WebElement cameraStatus;
	
	@FindBy()
	private WebElement spaceStatus;
	
	@FindBy()
	private WebElement backButton;
	
	@FindBy()
	private WebElement goToHomeButton;
}
