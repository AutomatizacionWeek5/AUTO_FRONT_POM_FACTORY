package org.pom.pages.auth;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pom.utils.config.TestConfig;
import org.pom.utils.wait.WaitUtils;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "button.btn-primary[type='submit']")
    private WebElement loginButton;

    @FindBy(css = "a.btn-secondary[href='/register']")
    private WebElement registerLink;

    @FindBy(css = ".auth-error")
    private WebElement errorMessage;

    @FindBy(css = ".auth-title")
    private WebElement pageTitle;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(TestConfig.BASE_URL + "/login");
        WaitUtils.waitUntilVisible(driver, emailInput);
        WaitUtils.demoDelay();
    }

    public void enterEmail(String email) {
        WaitUtils.waitUntilClickable(driver, emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
        WaitUtils.demoDelay();
    }

    public void enterPassword(String password) {
        WaitUtils.waitUntilClickable(driver, passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        WaitUtils.demoDelay();
    }

    public void clickLoginButton() {
        WaitUtils.waitUntilClickable(driver, loginButton);
        loginButton.click();
        WaitUtils.demoDelay();
    }

    public void clickRegisterLink() {
        WaitUtils.waitUntilClickable(driver, registerLink);
        registerLink.click();
        WaitUtils.demoDelay();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean loginAndWaitForRedirect(String email, String password, int timeoutSeconds) {
        login(email, password);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.not(ExpectedConditions.urlContains("/login")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".auth-error"))
            ));
        } catch (org.openqa.selenium.TimeoutException ignored) {

        }
        return !driver.getCurrentUrl().contains("/login");
    }

    public boolean isErrorVisible() {
        try {
            WaitUtils.waitUntilVisible(driver, errorMessage);
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorText() {
        try {
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

   
    public String getPageTitle() {
        WaitUtils.waitUntilVisible(driver, pageTitle);
        return pageTitle.getText();
    }

    public boolean isLoaded() {
        try {
            return getPageTitle().contains("TicketSystem");
        } catch (Exception e) {
            return false;
        }
    }
}
