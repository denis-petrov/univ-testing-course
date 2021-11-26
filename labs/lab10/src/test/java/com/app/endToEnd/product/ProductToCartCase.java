package com.app.endToEnd.product;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductToCartCase {

    public WebDriver driver;

    public ProductToCartCase(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(@id, 'productAdd')]")
    private WebElement addToCart;

    @FindBy(xpath = "//*[contains(@class, 'modal fade in')]")
    private WebElement modalWindow;

    @FindBy(xpath = "//*[contains(@href, 'cart/view')]")
    private WebElement linkToOrdering;

    public void clickAddToCart() {
        addToCart.click();
    }

    public boolean isProductWasAddedToCard() {
        return modalWindow.isDisplayed();
    }

    public void goToOrdering() {
        linkToOrdering.click();
    }
}
