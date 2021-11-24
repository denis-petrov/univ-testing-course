package com.app;

import org.openqa.selenium.WebDriver;

import java.time.Duration;

public final class DriverUtil {
    private DriverUtil() {}

    public static void setupDriver() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chrome.driver"));
    }

    public static void awaitDriver(WebDriver driver, Integer seconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }

    public static void goToPage(WebDriver driver, String pageProperty) {
        driver.get(ConfProperties.getProperty(pageProperty));
    }
}
