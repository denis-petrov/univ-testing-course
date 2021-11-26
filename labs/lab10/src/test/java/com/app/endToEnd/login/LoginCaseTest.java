package com.app.endToEnd.login;

import com.app.DriverUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;


public class LoginCaseTest {

    private final static String LOGIN_PAGE = "login.page";

    public static WebDriver driver;
    public static LoginCase loginCase;

    @BeforeMethod
    public static void setup() {
        DriverUtil.setupDriver();

        driver = new ChromeDriver();
        loginCase = new LoginCase(driver);

        driver.manage().window().maximize();

        DriverUtil.goToPage(driver, LOGIN_PAGE);
    }

    @Test
    public void loginWithEmptyCredentialsTest() {
        loginCase.loginBySpecialCredentials("", "");
        assertTrue(loginCase.doesFieldsErrorDisplayed());
    }

    @Test
    public void loginWithEmptyPasswordCredentialTest() {
        loginCase.loginBySpecialCredentials("", "test");
        assertTrue(loginCase.doesFieldsErrorDisplayed());
    }

    @Test
    public void loginWithEmptyLoginCredentialTest() {
        loginCase.loginBySpecialCredentials("test", "");
        assertTrue(loginCase.doesFieldsErrorDisplayed());
    }

    @Test
    public void loginWithInvalidLoginAndPasswordCredentialsTest() {
        loginCase.loginBySpecialCredentials("test", "test");
        assertTrue(loginCase.doesDangerAlertDisplayed());
    }

    @Test
    public void loginWithCorrectCredentialsTest() {
        loginCase.loginByDefaultCredentials();
        assertTrue(loginCase.doesSuccessAlertDisplayed());
        loginCase.clickLogout();
    }

    @AfterMethod
    public void close() {
        driver.close();
    }
}
