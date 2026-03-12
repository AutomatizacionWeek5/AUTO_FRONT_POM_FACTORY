package org.pom.pages.shared;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.pom.utils.wait.WaitUtils;

public class NotificationListPage {

    private final WebDriver driver;

    public NotificationListPage(WebDriver driver) {
        this.driver = driver;
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
}
