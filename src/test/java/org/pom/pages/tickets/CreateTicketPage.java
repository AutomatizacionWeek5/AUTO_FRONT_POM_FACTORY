package org.pom.pages.tickets;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.pom.utils.config.TestConfig;
import org.pom.utils.wait.WaitUtils;

public class CreateTicketPage {

    private final WebDriver driver;

    
    @FindBy(css = ".create-ticket-title, h1")
    private WebElement pageTitle;

    
    @FindBy(id = "ticket-title")
    private WebElement titleInput;

    
    @FindBy(id = "ticket-description")
    private WebElement descriptionInput;

    
    @FindBy(css = "button.form-button[type='submit']")
    private WebElement submitButton;

    
    @FindBy(css = ".error-alert")
    private WebElement errorAlert;

    public CreateTicketPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    
    public void open() {
        driver.get(TestConfig.BASE_URL + "/tickets/new");
        WaitUtils.waitUntilVisible(driver, titleInput);
        WaitUtils.demoDelay();
    }

    
    public void enterTitle(String title) {
        WaitUtils.waitUntilClickable(driver, titleInput);
        titleInput.clear();
        titleInput.sendKeys(title);
        WaitUtils.demoDelay();
    }

    
    public void enterDescription(String description) {
        WaitUtils.waitUntilClickable(driver, descriptionInput);
        descriptionInput.clear();
        descriptionInput.sendKeys(description);
        WaitUtils.demoDelay();
    }

    
    public void clickSubmit() {
        WaitUtils.waitUntilClickable(driver, submitButton);
        submitButton.click();
        WaitUtils.demoDelay();
    }

    
    public void createTicket(String title, String description) {
        enterTitle(title);
        enterDescription(description);
        clickSubmit();
    }

    
    public String getPageTitle() {
        WaitUtils.waitUntilVisible(driver, pageTitle);
        return pageTitle.getText();
    }

    
    public boolean isLoaded() {
        try {
            WaitUtils.waitUntilVisible(driver, titleInput);
            return titleInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    
    public boolean isErrorVisible() {
        try {
            return errorAlert.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    
    public String getErrorText() {
        try {
            return errorAlert.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
