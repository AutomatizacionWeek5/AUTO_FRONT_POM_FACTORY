package org.pom.pages.tickets;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.pom.utils.config.TestConfig;
import org.pom.utils.wait.WaitUtils;

import java.util.List;

public class TicketDetailPage {

    private final WebDriver driver;

    @FindBy(css = ".ticket-detail-number")
    private WebElement ticketNumber;

    @FindBy(css = ".ticket-detail-title")
    private WebElement ticketTitle;

    @FindBy(css = ".ticket-detail-status")
    private WebElement ticketStatus;

    @FindBy(css = ".priority-badge")
    private WebElement priorityBadge;

    @FindBy(css = ".ticket-detail-description")
    private WebElement ticketDescription;

    @FindBy(css = ".ticket-detail-meta span")
    private WebElement createdDate;

    @FindBy(css = ".responses-title")
    private WebElement responsesTitle;

    @FindBy(css = ".responses-empty")
    private WebElement noResponsesMessage;

    @FindBy(css = "[data-testid='response-item']")
    private List<WebElement> responseItems;

    @FindBy(css = "textarea[name='response'], .admin-response-textarea, textarea")
    private WebElement adminResponseTextarea;

    @FindBy(css = "button[type='submit'].admin-response-btn, button[type='submit']")
    private WebElement adminResponseSubmitButton;

    public TicketDetailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    
    public void open(int ticketId) {
        driver.get(TestConfig.BASE_URL + "/tickets/" + ticketId);
        WaitUtils.waitUntilVisible(driver, ticketTitle);
        WaitUtils.demoDelay();
    }

    
    public void waitForLoad() {
        WaitUtils.waitUntilUrlContains(driver, "/tickets/");
        WaitUtils.waitUntilVisible(driver, ticketTitle);
    }

    
    public void enterAdminResponse(String responseText) {
        WaitUtils.waitUntilClickable(driver, adminResponseTextarea);
        adminResponseTextarea.clear();
        adminResponseTextarea.sendKeys(responseText);
        WaitUtils.demoDelay();
    }

    
    public void submitAdminResponse() {
        WaitUtils.waitUntilClickable(driver, adminResponseSubmitButton);
        adminResponseSubmitButton.click();
        WaitUtils.demoDelay();
    }

    
    public String getTicketTitle() {
        WaitUtils.waitUntilVisible(driver, ticketTitle);
        return ticketTitle.getText();
    }

    
    public String getTicketStatus() {
        WaitUtils.waitUntilVisible(driver, ticketStatus);
        return ticketStatus.getText();
    }

    
    public String getTicketDescription() {
        WaitUtils.waitUntilVisible(driver, ticketDescription);
        return ticketDescription.getText();
    }

    
    public int getResponseCount() {
        try {
            return responseItems.size();
        } catch (Exception e) {
            return 0;
        }
    }

    
    public boolean hasNoResponses() {
        try {
            return noResponsesMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    
    public boolean isLoaded() {
        try {
            WaitUtils.waitUntilVisible(driver, ticketTitle);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    
    public boolean isAdminResponsePanelVisible() {
        try {
            return adminResponseTextarea.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
