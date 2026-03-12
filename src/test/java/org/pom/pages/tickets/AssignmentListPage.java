package org.pom.pages.tickets;

import org.openqa.selenium.WebDriver;
import org.pom.utils.wait.WaitUtils;

public class AssignmentListPage {

    private final WebDriver driver;

    public AssignmentListPage(WebDriver driver) {
        this.driver = driver;
    }

    
    public boolean isLoaded() {
        try {
            WaitUtils.waitUntilUrlContains(driver, "/assignments");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
