package com.app.endToEnd.login;

import com.app.ConfProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

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

    @FindBy(xpath = "//*[contains(@class, 'glyphicon form-control-feedback glyphicon-remove')]")
    private List<WebElement> listErrors;

    @FindBy(xpath = "//button[contains(@class, 'btn btn-default') and @type='submit']")
    private WebElement submitBtn;

    @FindBy(xpath = "//*[contains(@class, 'alert alert-success')]")
    private WebElement successAlert;

    @FindBy(xpath = "//*[contains(@class, 'alert alert-danger')]")
    private WebElement dangerAlert;

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

    public boolean doesFieldsErrorDisplayed() {
        return listErrors.stream()
                .anyMatch(WebElement::isDisplayed);
    }

    public boolean doesSuccessAlertDisplayed() {
        return successAlert.isDisplayed();
    }

    public boolean doesDangerAlertDisplayed() {
        return dangerAlert.isDisplayed();
    }

    public void clickLogout() {
        accountDropdown.click();
        logoutLink.click();
    }

    public void loginByDefaultCredentials() {
        inputLogin(ConfProperties.getProperty("credentials.login"));
        inputPassword(ConfProperties.getProperty("credentials.password"));
        clickSubmitBtn();
    }

    public void loginBySpecialCredentials(String login, String password) {
        inputLogin(login);
        inputPassword(password);
        clickSubmitBtn();
    }
}
