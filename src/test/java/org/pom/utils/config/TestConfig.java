package org.pom.utils.config;

public class TestConfig {

    
    public static final String BASE_URL =
            System.getProperty("webdriver.base.url", "http://localhost:3000");

    
    public static final int DEMO_DELAY =
            Integer.parseInt(System.getProperty("demo.delay", "1"));

    
    public static final String ADMIN_EMAIL    = "admin@sofkau.com";
    public static final String ADMIN_PASSWORD = "Admin@SofkaU_2026!";

    private TestConfig() {

    }
}
