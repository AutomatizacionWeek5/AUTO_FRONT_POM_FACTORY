package org.pom.pages.tickets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.pom.utils.config.TestConfig;
import org.pom.utils.wait.WaitUtils;

import java.util.List;

public class AssignmentListPage {

    private final WebDriver driver;

    @FindBy(css = ".page-header__title, h1")
    private WebElement pageTitle;

    
    @FindBy(css = ".assignment-card, .assignment-item")
    private List<WebElement> assignmentCards;

    
    @FindBy(css = "select.assign-select, select[name='agent'], .ticket-assign select")
    private List<WebElement> agentSelectors;

    
    @FindBy(css = "button.assign-btn, .ticket-assign button[type='submit']")
    private List<WebElement> assignButtons;

    
    @FindBy(css = ".empty-state, .empty-state__message")
    private WebElement emptyState;

    public AssignmentListPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    
    public void open() {
        driver.get(TestConfig.BASE_URL + "/assignments");
        WaitUtils.waitUntilUrlContains(driver, "/assignments");
        WaitUtils.demoDelay();
    }

    
    public void selectFirstAgent(String agentName) {
        if (!agentSelectors.isEmpty()) {
            WaitUtils.waitUntilClickable(driver, agentSelectors.get(0));
            Select select = new Select(agentSelectors.get(0));
            select.selectByVisibleText(agentName);
            WaitUtils.demoDelay();
        }
    }

    
    public void clickFirstAssignButton() {
        if (!assignButtons.isEmpty()) {
            WaitUtils.waitUntilClickable(driver, assignButtons.get(0));
            assignButtons.get(0).click();
            WaitUtils.demoDelay();
        }
    }

    
    public String getPageTitle() {
        WaitUtils.waitUntilVisible(driver, pageTitle);
        return pageTitle.getText();
    }

    
    public int getAssignmentCount() {
        try {
            return assignmentCards.size();
        } catch (Exception e) {
            return 0;
        }
    }

    
    public boolean isLoaded() {
        try {
            WaitUtils.waitUntilUrlContains(driver, "/assignments");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    
    public boolean isTicketAssigned(String ticketTitle) {
        for (WebElement card : assignmentCards) {
            try {
                WebElement title = card.findElement(
                    By.cssSelector(".assignment-ticket-title, h3, h4, .ticket-title"));
                if (title.getText().contains(ticketTitle)) {
                    return true;
                }
            } catch (Exception ignored) {}
        }
        return false;
    }
}
