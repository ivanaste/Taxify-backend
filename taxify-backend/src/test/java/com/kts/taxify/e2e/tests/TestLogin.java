package com.kts.taxify.e2e.tests;

import com.kts.taxify.e2e.pages.HomePage;
import com.kts.taxify.e2e.pages.LoginPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLogin{
    public static WebDriver driver;

    private HomePage homePage;
    private LoginPage loginPage;
    private static final String REDIRECT_URL = "https://localhost:4200/maps";

    @BeforeAll
    static void initializeWebDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions handlingSSL = new ChromeOptions();
        handlingSSL.setAcceptInsecureCerts(true);
        driver = new ChromeDriver( handlingSSL);
        driver.manage().window().maximize();
    }

    @AfterAll
    static void quitDriver() {
        driver.quit();
    }



    @Test
    public void loginTest() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);

        assertTrue(homePage.isHomePageLoaded());
        homePage.clickLoginBtn();

        loginPage.setEmail("asd");
        loginPage.setPassword("123");
        loginPage.login();
        Assertions.assertTrue(loginPage.getToastMessage().contains("email must be a well-formed email address"));

        loginPage.setEmail("milicftn@gmail.com");
        loginPage.clearPassword();
        loginPage.login();
        Assertions.assertEquals(loginPage.getToastMessage(), "password must not be empty");

        loginPage.setEmail("milicftn@gmail.com");
        loginPage.setPassword("123!");
        loginPage.login();
        Assertions.assertEquals(loginPage.getToastMessage(), "Bad login credentials");


        loginPage.setEmail("milicftn@gmail.com");
        loginPage.setPassword("Micomilic123!");
        loginPage.login();
        Assertions.assertEquals(loginPage.getToastMessage(), "You successfully logged in.");

        Assertions.assertEquals(REDIRECT_URL, driver.getCurrentUrl());

    }
}
