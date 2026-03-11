package org.pom.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue     = {
            "org.pom.stepdefs"
        },
        tags     = "",
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

}
