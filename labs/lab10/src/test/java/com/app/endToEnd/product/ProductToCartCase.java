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

    @FindBy(xpath = "//*[@id='cart']/div/div/div[2]/div/table/tbody/tr[1]/td[2]/a")
    private WebElement addedProductInCart;

    @FindBy(xpath = "//*[contains(@href, 'cart/view')]")
    private WebElement linkToOrdering;

    public void clickAddToCart() {
        addToCart.click();
    }

    public boolean isProductWasAddedToCard() {
        return addedProductInCart.isEnabled();
    }

    public void goToOrdering() {
        linkToOrdering.click();
    }
}
