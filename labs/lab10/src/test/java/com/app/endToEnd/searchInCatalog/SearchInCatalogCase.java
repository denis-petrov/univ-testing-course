package com.app.endToEnd.searchInCatalog;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchInCatalogCase {

    public WebDriver driver;

    public SearchInCatalogCase(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[contains(@href, 'product/casio-mrp-700-1avef-1')]")
    private WebElement linkToProduct;

    @FindBy(xpath = "//h2[contains(text(), 'Casio MRP-700-1AVEF')]")
    private WebElement productName;

    public void clickOnLinkToCategory() {
        linkToProduct.click();
    }

    public boolean isProductDisplayed() {
        return productName.isDisplayed();
    }
}
