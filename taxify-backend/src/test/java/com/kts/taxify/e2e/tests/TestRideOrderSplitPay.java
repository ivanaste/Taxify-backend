package com.kts.taxify.e2e.tests;

import com.kts.taxify.e2e.pages.HomePage;
import com.kts.taxify.e2e.pages.LoginPage;
import com.kts.taxify.e2e.util.Utilities;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.validation.constraints.AssertTrue;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRideOrderSplitPay {
    public static WebDriver firstClientDriver;
    public static WebDriver secondClientDriver;
    public static WebDriver driverDriver;
    private static HomePage homePageFirstClient;
    private static LoginPage loginPageFirstClient;

    private static HomePage homePageSecondClient;
    private static LoginPage loginPageSecondClient;

    private static HomePage homePageDriver;
    private static LoginPage loginPageDriver;

    private static final String REDIRECT_URL = "https://localhost:4200/maps";
    private static final String FILTER_ORDER_RIDE_URL = "https://localhost:4200/maps?filterDriversMode=true";

    @BeforeAll
    static void initializeWebDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions handlingSSL = new ChromeOptions();
        handlingSSL.setAcceptInsecureCerts(true);
        firstClientDriver = new ChromeDriver(handlingSSL);
        firstClientDriver.manage().window().maximize();
        secondClientDriver = new ChromeDriver(handlingSSL);
        secondClientDriver.manage().window().maximize();
        driverDriver = new ChromeDriver(handlingSSL);
        driverDriver.manage().window().maximize();
        homePageDriver = new HomePage(driverDriver);
        loginPageDriver = new LoginPage(driverDriver);
        homePageFirstClient = new HomePage(firstClientDriver);
        loginPageFirstClient = new LoginPage(firstClientDriver);
        homePageSecondClient = new HomePage(secondClientDriver);
        loginPageSecondClient = new LoginPage(secondClientDriver);
    }

    @AfterAll
    static void quitDriver() {
        firstClientDriver.quit();
        driverDriver.quit();
        secondClientDriver.quit();
    }


    @Test
    public void testOrderRide() {
        loginDriver("brzi@gmail.com", "Micomilic123!");
        switchWindows(secondClientDriver, driverDriver, firstClientDriver);
        loginSecondClient("ivana@gmail.com", "Uros123!");
        switchWindows(firstClientDriver, driverDriver, secondClientDriver);
        loginFirstClient("prijovic.uros13@gmail.com", "Uros123!");
        setRoute();
        linkPassengers("ivana@gmail.com");
        setFiltersAndPayment();
        homePageFirstClient.searchForRide();

        switchWindows(secondClientDriver, driverDriver, firstClientDriver);
        acceptRideInvite();
        Assertions.assertTrue(homePageSecondClient.getToastMessage().contains("You have been successfully charged"));
        switchWindows(firstClientDriver, driverDriver, secondClientDriver);
        homePageFirstClient.waitForToastMessage("All linked users have accepted the ride");
        Assertions.assertTrue(homePageFirstClient.getToastMessage().contains("You have been successfully charged"));
        homePageFirstClient.waitForToastMessage("Your ride has been accepted.");


        switchWindows(driverDriver, firstClientDriver, secondClientDriver);
        homePageDriver.waitForToastMessage("Ride has been assigned to you.");
        switchWindows(firstClientDriver, secondClientDriver, driverDriver);
        homePageFirstClient.waitForToastMessage("Vehicle has arrived on your destination.");

        switchWindows(driverDriver, firstClientDriver, secondClientDriver);
        homePageDriver.startRideDriver();

        switchWindows(firstClientDriver, driverDriver, secondClientDriver);
        homePageFirstClient.waitForToastMessage("Your ride has started.");
        homePageFirstClient.waitForToastMessage("You have arrived on your destination");

        switchWindows(driverDriver, firstClientDriver, secondClientDriver);
        homePageDriver.waitForToastMessage("You have arrived on destination.");

        homePageDriver.endRideDriver();

        homePageDriver.waitForToastMessage("You successfully finished a ride.");

        switchWindows(firstClientDriver, driverDriver, secondClientDriver);
        homePageFirstClient.waitForToastMessage("Your ride has finished.");

        homePageDriver.logout();
        homePageFirstClient.cancelAssessment();
        homePageFirstClient.logout();
        homePageSecondClient.logout();


    }

    private void acceptRideInvite() {
        homePageSecondClient.waitForToastMessage("You have been added to the ride.");
        homePageSecondClient.openNotifications();
        homePageSecondClient.acceptRideInvite();
        homePageSecondClient.selectCard();
    }

    private void setFiltersAndPayment() {
        homePageFirstClient.openPaymentOptions();
        homePageFirstClient.selectCard();
    }

    private void setRoute() {
        homePageFirstClient.setPickup("Dr ivana ribara 5, novi sad");
        homePageFirstClient.setDestination("Tocionica, narodnog fronta");
        homePageFirstClient.waitForRideInfoLabelToBePresent();
        homePageFirstClient.continueButtonClick();
        Assertions.assertEquals(FILTER_ORDER_RIDE_URL, firstClientDriver.getCurrentUrl());
    }
    private void linkPassengers(String email) {
        homePageFirstClient.openLinkedPassengersDialog();
        homePageFirstClient.writeLinkedPassengerEmail(email);
        homePageFirstClient.moveLinkedPassengerToListOfPassengers();
        homePageFirstClient.submitLinkedPassengers();
    }
    private void loginSecondClient(String email, String password) {
        assertTrue(homePageSecondClient.isHomePageLoaded());
        homePageSecondClient.clickLoginBtn();
        loginPageSecondClient.setEmail(email);
        loginPageSecondClient.setPassword(password);
        loginPageSecondClient.login();
        Assertions.assertEquals(loginPageSecondClient.getToastMessage(), "You successfully logged in.");
        Assertions.assertEquals(REDIRECT_URL, secondClientDriver.getCurrentUrl());
    }

    private void loginFirstClient(String email, String password) {
        assertTrue(homePageFirstClient.isHomePageLoaded());
        homePageFirstClient.clickLoginBtn();
        loginPageFirstClient.setEmail(email);
        loginPageFirstClient.setPassword(password);
        loginPageFirstClient.login();
        Assertions.assertEquals(loginPageFirstClient.getToastMessage(), "You successfully logged in.");
        Assertions.assertEquals(REDIRECT_URL, firstClientDriver.getCurrentUrl());
    }

    private void loginDriver(String email, String password) {
        assertTrue(homePageDriver.isHomePageLoaded());
        homePageDriver.clickLoginBtn();
        loginPageDriver.setEmail(email);
        loginPageDriver.setPassword(password);
        loginPageDriver.login();
        Assertions.assertEquals(loginPageDriver.getToastMessage(), "You successfully logged in.");
        Assertions.assertEquals(REDIRECT_URL, driverDriver.getCurrentUrl());
    }

    private void switchWindows(WebDriver driverToMaximize, WebDriver driverToMinimize, WebDriver driverToMinimize2) {
        driverToMinimize.manage().window().setPosition(new Point(-2000, 0));
        driverToMinimize2.manage().window().setPosition(new Point(-2000, 0));
        driverToMaximize.manage().window().maximize();
    }

}
