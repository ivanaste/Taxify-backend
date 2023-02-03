package com.kts.taxify.e2e.tests;

import com.kts.taxify.e2e.pages.HomePage;
import com.kts.taxify.e2e.pages.LoginPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRideOrderAndFinishSuccess {

    public static WebDriver clientDriver;
    public static WebDriver driverDriver;
    private static HomePage homePageClient;
    private static LoginPage loginPageClient;

    private static HomePage homePageDriver;
    private static LoginPage loginPageDriver;

    private static final String REDIRECT_URL = "https://localhost:4200/maps";
    private static final String FILTER_ORDER_RIDE_URL = "https://localhost:4200/maps?filterDriversMode=true";

    @BeforeAll
    static void initializeWebDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions handlingSSL = new ChromeOptions();
        handlingSSL.setAcceptInsecureCerts(true);
        clientDriver = new ChromeDriver(handlingSSL);
        clientDriver.manage().window().maximize();
        driverDriver = new ChromeDriver(handlingSSL);
        driverDriver.manage().window().maximize();
        homePageDriver = new HomePage(driverDriver);
        loginPageDriver = new LoginPage(driverDriver);
        homePageClient = new HomePage(clientDriver);
        loginPageClient = new LoginPage(clientDriver);
    }

    @AfterAll
    static void quitDriver() {
        clientDriver.quit();
        driverDriver.quit();
    }


    @Test
    public void testOrderRide() {
        loginDriver("brzi@gmail.com", "Micomilic123!");
        driverDriver.manage().window().setPosition(new Point(-2000, 0));
        clientDriver.manage().window().maximize();
        loginClient("prijovic.uros13@gmail.com", "Uros123!");
        setRoute();
        setFiltersAndPayment();
        homePageClient.searchForRide();

        switchWindows(driverDriver, clientDriver);
        homePageDriver.waitForToastMessage("Ride has been assigned to you.");
        homePageDriver.waitForToastMessage("Ride has been assigned to you.");

        switchWindows(clientDriver, driverDriver);
        homePageClient.waitForToastMessage("Your ride has been accepted.");
        homePageClient.waitForToastMessage("Your ride has been accepted.");
        homePageClient.waitForToastMessage("Vehicle has arrived on your destination.");
        homePageClient.waitForToastMessage("Vehicle has arrived on your destination.");

        switchWindows(driverDriver, clientDriver);
        homePageDriver.startRideDriver();

        switchWindows(clientDriver, driverDriver);
        homePageClient.waitForToastMessage("Your ride has started.");
        homePageClient.waitForToastMessage("Your ride has started.");
        homePageClient.waitForToastMessage("You have arrived on your destination");
        homePageClient.waitForToastMessage("You have arrived on your destination");

        switchWindows(driverDriver, clientDriver);
        homePageDriver.waitForToastMessage("You have arrived on destination.");
        homePageDriver.waitForToastMessage("You have arrived on destination.");

        homePageDriver.endRideDriver();

        homePageDriver.waitForToastMessage("You successfully finished a ride.");
        homePageDriver.waitForToastMessage("You successfully finished a ride.");

        switchWindows(clientDriver, driverDriver);
        homePageClient.waitForToastMessage("Your ride has finished.");
        homePageClient.waitForToastMessage("Your ride has finished.");

    }

    private void setFiltersAndPayment() {
        homePageClient.openPaymentOptions();
        homePageClient.selectCard();
    }

    private void setRoute() {
        homePageClient.setPickup("Dr ivana ribara 5, novi sad");
        homePageClient.setDestination("Tocionica, narodnog fronta");
        homePageClient.waitForRideInfoLabelToBePresent();
        homePageClient.continueButtonClick();
        Assertions.assertEquals(FILTER_ORDER_RIDE_URL, clientDriver.getCurrentUrl());
    }

    private void loginClient(String email, String password) {
        assertTrue(homePageClient.isHomePageLoaded());
        homePageClient.clickLoginBtn();
        loginPageClient.setEmail(email);
        loginPageClient.setPassword(password);
        loginPageClient.login();
        Assertions.assertEquals(loginPageClient.getToastMessage(), "You successfully logged in.");
        Assertions.assertEquals(REDIRECT_URL, clientDriver.getCurrentUrl());
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

    private void switchWindows(WebDriver driverToMaximize, WebDriver driverToMinimize) {
        driverToMinimize.manage().window().setPosition(new Point(-2000, 0));
        driverToMaximize.manage().window().maximize();
    }

}
