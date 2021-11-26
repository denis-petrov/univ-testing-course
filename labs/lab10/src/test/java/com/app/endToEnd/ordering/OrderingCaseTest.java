package com.app.endToEnd.ordering;

import com.app.DriverUtil;
import com.app.endToEnd.login.LoginCase;
import com.app.endToEnd.product.ProductToCartCase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class OrderingCaseTest {

    private final static String LOGIN_PAGE = "login.page";
    private final static String PRODUCT_PAGE = "product.page";

    public static WebDriver driver;
    public static ProductToCartCase productToCartCase;
    public static OrderingCase orderingCase;
    public static LoginCase loginCase;

    @BeforeSuite
    public static void setup() {
        DriverUtil.setupDriver();

        driver = new ChromeDriver();
        loginCase = new LoginCase(driver);
        productToCartCase = new ProductToCartCase(driver);
        orderingCase = new OrderingCase(driver);

        driver.manage().window().maximize();
    }

    @Test
    public void addProductToCart() {
        DriverUtil.goToPage(driver, LOGIN_PAGE);
        loginCase.loginByDefaultCredentials();

        DriverUtil.goToPage(driver, PRODUCT_PAGE);
        productToCartCase.clickAddToCart();
        DriverUtil.awaitDriver(driver, 10);
        productToCartCase.goToOrdering();


        orderingCase.clickSubmitOrder();
        DriverUtil.awaitDriver(driver, 10);
        assertTrue(orderingCase.isSuccessAlertDisplayed());
    }

    @AfterSuite
    public void afterSuite() {
        driver.close();
    }
}
