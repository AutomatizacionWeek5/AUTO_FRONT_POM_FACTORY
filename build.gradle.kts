plugins {
    id("java")
}

group = "org.pom"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val serenityCoreVersion = "4.1.20"
val cucumberVersion    = "7.15.0"

dependencies {
    // Serenity BDD + Cucumber
    testImplementation("net.serenity-bdd:serenity-core:$serenityCoreVersion")
    testImplementation("net.serenity-bdd:serenity-cucumber:$serenityCoreVersion")
    testImplementation("net.serenity-bdd:serenity-screenplay-webdriver:$serenityCoreVersion")

    // Cucumber
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")

    // JUnit 4 (requerido por Serenity CucumberRunner)
    testImplementation("junit:junit:4.13.2")

    // Selenium WebDriver (incluido transitivamente por serenity-core, pero explícito)
    testImplementation("org.seleniumhq.selenium:selenium-java:4.18.1")

    // WebDriverManager para gestionar el driver automáticamente
    testImplementation("io.github.bonigarcia:webdrivermanager:5.8.0")

    // AssertJ para aserciones fluidas
    testImplementation("org.assertj:assertj-core:3.25.3")

    // SLF4J para evitar warnings en consola
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.12")
}

// Configuración de Java
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

// Configuración de la tarea de test
tasks.test {
    useJUnit()

    // Pasar todas las system properties al proceso de test (incluye serenity.* y demo.delay)
    systemProperties(System.getProperties().toMap() as Map<String, Any>)

    // Directorio de salida de reportes Serenity
    outputs.dir("target/site/serenity")
}

// ============================================================
// Tarea personalizada: generar reporte Serenity HTML completo
// ============================================================
tasks.register<JavaExec>("aggregate") {
    group = "Serenity BDD"
    description = "Genera el reporte HTML de Serenity BDD con los resultados de los tests"

    mainClass.set("net.serenitybdd.cli.Serenity")
    classpath = configurations.testRuntimeClasspath.get()
    args = listOf(
        "--project", rootProject.name,
        "--source", "target/site/serenity"
    )
}

// Ejecutar la generación de reporte automáticamente tras los tests
tasks.named("test") {
    finalizedBy("aggregate")
}