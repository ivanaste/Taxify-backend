package com.kts.taxify.e2e.pages;

import com.kts.taxify.e2e.util.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver;
    private Actions actions;

    @FindBy(id="loginEmail")
    private WebElement emailInput;

    @FindBy(id="loginPassword")
    private WebElement passwordInput;

    @FindBy(className = "authButton")
    private WebElement loginButton;

    @FindBy(className = "hot-toast-message")
    private WebElement toastMessage;

    @FindBy(className = "hot-toast-close-btn")
    private WebElement toastCloseBtn;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.actions = new Actions(driver);
    }

    public void setEmail(String email) {
        Utilities.clickableWait(driver, emailInput, 10).sendKeys(email);
    }

    public void setPassword(String password) {
        Utilities.clickableWait(driver, passwordInput, 10).sendKeys(password);
    }

    public void clearPassword() {
        this.passwordInput.clear();
    }

    public void login() {
        Utilities.clickableWait(driver, loginButton, 10).click();
    }

    public String getToastMessage() {
        String text = Utilities.visibilityWait(driver, toastMessage, 10).getText();
        this.closeToast();
        return text;
    }

    public void closeToast() {
        Utilities.clickableWait(driver, toastCloseBtn, 10).click();
    }

}
