package com.kts.taxify.e2e.pages;

import com.kts.taxify.e2e.util.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;
    private Actions actions;

    @FindBy(id = "loginBtn")
    private WebElement loginBtn;

    @FindBy(id = "logo-taxify")
    private WebElement logo;

    @FindBy(id = "pickupLocation")
    private WebElement pickupLocationInput;

    @FindBy(id = "destination")
    private WebElement destinationInput;

    @FindBy(className = "mat-option-text")
    private WebElement autoComplete;

    @FindBy(id = "rideInfo")
    private WebElement rideInfoLabel;

    @FindBy(className = "continueButton")
    private WebElement continueButton;

    @FindBy(id = "paymentMethod")
    private WebElement paymentMethods;

    @FindBy(className = "card-brand")
    private WebElement card;

    @FindBy(id = "searchRideButton")
    private WebElement searchRideButton;

    @FindBy(className = "hot-toast-message")
    private WebElement toastMessage;

    @FindBy(className = "hot-toast-close-btn")
    private WebElement toastCloseBtn;

    @FindBy(className = "startEndRideButton")
    private WebElement startEndRideButtonDriver;

    @FindBy(id = "linkPassengers")
    private WebElement linkPassengersDialog;

    @FindBy(id = "linkedUserInput")
    private WebElement addLinkedPassengerInput;

    @FindBy(id = "moveLinkedUser")
    private WebElement moveLinkedPassenger;

    @FindBy(id = "continueLinkedUsers")
    private WebElement continueLinkedUsersButton;

    @FindBy(id = "dropdownMenuButton1")
    private WebElement notifications;

    @FindBy(id = "acceptRide")
    private WebElement acceptRideBtn;

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "assessmentCancelButton")
    private WebElement assessmentCancelButton;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://localhost:4200/");
        PageFactory.initElements(driver, this);
        this.actions = new Actions(driver);
    }

    public void cancelAssessment(){ Utilities.clickableWait(driver, assessmentCancelButton, 10).click();}
    public void logout() {Utilities.clickableWait(driver, logoutButton, 10).click();}
    public void openNotifications() { Utilities.clickableWait(driver, notifications, 10).click();}
    public void acceptRideInvite() { Utilities.clickableWait(driver, acceptRideBtn, 10).click();}

    public void openLinkedPassengersDialog() { Utilities.visibilityWait(driver, linkPassengersDialog, 10).click();}
    public void writeLinkedPassengerEmail(String email) { Utilities.visibilityWait(driver, addLinkedPassengerInput, 10).sendKeys(email);}
    public void moveLinkedPassengerToListOfPassengers() { Utilities.clickableWait(driver, moveLinkedPassenger, 10).click();}

    public void submitLinkedPassengers() { Utilities.clickableWait(driver, continueLinkedUsersButton, 10).click();}

    public void startRideDriver() {
        Utilities.clickableWait(driver, startEndRideButtonDriver, 30).click();
    }

    public void endRideDriver() {
        Utilities.clickableWait(driver, startEndRideButtonDriver, 30).click();
    }

    public void closeToast() {
        Utilities.clickableWait(driver, toastCloseBtn, 10).click();
    }

    public String getToastMessage() {
        String text = Utilities.visibilityWait(driver, toastMessage, 50).getText();
        this.closeToast();
        return text;
    }

    public void waitForRideInfoLabelToBePresent() {
        Utilities.visibilityWait(driver, rideInfoLabel, 10);
    }

    public void waitForToastMessage(String message){
        Utilities.textWait(driver, toastMessage, message, 100);
        Utilities.clickableWait(driver, toastCloseBtn, 10).click();
    }

    public void searchForRide() {
        Utilities.visibilityWait(driver, searchRideButton, 10);
        Utilities.clickableWait(driver, searchRideButton, 10).click();
    }

    public void selectCard() {
        Utilities.visibilityWait(driver, card, 10).click();
    }

    public void openPaymentOptions(){
        Utilities.clickableWait(driver, paymentMethods, 10).click();
    }

    public void continueButtonClick() {
        Utilities.clickableWait(driver, continueButton, 10).click();
    }

    public void setPickup(String pickup) {
        Utilities.visibilityWait(driver, pickupLocationInput, 10).sendKeys(pickup);
        Utilities.visibilityWait(driver, autoComplete, 10).click();
    }

    public void setDestination(String destination) {
        Utilities.visibilityWait(driver, destinationInput, 10).sendKeys(destination);
        Utilities.visibilityWait(driver, autoComplete, 10).click();
    }


    public void clickLoginBtn() {
        Utilities.clickableWait(driver, loginBtn, 10).click();
    }

    public boolean isHomePageLoaded() {
        return Utilities.textWait(driver, logo, "Taxify", 10);
    }
}
