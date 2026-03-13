import java.net.URLClassLoader

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
    testImplementation("net.serenity-bdd:serenity-core:$serenityCoreVersion")
    testImplementation("net.serenity-bdd:serenity-cucumber:$serenityCoreVersion")
    testImplementation("net.serenity-bdd:serenity-screenplay:$serenityCoreVersion")
    testImplementation("net.serenity-bdd:serenity-screenplay-webdriver:$serenityCoreVersion")

    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")

    testImplementation("junit:junit:4.13.2")

    testImplementation("org.seleniumhq.selenium:selenium-java:4.18.1")

    testImplementation("io.github.bonigarcia:webdrivermanager:5.8.0")

    testImplementation("org.assertj:assertj-core:3.25.3")

    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.12")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.test {
    useJUnit()

    systemProperties(System.getProperties().toMap() as Map<String, Any>)
    systemProperty("serenity.outputDirectory", layout.projectDirectory.dir("target/site/serenity").asFile.absolutePath)
    systemProperty("serenity.project.name", rootProject.name)

    outputs.dir("target/site/serenity")
    finalizedBy("aggregate")
}

tasks.register("aggregate") {
    group = "Serenity BDD"
    description = "Genera el reporte HTML agregado de Serenity"
    mustRunAfter("test")
    doLast {
        val outputDir = layout.projectDirectory.dir("target/site/serenity").asFile
        val urls = configurations.getByName("testRuntimeClasspath").map { it.toURI().toURL() }.toTypedArray()
        val cl = URLClassLoader(urls, Thread.currentThread().contextClassLoader)
        val rc = cl.loadClass("net.thucydides.core.reports.html.HtmlAggregateStoryReporter")
        val reporter = rc.getDeclaredConstructor(String::class.java).newInstance(rootProject.name)
        rc.getMethod("setSourceDirectory", File::class.java).invoke(reporter, outputDir)
        rc.getMethod("setOutputDirectory", File::class.java).invoke(reporter, outputDir)
        rc.getMethod("generateReportsForTestResultsFrom", File::class.java).invoke(reporter, outputDir)
        cl.close()
    }
}

