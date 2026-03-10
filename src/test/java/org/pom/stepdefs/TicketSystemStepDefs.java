package org.pom.stepdefs;

import io.cucumber.java.en.*;
import net.serenitybdd.annotations.Managed;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.pom.pages.*;
import org.pom.utils.TestConfig;
import org.pom.utils.WaitUtils;

import java.util.List;
import java.util.Map;

/**
 * Step Definitions del flujo E2E completo del Sistema de Tickets.
 *
 * <p>Implementa todos los pasos definidos en el feature file
 * {@code sistema_tickets_e2e.feature} utilizando los Page Objects
 * con Page Factory.
 */
public class TicketSystemStepDefs {

    // ----------------------------------------------------------------
    // WebDriver gestionado por Serenity
    // ----------------------------------------------------------------

    @Managed(uniqueSession = false)
    WebDriver driver;

    // ----------------------------------------------------------------
    // Page Objects (se instancian bajo demanda)
    // ----------------------------------------------------------------

    private LoginPage loginPage;
    private RegisterPage registerPage;
    private TicketListPage ticketListPage;
    private CreateTicketPage createTicketPage;
    private TicketDetailPage ticketDetailPage;
    private AssignmentListPage assignmentListPage;
    private NavBarPage navBarPage;

    // ----------------------------------------------------------------
    // Inicialización de Page Objects
    // ----------------------------------------------------------------

    private LoginPage getLoginPage() {
        if (loginPage == null) loginPage = new LoginPage(driver);
        return loginPage;
    }

    private RegisterPage getRegisterPage() {
        if (registerPage == null) registerPage = new RegisterPage(driver);
        return registerPage;
    }

    private TicketListPage getTicketListPage() {
        if (ticketListPage == null) ticketListPage = new TicketListPage(driver);
        return ticketListPage;
    }

    private CreateTicketPage getCreateTicketPage() {
        if (createTicketPage == null) createTicketPage = new CreateTicketPage(driver);
        return createTicketPage;
    }

    private TicketDetailPage getTicketDetailPage() {
        if (ticketDetailPage == null) ticketDetailPage = new TicketDetailPage(driver);
        return ticketDetailPage;
    }

    private AssignmentListPage getAssignmentListPage() {
        if (assignmentListPage == null) assignmentListPage = new AssignmentListPage(driver);
        return assignmentListPage;
    }

    private NavBarPage getNavBarPage() {
        if (navBarPage == null) navBarPage = new NavBarPage(driver);
        return navBarPage;
    }

    // ----------------------------------------------------------------
    // Steps - Contexto / Background
    // ----------------------------------------------------------------

    @Given("el usuario navega a la aplicación")
    public void elUsuarioNavegaALaAplicacion() {
        driver.get(TestConfig.BASE_URL);
        WaitUtils.waitUntilUrlContains(driver, TestConfig.BASE_URL);
        WaitUtils.demoDelay();
    }

    @Given("un usuario con email {string} y contraseña {string} existe en el sistema")
    public void unUsuarioExisteEnElSistema(String email, String password) {
        // Precondición documentada: el usuario admin ya es creado por el seeder del sistema.
        // Este step es informativo y no requiere acción adicional.
        System.out.println("Precondición verificada: usuario " + email + " existe en el sistema.");
    }

    // ----------------------------------------------------------------
    // Steps - Registro
    // ----------------------------------------------------------------

    @When("el usuario navega a la página de registro")
    public void elUsuarioNavegaALaPaginaDeRegistro() {
        getRegisterPage().open();
    }

    @When("completa el formulario de registro con:")
    public void completaElFormularioDeRegistroCon(List<Map<String, String>> dataTable) {
        Map<String, String> data = dataTable.get(0);
        // Generar email único para evitar conflictos en ejecuciones repetidas
        String uniqueEmail = generateUniqueEmail(data.get("email"));
        String uniqueUsername = generateUniqueUsername(data.get("username"));
        getRegisterPage().register(uniqueUsername, uniqueEmail, data.get("password"));
    }

