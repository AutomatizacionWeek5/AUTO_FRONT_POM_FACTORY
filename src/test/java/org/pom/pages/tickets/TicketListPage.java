package org.pom.pages.tickets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pom.utils.wait.WaitUtils;

import java.time.Duration;
import java.util.List;

public class TicketListPage {

    private final WebDriver driver;

    
    @FindBy(css = ".ticket-item")
    private List<WebElement> ticketItems;

    public TicketListPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    
    public void waitForLoad() {
        WebDriverWait urlWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        urlWait.until(ExpectedConditions.urlMatches(".*/tickets$"));

        WebDriverWait contentWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        contentWait.until(d -> {
            boolean hasGrid  = !d.findElements(By.cssSelector(".tickets-grid")).isEmpty();
            boolean hasEmpty = !d.findElements(By.cssSelector(".empty-state")).isEmpty();
            return hasGrid || hasEmpty;
        });
    }

    
    public void clickTicketByTitle(String title) {
        for (WebElement item : ticketItems) {
            WebElement titleEl = item.findElement(By.cssSelector(".ticket-title"));
            if (titleEl.getText().contains(title)) {
                WaitUtils.waitUntilClickable(driver, item);
                item.click();
                WaitUtils.demoDelay();
                return;
            }
        }
        throw new org.openqa.selenium.NoSuchElementException(
                "No se encontró ningún ticket con título: " + title);
    }

    
    public boolean isTicketPresent(String title) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            return wait.until(d -> {
                List<WebElement> items = d.findElements(By.cssSelector(".ticket-item"));
                for (WebElement item : items) {
                    try {
                        WebElement titleEl = item.findElement(By.cssSelector(".ticket-title"));
                        if (titleEl.getText().contains(title)) {
                            return true;
                        }
                    } catch (Exception ignored) {}
                }
                return false;
            });
        } catch (Exception e) {
            return false;
        }
    }
}
