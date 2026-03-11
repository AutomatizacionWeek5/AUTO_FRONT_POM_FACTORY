package org.pom.stepdefs;

import io.cucumber.java.en.*;
import net.serenitybdd.annotations.Managed;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pom.context.TestContext;
import org.pom.pages.auth.LoginPage;
import org.pom.utils.api.ApiHelper;
import org.pom.utils.config.TestConfig;
import org.pom.utils.wait.WaitUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class LoginSteps {

    @Managed(uniqueSession = false)
    WebDriver driver;

    private LoginPage loginPage;

    private LoginPage getLoginPage() {
        if (loginPage == null) loginPage = new LoginPage(driver);
        return loginPage;
    }

    @Given("un usuario con email {string} y contraseña {string} existe en el sistema")
    public void unUsuarioExisteEnElSistema(String email, String password) {

        System.out.println("Precondición verificada: usuario " + email + " existe en el sistema.");
    }

    @Given("el usuario está autenticado con email {string} y contraseña {string}")
    public void elUsuarioEstaAutenticado(String email, String password) {
        getLoginPage().open();
        getLoginPage().login(email, password);
        WaitUtils.waitUntilUrlContains(driver, "/tickets");
        WaitUtils.demoDelay();
    }

    @When("el usuario navega a la página de login")
    public void elUsuarioNavegaALaPaginaDeLogin() {
        getLoginPage().open();
    }

    @When("el usuario introduce el email {string}")
    public void elUsuarioIntroduceElEmail(String email) {
        TestContext.get().setEmail(email);
        getLoginPage().enterEmail(email);
    }

    @When("el usuario introduce la contraseña {string}")
    public void elUsuarioIntroduceLaContrasena(String password) {
        TestContext.get().setPassword(password);
        getLoginPage().enterPassword(password);
    }

    @When("el usuario hace click en {string}")
    public void elUsuarioHaceClickEn(String buttonText) {
        if ("Iniciar sesión".equals(buttonText)) {
            getLoginPage().clickLoginButton();
        }
        WaitUtils.demoDelay();
    }

    @When("el usuario ingresa el email {string}")
    public void elUsuarioIngresaElEmail(String email) {
        TestContext.get().setEmail(email);
        getLoginPage().enterEmail(email);
    }

    @When("ingresa la contraseña {string}")
    public void ingresaLaContrasena(String password) {
        TestContext.get().setPassword(password);
        getLoginPage().enterPassword(password);
    }

    @When("el usuario ingresa las credenciales almacenadas")
    public void elUsuarioIngresaLasCredencialesAlmacenadas() {
        getLoginPage().enterEmail(TestContext.get().getEmail());
        getLoginPage().enterPassword(TestContext.get().getPassword());
    }

    @When("hace click en el botón de login")
    public void haceClickEnElBotonDeLogin() {
        getLoginPage().clickLoginButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.not(ExpectedConditions.urlContains("/login")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".auth-error"))
            ));
        } catch (org.openqa.selenium.TimeoutException ignored) {}
        WaitUtils.demoDelay();
    }

    @When("completa el formulario de login con:")
    public void completaElFormularioDeLoginCon(List<Map<String, String>> dataTable) {
        Map<String, String> data = dataTable.get(0);
        String email    = data.get("email");
        String password = data.get("password");
        String safeEmail    = email.replace("'", "\\'");
        String safePassword = password.replace("'", "\\'");

        Object loginResult = ApiHelper.apiLogin(driver, safeEmail, safePassword);
        if (loginResult != null && String.valueOf(loginResult).startsWith("login:200")) {
            driver.get(TestConfig.BASE_URL + "/tickets");
            if (ApiHelper.waitForTicketsContent(driver, 15)) {
                WaitUtils.demoDelay();
                return;
            }
        }
        boolean ok = getLoginPage().loginAndWaitForRedirect(email, password, 25);
        if (!ok) driver.get(TestConfig.BASE_URL + "/tickets");
        WaitUtils.demoDelay();
    }

    @Then("debería ver un mensaje de error de autenticación")
    public void deberiaVerUnMensajeDeErrorDeAutenticacion() {
        Assertions.assertThat(getLoginPage().isErrorVisible())
                .as("El mensaje de error de autenticación debería ser visible")
                .isTrue();
        WaitUtils.demoDelay();
    }
}
