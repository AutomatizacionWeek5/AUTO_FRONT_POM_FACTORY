package org.pom.utils.api;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ApiHelper {

    private ApiHelper() {

    }

    
    public static Object apiLogin(WebDriver driver, String safeEmail, String safePassword) {
        try {
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(15));
            return ((JavascriptExecutor) driver).executeAsyncScript(
                "var cb = arguments[arguments.length - 1];" +
                "fetch('http://localhost:8003/api/auth/login/', {" +
                "  method:'POST', credentials:'include'," +
                "  headers:{'Content-Type':'application/json'}," +
                "  body:JSON.stringify({email:'" + safeEmail + "',password:'" + safePassword + "'})" +
                "}).then(function(r){cb('login:'+r.status);})"
                + ".catch(function(e){cb('login_err:'+e);})"
            );
        } catch (Exception e) {
            System.out.println("[WARN] apiLogin error: " + e.getMessage());
            return null;
        }
    }

    
    public static boolean waitForTicketsContent(WebDriver driver, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        try {
            wait.until(d -> {
                if (d.getCurrentUrl().contains("/login")) return true;
                List<WebElement> content = d.findElements(
                    By.cssSelector(".tickets-grid, .empty-state"));
                return !content.isEmpty();
            });
        } catch (org.openqa.selenium.TimeoutException ignored) {

        }
        return !driver.getCurrentUrl().contains("/login");
    }
}
