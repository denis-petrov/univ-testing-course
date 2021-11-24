package com.app.endToEnd.product;

import com.app.DriverUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class ProductToCartCaseTest {

    public static WebDriver driver;
    public static ProductToCartCase productToCartCase;

    @BeforeSuite
    public static void setup() {
        DriverUtil.setupDriver();

        driver = new ChromeDriver();
        productToCartCase = new ProductToCartCase(driver);

        driver.manage().window().maximize();

        DriverUtil.goToPage(driver, "product.page");
    }

    @Test
    public void addProductToCart() {
        productToCartCase.clickAddToCart();
        DriverUtil.awaitDriver(driver, 10);
        assertTrue(productToCartCase.isProductWasAddedToCard());
    }

    @AfterSuite
    public void afterSuite() {
        driver.close();
    }
}
