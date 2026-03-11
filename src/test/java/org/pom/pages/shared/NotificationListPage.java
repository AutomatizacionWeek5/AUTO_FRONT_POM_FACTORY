package org.pom.pages.shared;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.pom.utils.config.TestConfig;
import org.pom.utils.wait.WaitUtils;

import java.util.List;

public class NotificationListPage {

    private final WebDriver driver;

    
    @FindBy(css = ".list-header h1")
    private WebElement pageTitle;

    
    @FindBy(css = ".notification-item")
    private List<WebElement> notificationItems;

    
    @FindBy(css = ".empty-state, .empty-state__message")
    private WebElement emptyState;

    
    @FindBy(css = ".btn-clear")
    private WebElement clearAllButton;

    public NotificationListPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    
    public void open() {
        driver.get(TestConfig.BASE_URL + "/notifications");
        WaitUtils.waitUntilUrlContains(driver, "/notifications");
        WaitUtils.demoDelay();
    }

    
    public boolean isLoaded() {
        try {
            WaitUtils.waitUntilUrlContains(driver, "/notifications");
            boolean hasItems  = !driver.findElements(By.cssSelector(".notification-item")).isEmpty();
            boolean hasEmpty  = !driver.findElements(By.cssSelector(".empty-state")).isEmpty();
            boolean hasHeader = !driver.findElements(By.cssSelector(".list-header h1")).isEmpty();
            return hasHeader && (hasItems || hasEmpty);
        } catch (Exception e) {
            return false;
        }
    }

    
    public int getNotificationCount() {
        try {
            return notificationItems.size();
        } catch (Exception e) {
            return 0;
        }
    }

    
    public boolean hasEmptyState() {
        try {
            return emptyState.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
