package com.app;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.fail;

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
