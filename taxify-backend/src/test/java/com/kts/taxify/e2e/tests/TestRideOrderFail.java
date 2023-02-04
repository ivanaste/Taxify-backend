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

public class TestRideOrderFail {

    public static WebDriver clientDriver;
    private static HomePage homePageClient;
    private static LoginPage loginPageClient;

    private static final String REDIRECT_URL = "https://localhost:4200/maps";
    private static final String FILTER_ORDER_RIDE_URL = "https://localhost:4200/maps?filterDriversMode=true";

    @BeforeAll
    static void initializeWebDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions handlingSSL = new ChromeOptions();
        handlingSSL.setAcceptInsecureCerts(true);
        clientDriver = new ChromeDriver(handlingSSL);
        clientDriver.manage().window().maximize();
        homePageClient = new HomePage(clientDriver);
        loginPageClient = new LoginPage(clientDriver);
    }

    @AfterAll
    static void quitDriver() {
        clientDriver.quit();
    }


    @Test
    public void testOrderRide() {
        loginClient("prijovic.uros13@gmail.com", "Uros123!");
        this.continueWithoutRoute();
        homePageClient.waitForToastMessage("You cant continue without selecting atleast two places!");
        setRoute();

        homePageClient.searchForRide();
        homePageClient.waitForToastMessage("You must select a payment type before searching a ride!");
        setFiltersAndPayment();

        homePageClient.searchForRide();
        homePageClient.waitForToastMessage("There are none active drivers in area with matching filters!");
    }

    private void setFiltersAndPayment() {
        homePageClient.openPaymentOptions();
        homePageClient.selectCard();
    }

    private void continueWithoutRoute() {
        homePageClient.continueButtonClick();
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

}
