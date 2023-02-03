package com.kts.taxify.e2e.tests;

import com.kts.taxify.e2e.pages.HomePage;
import com.kts.taxify.e2e.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestLogin{
    public static WebDriver driver;

    private static HomePage homePage;
    private static LoginPage loginPage;
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

    @BeforeEach
    public void resetPage() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
    }

    @Test
    @Order(0)
    public void testInvalidMail() {
        assertTrue(homePage.isHomePageLoaded());
        homePage.clickLoginBtn();
        loginPage.setEmail("asd");
        loginPage.setPassword("123");
        loginPage.login();
        Assertions.assertTrue(loginPage.getToastMessage().contains("email must be a well-formed email address"));
    }

    @Test
    @Order(1)
    public void badCredentialsTest() {
        assertTrue(homePage.isHomePageLoaded());
        homePage.clickLoginBtn();
        loginPage.setEmail("milicftn@gmail.com");
        loginPage.setPassword("123!");
        loginPage.login();
        Assertions.assertEquals(loginPage.getToastMessage(), "Bad login credentials");
    }

    @Test
    @Order(2)
    public void emptyPasswordTest() {
        assertTrue(homePage.isHomePageLoaded());
        homePage.clickLoginBtn();
        loginPage.setEmail("milicftn@gmail.com");
        loginPage.clearPassword();
        loginPage.login();
        Assertions.assertEquals(loginPage.getToastMessage(), "password must not be empty");
    }

    @Test
    @Order(3)
    public void successLoginTest() {
        assertTrue(homePage.isHomePageLoaded());
        homePage.clickLoginBtn();
        loginPage.setEmail("milicftn@gmail.com");
        loginPage.setPassword("Micomilic123!");
        loginPage.login();
        Assertions.assertEquals(loginPage.getToastMessage(), "You successfully logged in.");
        Assertions.assertEquals(REDIRECT_URL, driver.getCurrentUrl());
    }
}
