package com.app.endToEnd.login;

import com.app.ConfProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginCase {

    public WebDriver driver;

    public LoginCase(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[contains(@id, 'login') and contains(@class, 'form-control')]")
    private WebElement loginField;

    @FindBy(xpath = "//*[contains(@id, 'pasword')]")
    private WebElement passwordField;

    @FindBy(xpath = "//button[contains(@class, 'btn btn-default') and @type='submit']")
    private WebElement submitBtn;

    @FindBy(xpath = "//*[contains(@class, 'alert alert-success')]")
    private WebElement successAlert;

    @FindBy(xpath = "//a[contains(@class, 'dropdown-toggle')]")
    private WebElement accountDropdown;

    @FindBy(xpath = "//a[contains(@href, 'user/logout')]")
    private WebElement logoutLink;

    public void inputLogin(String login) {
        loginField.sendKeys(login);
    }

    public void inputPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickSubmitBtn() {
        submitBtn.click();
    }

    public boolean doesSuccessAlertExist() {
        return successAlert.isDisplayed();
    }

    public void clickLogout() {
        accountDropdown.click();
        logoutLink.click();
    }

    public void login() {
        inputLogin(ConfProperties.getProperty("credentials.login"));
        inputPassword(ConfProperties.getProperty("credentials.password"));
        clickSubmitBtn();
    }
}
