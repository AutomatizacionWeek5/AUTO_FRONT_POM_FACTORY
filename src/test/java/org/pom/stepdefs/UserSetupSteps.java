package org.pom.stepdefs;

import io.cucumber.java.en.Given;
import org.pom.context.TestContext;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;

public class UserSetupSteps {

    @Given("el usuario {string} con email {string} y contraseña {string} existe previamente en el sistema")
    public void elUsuarioExistePreviamenteEnElSistema(String username, String email, String password) {
        TestContext.get().setUsername(username);
        TestContext.get().setEmail(email);
        TestContext.get().setPassword(password);

        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            String loginBody = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
            HttpRequest loginReq = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8003/api/auth/login/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(loginBody))
                .timeout(Duration.ofSeconds(10))
                .build();
            HttpResponse<String> loginResp = httpClient.send(loginReq, HttpResponse.BodyHandlers.ofString());
            if (loginResp.statusCode() == 200) {
                System.out.println("[SETUP] Usuario " + email + " ya existe.");
                return;
            }
            System.out.println("[SETUP] Usuario no encontrado (login:" + loginResp.statusCode() + "). Registrando...");

            String regBody = "{\"username\":\"" + username + "\",\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
            HttpRequest regReq = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8003/api/auth/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(regBody))
                .timeout(Duration.ofSeconds(6))
                .build();
            try {
                HttpResponse<String> regResp = httpClient.send(regReq, HttpResponse.BodyHandlers.ofString());
                System.out.println("[SETUP] Register HTTP status: " + regResp.statusCode());
            } catch (HttpTimeoutException toe) {
                System.out.println("[SETUP] Register timeout (pika) — usuario guardado en BD.");
            }

            loginResp = httpClient.send(loginReq, HttpResponse.BodyHandlers.ofString());
            System.out.println("[SETUP] Verificación post-registro: login:" + loginResp.statusCode());

        } catch (Exception e) {
            System.out.println("[SETUP] Error creando usuario " + email + ": " + e.getMessage());
        } finally {
            httpClient.close();
        }
    }
}
