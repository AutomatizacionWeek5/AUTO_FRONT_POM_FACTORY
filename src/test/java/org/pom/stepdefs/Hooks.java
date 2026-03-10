package org.pom.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.serenitybdd.annotations.Managed;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Hooks de Cucumber para configurar y limpiar el entorno de pruebas.
 *
 * <p>{@code @Before} y {@code @After} se ejecutan antes y después de cada
 * escenario. Son el lugar apropiado para inicializar el WebDriver y hacer
 * captura de pantalla en caso de fallo.
 */
public class Hooks {

    @Managed
    WebDriver driver;

    /**
     * Setup previo a cada escenario.
     * Serenity gestiona el ciclo de vida del WebDriver mediante {@code @Managed},
     * por lo que aquí solo es necesario registrar el inicio del escenario.
     *
     * @param scenario meta-información del escenario actual
     */
    @Before
    public void setUp(Scenario scenario) {
        System.out.println("=================================================");
        System.out.println("Iniciando escenario: " + scenario.getName());
        System.out.println("Tags: " + scenario.getSourceTagNames());
        System.out.println("=================================================");
    }

    /**
     * Teardown posterior a cada escenario.
     * Si el escenario falló, captura una screenshot y la adjunta al reporte.
     *
     * @param scenario meta-información del escenario finalizado
     */
    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Captura de pantalla - Fallo");
        }
        System.out.println("=================================================");
        System.out.println("Escenario finalizado: " + scenario.getName());
        System.out.println("Estado: " + (scenario.isFailed() ? "FALLIDO" : "EXITOSO"));
        System.out.println("=================================================");
    }
}
