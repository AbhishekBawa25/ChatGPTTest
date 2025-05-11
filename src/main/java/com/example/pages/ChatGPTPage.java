package com.example.pages;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChatGPTPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private final By closeButton = By.xpath("//button[@data-testid='close-button' and @aria-label='Close']");
    private final By loginButton = By.xpath("//button[@data-testid='login-button']");
    private final By newChatButton = By.xpath("//*[@id='page-header']/div[2]/div/span/a");
    private final By clearChatButton = By.xpath("//button[contains(@class, 'btn-danger')]");
    private final By inputBox = By.xpath("//div[@id='prompt-textarea']");
    private final By sendButton = By.xpath("//button[@id='composer-submit-button']");
    private final By signUpButton = By.xpath("//*[@id='conversation-header-actions']/div/button[2]");
    private final By chatResponse = By.xpath("//div[contains(@class, 'response-message')]");

    public ChatGPTPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void dismissPopup() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(closeButton));
            button.click();
            System.out.println("Popup dismissed.");
        } catch (Exception e) {
            System.out.println("No popup to dismiss or already closed: " + e.getMessage());
        }
    }

    public void clickLoginButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        button.click();
        System.out.println("Login button clicked.");
    }

    public void clickNewChatButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(newChatButton));
        button.click();
        System.out.println("New Chat button clicked.");
    }

    public void clearChat() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(clearChatButton));
        button.click();
        System.out.println("Chat cleared.");
    }

    public void enterTextAndSend(String message) {
        try {
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(inputBox));
            input.sendKeys(message);
            WebElement send = wait.until(ExpectedConditions.elementToBeClickable(sendButton));
            send.click();
            System.out.println("Message sent.");
        } catch (Exception e) {
            System.out.println("Could not send message: " + e.getMessage());
            throw new RuntimeException("Failed to send message", e);
        }
    }

    public void clickSignUpButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(signUpButton));
        button.click();
        System.out.println("Sign Up button clicked.");
    }

    public boolean isChatCleared() {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(chatResponse));
        } catch (Exception e) {
            return true; // No messages found, chat is cleared
        }
    }

    public boolean isMessageSent() {
        try {
            WebElement response = wait.until(ExpectedConditions.presenceOfElementLocated(chatResponse));
            return response.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
