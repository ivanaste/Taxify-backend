package com.kts.taxify.e2e.pages;

import com.kts.taxify.e2e.util.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private Actions actions;

    @FindBy(id = "loginBtn")
    private WebElement loginBtn;

    @FindBy(id = "logo-taxify")
    private WebElement logo;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://localhost:4200/");
        PageFactory.initElements(driver, this);
        this.actions = new Actions(driver);
    }

    public void clickLoginBtn() {
        Utilities.clickableWait(driver, loginBtn, 10).click();
    }

    public boolean isHomePageLoaded() {
        return Utilities.textWait(driver, logo, "Taxify", 10);
    }
}
