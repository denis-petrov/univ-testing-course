package com.app.endToEnd.searchInCatalog;

import com.app.DriverUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class SearchInCatalogCaseTest {

    public static WebDriver driver;
    public static SearchInCatalogCase searchInCatalogCase;

    private final static String CATEGORY_PAGE = "category.page";

    @BeforeSuite
    public static void setup() {
        DriverUtil.setupDriver();

        driver = new ChromeDriver();
        searchInCatalogCase = new SearchInCatalogCase(driver);

        driver.manage().window().maximize();
    }

    @Test
    public void addProductToCart() throws InterruptedException {
        DriverUtil.goToPage(driver, CATEGORY_PAGE);
        searchInCatalogCase.clickOnMechanismSearchCheckbox();

        Thread.sleep(3000);
        searchInCatalogCase.clickOnGlassSearchCheckbox();
        Thread.sleep(3000);
        searchInCatalogCase.clickOnStrapSearchCheckbox();
        Thread.sleep(3000);
        searchInCatalogCase.clickOnBodySearchCheckbox();
        Thread.sleep(3000);

        searchInCatalogCase.clickOnLinkToProduct();
        assertTrue(searchInCatalogCase.isProductDisplayed());
    }

    @AfterSuite
    public void afterSuite() {
        driver.close();
    }
}
