package com.app.endToEnd.ordering;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderingCase {
    public WebDriver driver;

    public OrderingCase(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitOrder;

    @FindBy(xpath = "//*[contains(@class, 'alert alert-success')]")
    private WebElement successAlert;

    public void clickSubmitOrder() {
        submitOrder.click();
    }

    public boolean isSuccessAlertDisplayed() {
        return successAlert.isDisplayed();
    }
}
