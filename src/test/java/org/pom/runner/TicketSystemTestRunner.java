package org.pom.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Test Runner principal del proyecto E2E.
 *
 * <p>Usa {@link CucumberWithSerenity} como runner, que integra Cucumber con
 * Serenity BDD para generar reportes enriquecidos al finalizar la ejecución.
 *
 * <p>Configuración de {@link CucumberOptions}:
 * <ul>
 *   <li>{@code features} — directorio donde se encuentran los archivos {@code .feature}</li>
 *   <li>{@code glue} — paquetes donde Cucumber busca los Step Definitions y Hooks</li>
 *   <li>{@code tags} — permite filtrar escenarios por anotaciones (vacío = todos)</li>
 *   <li>{@code plugin} — formatos de reporte adicionales</li>
 *   <li>{@code monochrome} — salida de consola sin caracteres especiales</li>
 * </ul>
 *
 * <p>Para ejecutar solo un tag específico, añadir la propiedad al comando Gradle:
 * <pre>
 *   ./gradlew test -Dcucumber.filter.tags="@smoke"
 * </pre>
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue     = {
            "org.pom.stepdefs"
        },
        tags     = "",          // Vacío = ejecutar todos los escenarios
        plugin   = {
            "pretty",
            "html:target/cucumber-reports/cucumber-report.html",
            "json:target/cucumber-reports/cucumber.json"
        },
        monochrome   = true,
        publish      = false,
        dryRun       = false
)
public class TicketSystemTestRunner {
    // Clase vacía — JUnit requiere que exista como punto de entrada.
}
