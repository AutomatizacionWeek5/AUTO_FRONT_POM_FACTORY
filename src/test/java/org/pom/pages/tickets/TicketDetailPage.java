package org.pom.pages.tickets;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.pom.utils.wait.WaitUtils;

public class TicketDetailPage {

    private final WebDriver driver;

    @FindBy(css = ".ticket-detail-title")
    private WebElement ticketTitle;

    public TicketDetailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    
    public String getTicketTitle() {
        WaitUtils.waitUntilVisible(driver, ticketTitle);
        return ticketTitle.getText();
    }

    
    public boolean isLoaded() {
        try {
            WaitUtils.waitUntilVisible(driver, ticketTitle);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
