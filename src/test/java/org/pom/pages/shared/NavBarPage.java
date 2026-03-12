package org.pom.pages.shared;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.pom.utils.wait.WaitUtils;

public class NavBarPage {

    private final WebDriver driver;

@FindBy(css = "a[href='/tickets'][class*='navbar__link']:not([href='/tickets/new'])")
    private WebElement ticketsNavLink;

    @FindBy(css = "a[href='/tickets/new']")
    private WebElement createTicketNavLink;

    @FindBy(css = "a[href='/notifications']")
    private WebElement notificationsNavLink;

    @FindBy(css = "a[href='/assignments']")
    private WebElement assignmentsNavLink;

    
    @FindBy(css = "button.navbar__logout, button[class*='logout']")
    private WebElement logoutButton;

    public NavBarPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    
    public void goToTickets() {
        WaitUtils.waitUntilClickable(driver, ticketsNavLink);
        ticketsNavLink.click();
        WaitUtils.demoDelay();
    }

    
    public void goToCreateTicket() {
        WaitUtils.waitUntilClickable(driver, createTicketNavLink);
        createTicketNavLink.click();
        WaitUtils.demoDelay();
    }

    
    public void goToNotifications() {
        WaitUtils.waitUntilClickable(driver, notificationsNavLink);
        notificationsNavLink.click();
        WaitUtils.demoDelay();
    }

    
    public void goToAssignments() {
        WaitUtils.waitUntilClickable(driver, assignmentsNavLink);
        assignmentsNavLink.click();
        WaitUtils.demoDelay();
    }

    
    public void logout() {
        WaitUtils.waitUntilClickable(driver, logoutButton);
        logoutButton.click();
        WaitUtils.demoDelay();
    }

}
