package com.example.pages;

import org.openqa.selenium.WebDriver;
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
    }

    @BeforeMethod
    public void resetState() {
        driver.get(config.getProperty("app.url"));
    }

    @Test(priority = 1)
    public void testDismissPopup() {
        chatGPTPage.dismissPopup();
    }


    @Test(priority = 2)
    public void testClickNewChatButton() {
        chatGPTPage.clickNewChatButton();
        chatGPTPage.clearChat();
        Assert.assertTrue(chatGPTPage.isChatCleared(), "Chat should be cleared.");
    }

    @Test(priority = 3)
    public void testEnterTextAndSend() {
        String message = "Hello ChatGPT, this is an automation test.";
        chatGPTPage.enterTextAndSend(message);
        Assert.assertTrue(chatGPTPage.isMessageSent(), "Message should be sent successfully.");
    }

    @Test(priority = 4)
    public void testClickSignUpButton() {
        chatGPTPage.clickSignUpButton();
        wait.until(ExpectedConditions.urlContains("signup"));
        Assert.assertTrue(driver.getCurrentUrl().contains("signup"), "Should navigate to signup page.");
        driver.navigate().back();
    }
    
    @Test(priority = 5)
    public void testClickLoginButton() {
        chatGPTPage.clickLoginButton();
        wait.until(ExpectedConditions.urlContains("chat.openai.com"));
        Assert.assertTrue(driver.getCurrentUrl().contains("chat.openai.com"), "Should navigate to ChatGPT login page.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}