package org.pom.pages.tickets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pom.utils.config.TestConfig;
import org.pom.utils.wait.WaitUtils;

import java.time.Duration;
import java.util.List;

public class TicketListPage {

    private final WebDriver driver;

    
    @FindBy(css = ".page-header__title, h1")
    private WebElement pageTitle;

    
    @FindBy(css = ".page-header__subtitle, h2")
    private WebElement pageSubtitle;

    
    @FindBy(css = ".tickets-grid")
    private WebElement ticketsGrid;

    
    @FindBy(css = ".ticket-item")
    private List<WebElement> ticketItems;

    
    @FindBy(css = "a[href='/tickets/new']")
    private WebElement createTicketNavLink;

    
    @FindBy(css = ".empty-state, .empty-state__message")
    private WebElement emptyStateMessage;

    public TicketListPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    
    public void open() {
        driver.get(TestConfig.BASE_URL + "/tickets");
        waitForLoad();
        WaitUtils.demoDelay();
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

    
    public void clickCreateTicket() {
        WaitUtils.waitUntilClickable(driver, createTicketNavLink);
        createTicketNavLink.click();
        WaitUtils.demoDelay();
    }

    
    public void clickFirstTicket() {
        WaitUtils.waitUntilVisible(driver, ticketItems.get(0));
        ticketItems.get(0).click();
        WaitUtils.demoDelay();
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

    
    public String getPageTitle() {
        WaitUtils.waitUntilVisible(driver, pageTitle);
        return pageTitle.getText();
    }

    
    public int getTicketCount() {
        try {
            return ticketItems.size();
        } catch (Exception e) {
            return 0;
        }
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

    
    public boolean isLoaded() {
        try {
            WaitUtils.waitUntilUrlContains(driver, "/tickets");
            WaitUtils.waitUntilVisible(driver, pageTitle);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    
    public boolean isEmptyStateVisible() {
        try {
            return emptyStateMessage.isDisplayed();
        } catch (Exception e) {
            return ticketItems.isEmpty();
        }
    }
}
