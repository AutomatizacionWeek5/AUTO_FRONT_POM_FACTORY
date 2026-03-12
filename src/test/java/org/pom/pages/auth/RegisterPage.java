package org.pom.pages.auth;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.pom.utils.config.TestConfig;
import org.pom.utils.wait.WaitUtils;

public class RegisterPage {

    private final WebDriver driver;

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "confirmPassword")
    private WebElement confirmPasswordInput;

    @FindBy(css = "button.btn-primary[type='submit']")
    private WebElement registerButton;

    @FindBy(css = ".auth-error")
    private WebElement errorMessage;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(TestConfig.BASE_URL + "/register");
        WaitUtils.waitUntilVisible(driver, usernameInput);
        WaitUtils.demoDelay();
    }

    public void enterUsername(String username) {
        WaitUtils.waitUntilClickable(driver, usernameInput);
        usernameInput.clear();
        usernameInput.sendKeys(username);
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

    public void enterConfirmPassword(String password) {
        WaitUtils.waitUntilClickable(driver, confirmPasswordInput);
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(password);
        WaitUtils.demoDelay();
    }

    public void clickRegisterButton() {
        WaitUtils.waitUntilClickable(driver, registerButton);
        registerButton.click();
        WaitUtils.demoDelay();
    }

    public void register(String username, String email, String password) {
        enterUsername(username);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(password);
        clickRegisterButton();
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

}
