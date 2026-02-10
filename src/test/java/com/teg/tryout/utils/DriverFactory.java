package com.teg.tryout.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Map; 

public class DriverFactory {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
		
			// Path to your Chrome user profile
			
			ChromeOptions options = new ChromeOptions();
			
			options.addArguments("user-data-dir=D:\\Workplace\\TegTestSample\\ChromeProfile\\User Data");                     
			options.addArguments("profile-directory=Default"); // or "Profile 1"
			
			/*
			options.setExperimentalOption("prefs", Map.of(
                "safebrowsing.enabled", true,          // enable basic safe browsing
                "profile.default_content_setting_values.javascript", 1, // 1 = allow JS
				"profile.default_content_setting_values.automatic_downloads", 1
			));
			*/

			driver = new ChromeDriver(options);		
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
		
		
		
    }
}