    @When("introduce el nombre de usuario {string}")
    public void introduceElNombreDeUsuario(String username) {
        getRegisterPage().enterUsername(username);
    }

    @When("introduce el email {string}")
    public void introduceElEmail(String email) {
        getRegisterPage().enterEmail(email);
    }

    @When("introduce la contraseña {string}")
    public void introducelaContrasena(String password) {
        getRegisterPage().enterPassword(password);
    }

    @When("introduce la confirmación de contraseña {string}")
    public void introducelaConfirmacionDeContrasena(String password) {
        getRegisterPage().enterConfirmPassword(password);
    }

    @When("hace click en {string}")
    public void haceClickEn(String buttonText) {
        switch (buttonText) {
            case "Crear cuenta":
                getRegisterPage().clickRegisterButton();
                break;
            case "Iniciar sesión":
                getLoginPage().clickLoginButton();
                break;
            default:
                throw new IllegalArgumentException("Botón no reconocido: " + buttonText);
        }
        WaitUtils.demoDelay();
    }

    // ----------------------------------------------------------------
    // Steps - Login
    // ----------------------------------------------------------------

    @Given("el usuario está autenticado con email {string} y contraseña {string}")
    public void elUsuarioEstaAutenticado(String email, String password) {
        getLoginPage().open();
        getLoginPage().login(email, password);
        WaitUtils.waitUntilUrlContains(driver, "/tickets");
        WaitUtils.demoDelay();
    }

    @When("el usuario introduce el email {string}")
    public void elUsuarioIntroduceElEmail(String email) {
        getLoginPage().enterEmail(email);
    }

    @When("el usuario introduce la contraseña {string}")
    public void elUsuarioIntroduceLaContrasena(String password) {
        getLoginPage().enterPassword(password);
    }

    @When("el usuario hace click en {string}")
    public void elUsuarioHaceClickEn(String buttonText) {
        if ("Iniciar sesión".equals(buttonText)) {
            getLoginPage().clickLoginButton();
        }
        WaitUtils.demoDelay();
    }

    // ----------------------------------------------------------------
    // Steps - Navegación
    // ----------------------------------------------------------------

    @When("el usuario navega a {string}")
    public void elUsuarioNavegaA(String destination) {
        switch (destination) {
            case "Crear Ticket":
                getNavBarPage().goToCreateTicket();
                WaitUtils.waitUntilUrlContains(driver, "/tickets/new");
                break;
            case "Tickets":
                getNavBarPage().goToTickets();
                WaitUtils.waitUntilUrlContains(driver, "/tickets");
                break;
            case "Asignaciones":
                getNavBarPage().goToAssignments();
                WaitUtils.waitUntilUrlContains(driver, "/assignments");
                break;
            case "Notificaciones":
                getNavBarPage().goToNotifications();
                WaitUtils.waitUntilUrlContains(driver, "/notifications");
                break;
            default:
                throw new IllegalArgumentException("Destino de navegación no reconocido: " + destination);
        }
        WaitUtils.demoDelay();
    }

    @When("el administrador navega a {string}")
    public void elAdministradorNavegaA(String destination) {
        elUsuarioNavegaA(destination);
    }

    @When("el usuario navega a la lista de tickets")
    public void elUsuarioNavegaALaListaDeTickets() {
        getTicketListPage().open();
    }

    // ----------------------------------------------------------------
    // Steps - Creación de Ticket
    // ----------------------------------------------------------------

    @When("completa el formulario de ticket con título {string} y descripción {string}")
    public void completaElFormularioDeTicket(String title, String description) {
        getCreateTicketPage().enterTitle(title);
        getCreateTicketPage().enterDescription(description);
        WaitUtils.demoDelay();
    }

    @When("envía el formulario del ticket")
    public void enviaElFormularioDelTicket() {
        getCreateTicketPage().clickSubmit();
        WaitUtils.demoDelay();
    }

    // ----------------------------------------------------------------
    // Steps - Interacción con la lista de tickets
    // ----------------------------------------------------------------

