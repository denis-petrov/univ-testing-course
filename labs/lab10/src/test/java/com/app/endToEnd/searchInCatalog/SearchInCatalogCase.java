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

    @FindBy(xpath = "//section[1]/div/div/label[1]")
    private WebElement mechanismSearchCheckbox;

    @FindBy(xpath = "//section[2]/div/div/label[1]")
    private WebElement glassSearchCheckbox;

    @FindBy(xpath = "//section[3]/div/div/label[1]")
    private WebElement strapSearchCheckbox;

    @FindBy(xpath = "//section[4]/div/div/label[1]")
    private WebElement bodySearchCheckbox;

    @FindBy(xpath = "//h2[contains(text(), 'Casio MRP-700-1AVEF')]")
    private WebElement productName;

    public void clickOnLinkToProduct() {
        linkToProduct.click();
    }

    public void clickOnMechanismSearchCheckbox() {
        mechanismSearchCheckbox.click();
    }

    public void clickOnGlassSearchCheckbox() {
        glassSearchCheckbox.click();
    }

    public void clickOnStrapSearchCheckbox() {
        strapSearchCheckbox.click();
    }

    public void clickOnBodySearchCheckbox() {
        bodySearchCheckbox.click();
    }

    public boolean isProductDisplayed() {
        return productName.isDisplayed();
    }
}
