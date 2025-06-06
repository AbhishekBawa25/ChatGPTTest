package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.FileInputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class ChatGPTTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private Properties config;
    private ChatGPTPage chatGPTPage;

    @BeforeClass
    @Parameters("browser")
    public void setUp(String browser) throws Exception {
        // Load config
        config = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            config.load(fis);
        }

        URL gridUrl = new URL(config.getProperty("grid.url"));
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            driver = new RemoteWebDriver(gridUrl, options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            driver = new RemoteWebDriver(gridUrl, options);
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        chatGPTPage = new ChatGPTPage(driver, wait);
        driver.get(config.getProperty("app.url"));
    }

    
    
// _____________Chrome driver setup_____________   
//    public void setUp() throws Exception {
//        // Load config
//        config = new Properties();
//        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
//        config.load(fis);
//
//        // Set up ChromeDriver using WebDriverManager
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//        //driver.get(config.getProperty("app.url"));
//        chatGPTPage = new ChatGPTPage(driver, wait);
//        driver.get(config.getProperty("app.url"));
//    }

    
    @Test(priority = 1)
    public void testDismissPopup() {
        chatGPTPage.dismissPopup();
    }


    @Test(priority = 2)
    public void testClickNewChatButton() {
        chatGPTPage.clickNewChatButton();
        
    }

    @Test(priority = 3)
    public void testEnterTextAndSend() {
        String message = "Hello ChatGPT, this is an automation test.";
        chatGPTPage.enterTextAndSend(message);
        
    }

    @Test(priority = 4)
    public void testClickSignUpButton() {
        chatGPTPage.clickSignUpButton();
    }
    
    @Test(priority = 5)
    public void testClickLoginButton() throws InterruptedException {
        chatGPTPage.clickLoginButton();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}