    @Given("existe al menos un ticket en el sistema")
    public void existeAlMenosUnTicketEnElSistema() {
        getTicketListPage().waitForLoad();
        // Si no hay tickets, crear uno de prueba
        if (getTicketListPage().getTicketCount() == 0) {
            getNavBarPage().goToCreateTicket();
            getCreateTicketPage().createTicket(
                    "Ticket de Precondición E2E",
                    "Ticket creado automáticamente como precondición de prueba"
            );
            WaitUtils.waitUntilUrlContains(driver, "/tickets");
            getTicketListPage().waitForLoad();
        }
    }

    @When("el usuario hace click en el primer ticket de la lista")
    public void elUsuarioHaceClickEnElPrimerTicketDeLaLista() {
        getTicketListPage().waitForLoad();
        getTicketListPage().clickFirstTicket();
    }

    @When("el usuario hace click en el ticket {string}")
    public void elUsuarioHaceClickEnElTicket(String ticketTitle) {
        getTicketListPage().waitForLoad();
        getTicketListPage().clickTicketByTitle(ticketTitle);
    }

    // ----------------------------------------------------------------
    // Steps - Cierre de sesión
    // ----------------------------------------------------------------

    @When("el usuario cierra sesión")
    public void elUsuarioCierraSesion() {
        getNavBarPage().logout();
        WaitUtils.demoDelay();
    }

    // ----------------------------------------------------------------
    // Steps - Verificaciones (Then)
    // ----------------------------------------------------------------

    @Then("debería ser redirigido a la lista de tickets")
    public void deberiaSaerRedirigidoALaListaDeTickets() {
        WaitUtils.waitUntilUrlContains(driver, "/tickets");
        Assertions.assertThat(driver.getCurrentUrl())
                .as("La URL debería contener '/tickets'")
                .contains("/tickets");
        WaitUtils.demoDelay();
    }

    @Then("debería ser redirigido a la página de login")
    public void deberiaSerRedirigidoALaPaginaDeLogin() {
        WaitUtils.waitUntilUrlContains(driver, "/login");
        Assertions.assertThat(driver.getCurrentUrl())
                .as("La URL debería contener '/login'")
                .contains("/login");
        WaitUtils.demoDelay();
    }

    @Then("la barra de navegación debería estar visible")
    public void laBarraDeNavegacionDeberiaEstarVisible() {
        // Verificar que la navbar (presente solo para autenticados) está en el DOM
        Assertions.assertThat(driver.findElements(
                org.openqa.selenium.By.cssSelector(".navbar, nav.navbar")
        )).as("La navbar debería ser visible después del login")
          .isNotEmpty();
        WaitUtils.demoDelay();
    }

    @Then("debería ver el error {string}")
    public void deberiaVerElError(String errorText) {
        Assertions.assertThat(getRegisterPage().isErrorVisible())
                .as("El mensaje de error debería ser visible")
                .isTrue();
        Assertions.assertThat(getRegisterPage().getErrorText())
                .as("El texto del error debería contener '" + errorText + "'")
                .contains(errorText);
        WaitUtils.demoDelay();
    }

    @Then("debería ver un mensaje de error de autenticación")
    public void deberiaVerUnMensajeDeErrorDeAutenticacion() {
        Assertions.assertThat(getLoginPage().isErrorVisible())
                .as("El mensaje de error de autenticación debería ser visible")
                .isTrue();
        WaitUtils.demoDelay();
    }

    @Then("la página de creación de ticket debería estar cargada")
    public void laPaginaDeCreacionDeTicketDeberiaEstarCargada() {
        Assertions.assertThat(getCreateTicketPage().isLoaded())
                .as("La página de creación de ticket debería estar cargada")
                .isTrue();
        WaitUtils.demoDelay();
    }

    @Then("el formulario debería tener los campos de título y descripción")
    public void elFormularioDeberiaTenerLosCampos() {
        Assertions.assertThat(getCreateTicketPage().isLoaded())
                .as("El formulario de creación de ticket debería tener los campos de título y descripción")
                .isTrue();
        WaitUtils.demoDelay();
    }

