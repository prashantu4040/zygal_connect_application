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

    /**
     * Opens the Chrome browser with mobile emulation and loads the specified URL.
     * 
     * @return WebDriver instance with applied settings.
     */
    public static WebDriver openBrowser() {
        // Setup ChromeOptions to disable browser notifications
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        // Setup and initialize WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);

        // Open Chrome DevTools and start a session
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();

        // Apply mobile emulation (iPhone SE simulation)
        applyMobileEmulation(devTools);

        // Load the base URL from the config file
        String baseUrl = ConfigReader.getProperty("base_url");
        driver.get(baseUrl);

        // Reapply mobile emulation after page load to maintain settings
        applyMobileEmulation(devTools);

        return driver;
    }

    /**
     * Applies mobile emulation settings to mimic an iPhone SE device.
     * 
     * @param devTools DevTools instance to interact with the Chrome browser.
     */
    private static void applyMobileEmulation(DevTools devTools) {
        // Set the device screen size and scale factor to mimic a mobile device
        devTools.send(Emulation.setDeviceMetricsOverride(
                375, 667, 2.0, true, // width, height, scale factor, mobile mode
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty()));

        // Set the User-Agent string to simulate an iPhone SE
        devTools.send(Emulation.setUserAgentOverride(
                "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Mobile Safari/537.36",
                Optional.empty(), Optional.empty(), Optional.empty()));
    }
}
