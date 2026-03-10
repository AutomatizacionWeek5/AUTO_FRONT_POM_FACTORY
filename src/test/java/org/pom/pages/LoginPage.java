package org.pom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.pom.utils.WaitUtils;

/**
 * Page Object para la página de Login.
 *
 * <p>Ruta: {@code /login}
 *
 * <p>Representa los elementos del formulario de autenticación con
 * anotaciones {@link FindBy} de Page Factory.
 */
public class LoginPage {

    private final WebDriver driver;

    // ----------------------------------------------------------------
    // Elementos del formulario - localizados con @FindBy (Page Factory)
    // ----------------------------------------------------------------

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "button.btn-primary[type='submit']")
    private WebElement loginButton;

    @FindBy(css = "a.btn-secondary[href='/register']")
    private WebElement registerLink;

    @FindBy(css = ".auth-error")
    private WebElement errorMessage;

    @FindBy(css = ".auth-title")
    private WebElement pageTitle;

    // ----------------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------------

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ----------------------------------------------------------------
    // Acciones
    // ----------------------------------------------------------------

    /**
     * Navega directamente a la página de login.
     */
    public void open() {
        driver.get(org.pom.utils.TestConfig.BASE_URL + "/login");
        WaitUtils.waitUntilVisible(driver, emailInput);
        WaitUtils.demoDelay();
    }

    /**
     * Introduce el correo en el campo email.
     *
     * @param email correo del usuario
     */
    public void enterEmail(String email) {
        WaitUtils.waitUntilClickable(driver, emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
        WaitUtils.demoDelay();
    }

    /**
     * Introduce la contraseña.
     *
     * @param password contraseña del usuario
     */
    public void enterPassword(String password) {
        WaitUtils.waitUntilClickable(driver, passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        WaitUtils.demoDelay();
    }

    /**
     * Hace click en el botón de login.
     */
    public void clickLoginButton() {
        WaitUtils.waitUntilClickable(driver, loginButton);
        loginButton.click();
        WaitUtils.demoDelay();
    }

    /**
     * Navega a la página de registro.
     */
    public void clickRegisterLink() {
        WaitUtils.waitUntilClickable(driver, registerLink);
        registerLink.click();
        WaitUtils.demoDelay();
    }

    /**
     * Ejecuta el flujo completo de login (email + password + click).
     *
     * @param email    correo del usuario
     * @param password contraseña del usuario
     */
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    // ----------------------------------------------------------------
    // Verificaciones
    // ----------------------------------------------------------------

    /**
     * Verifica si el mensaje de error es visible.
     *
     * @return {@code true} si el error está visible
     */
    public boolean isErrorVisible() {
        try {
            WaitUtils.waitUntilVisible(driver, errorMessage);
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Devuelve el texto del mensaje de error.
     *
     * @return texto del error o cadena vacía si no hay error
     */
    public String getErrorText() {
        try {
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Devuelve el título visible de la página de login.
     *
     * @return texto del título
     */
    public String getPageTitle() {
        WaitUtils.waitUntilVisible(driver, pageTitle);
        return pageTitle.getText();
    }

    /**
     * Verifica que la página de login esté cargada comprobando el título.
     *
     * @return {@code true} si el título contiene "TicketSystem"
     */
    public boolean isLoaded() {
        try {
            return getPageTitle().contains("TicketSystem");
        } catch (Exception e) {
            return false;
        }
    }
}
