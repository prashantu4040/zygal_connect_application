package Utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.emulation.Emulation;

import Config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.Optional;

public class browserLaunch {
	public static WebDriver openBrowser() {
		// Setup ChromeOptions
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		// Setup WebDriver
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(options);

		// Open DevTools and start a session
		DevTools devTools = ((ChromeDriver) driver).getDevTools();
		devTools.createSession();

		// Function to apply iPhone SE mobile emulation
		applyMobileEmulation(devTools);

		// Load URL after emulation is applied
		String baseUrl = ConfigReader.getProperty("base_url");
		driver.get(baseUrl);

		// Reapply emulation after page load to prevent reverting to fullscreen
		applyMobileEmulation(devTools);

		return driver;
	}

	// Function to apply mobile emulation settings
	private static void applyMobileEmulation(DevTools devTools) {
		devTools.send(Emulation.setDeviceMetricsOverride(375, 667, 2.0, true, // width, height, scale factor, mobile
																				// mode
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty()));

		// Set iPhone SE User-Agent
		devTools.send(Emulation.setUserAgentOverride(
				"Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Mobile Safari/537.36",
				Optional.empty(), Optional.empty(), Optional.empty()));
	}
}