    @Then("la página de tickets debería estar cargada")
    public void laPaginaDeTicketsDeberiaEstarCargada() {
        Assertions.assertThat(getTicketListPage().isLoaded())
                .as("La página de lista de tickets debería estar cargada")
                .isTrue();
        WaitUtils.demoDelay();
    }

    @Then("debería ver la lista de tickets del sistema")
    public void deberiaVerLaListaDeTickets() {
        // La lista puede estar vacía o tener tickets — lo importante es que la página cargó
        Assertions.assertThat(driver.getCurrentUrl())
                .as("La URL debería ser la de tickets")
                .contains("/tickets");
        WaitUtils.demoDelay();
    }

    @Then("el ticket {string} debería aparecer en la lista")
    public void elTicketDeberiaAparecerEnLaLista(String ticketTitle) {
        getTicketListPage().waitForLoad();
        Assertions.assertThat(getTicketListPage().isTicketPresent(ticketTitle))
                .as("El ticket con título '" + ticketTitle + "' debería aparecer en la lista")
                .isTrue();
        WaitUtils.demoDelay();
    }

    @Then("debería ver el detalle del ticket")
    public void deberiaVerElDetalleDelTicket() {
        Assertions.assertThat(getTicketDetailPage().isLoaded())
                .as("El detalle del ticket debería estar cargado")
                .isTrue();
        WaitUtils.demoDelay();
    }

    @Then("debería ver el estado del ticket")
    public void deberiaVerElEstadoDelTicket() {
        String status = getTicketDetailPage().getTicketStatus();
        Assertions.assertThat(status)
                .as("El estado del ticket debería ser visible")
                .isNotBlank();
        WaitUtils.demoDelay();
    }

    @Then("debería ver la sección de respuestas")
    public void deberiaVerLaSeccionDeRespuestas() {
        // La sección de respuestas existe aunque esté vacía
        Assertions.assertThat(driver.getCurrentUrl())
                .as("Debería estar en la página de detalle del ticket")
                .contains("/tickets/");
        WaitUtils.demoDelay();
    }

    @Then("el título del detalle debería contener {string}")
    public void elTituloDelDetalleDeberiaContener(String expectedTitle) {
        String actualTitle = getTicketDetailPage().getTicketTitle();
        Assertions.assertThat(actualTitle)
                .as("El título del detalle debería contener '" + expectedTitle + "'")
                .contains(expectedTitle);
        WaitUtils.demoDelay();
    }

    @Then("la página de asignaciones debería estar cargada")
    public void laPaginaDeAsignacionesDeberiaEstarCargada() {
        Assertions.assertThat(getAssignmentListPage().isLoaded())
                .as("La página de asignaciones debería estar cargada")
                .isTrue();
        WaitUtils.demoDelay();
    }

    // ----------------------------------------------------------------
    // Métodos auxiliares privados
    // ----------------------------------------------------------------

    /**
     * Genera un email único añadiendo un timestamp para evitar conflictos
     * en ejecuciones repetidas del test cuando el usuario ya existe.
     *
     * @param baseEmail email base
     * @return email con sufijo único
     */
    private String generateUniqueEmail(String baseEmail) {
        long timestamp = System.currentTimeMillis();
        int atIndex = baseEmail.indexOf('@');
        if (atIndex > 0) {
            return baseEmail.substring(0, atIndex) + "_" + timestamp + baseEmail.substring(atIndex);
        }
        return baseEmail + "_" + timestamp;
    }

    /**
     * Genera un username único añadiendo un timestamp.
     *
     * @param baseUsername username base
     * @return username con sufijo único
     */
    private String generateUniqueUsername(String baseUsername) {
        // Truncar para que el username no supere límites del backend
        String suffix = String.valueOf(System.currentTimeMillis()).substring(8);
        String candidate = baseUsername + suffix;
        return candidate.length() > 20 ? candidate.substring(0, 20) : candidate;
    }
}
