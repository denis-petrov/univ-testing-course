package com.app.endToEnd.login;

import com.app.DriverUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;


public class LoginCaseTest {

    public static WebDriver driver;
    public static LoginCase loginCase;

    @BeforeSuite
    public static void setup() {
        DriverUtil.setupDriver();

        driver = new ChromeDriver();
        loginCase = new LoginCase(driver);

        driver.manage().window().maximize();

        DriverUtil.goToPage(driver, "login.page");
    }

    @Test
    public void loginTest() {
        loginCase.login();
        assertTrue(loginCase.doesSuccessAlertExist());
    }

    @AfterSuite
    public void logout() {
        loginCase.clickLogout();
        driver.close();
    }
}
