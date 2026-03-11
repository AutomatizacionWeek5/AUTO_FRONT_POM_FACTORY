package org.pom.pages.shared;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.pom.utils.wait.WaitUtils;

public class NavBarPage {

    private final WebDriver driver;

    @FindBy(css = "a.navbar__logo, a[href='/tickets'].navbar__logo")
    private WebElement logoLink;

    @FindBy(css = "a[href='/tickets'][class*='navbar__link']:not([href='/tickets/new'])")
    private WebElement ticketsNavLink;

    @FindBy(css = "a[href='/tickets/new']")
    private WebElement createTicketNavLink;

    @FindBy(css = "a[href='/notifications']")
    private WebElement notificationsNavLink;

    @FindBy(css = "a[href='/assignments']")
    private WebElement assignmentsNavLink;

    
    @FindBy(css = ".navbar__badge")
    private WebElement notificationBadge;

    
    @FindBy(css = "button.navbar__logout, button[class*='logout']")
    private WebElement logoutButton;

    
    @FindBy(css = "button.navbar__hamburger")
    private WebElement hamburgerButton;

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

    
    public boolean isAssignmentsLinkVisible() {
        try {
            return assignmentsNavLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    
    public boolean isNotificationBadgeVisible() {
        try {
            return notificationBadge.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    
    public int getNotificationCount() {
        try {
            WaitUtils.waitUntilVisible(driver, notificationBadge);
            return Integer.parseInt(notificationBadge.getText().trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
