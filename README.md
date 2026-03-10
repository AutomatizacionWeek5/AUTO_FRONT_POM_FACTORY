# AUTO_FRONT_POM_FACTORY — Automatización E2E con Serenity BDD

Proyecto de automatización de pruebas **End-to-End (E2E)** para el **Sistema de Tickets**, implementado con **Java + Serenity BDD + Cucumber + Gradle** siguiendo el patrón **Page Object Model (POM) con Page Factory**.

---

## Estructura del Proyecto

```
src/
└── test/
    ├── java/
    │   └── org/pom/
    │       ├── pages/                        # Page Objects (POM + Page Factory)
    │       │   ├── LoginPage.java
    │       │   ├── RegisterPage.java
    │       │   ├── TicketListPage.java
    │       │   ├── CreateTicketPage.java
    │       │   ├── TicketDetailPage.java
    │       │   ├── AssignmentListPage.java
    │       │   └── NavBarPage.java
    │       ├── stepdefs/                     # Step Definitions (Cucumber)
    │       │   ├── Hooks.java
    │       │   └── TicketSystemStepDefs.java
    │       ├── runner/                       # Test Runner (JUnit + CucumberWithSerenity)
    │       │   └── TicketSystemTestRunner.java
    │       └── utils/                        # Utilidades
    │           ├── TestConfig.java           # Configuración centralizada
    │           ├── WaitUtils.java            # Esperas explícitas + demo_delay
    │           └── DriverFactory.java        # Fábrica de WebDriver
    └── resources/
        ├── features/
        │   └── sistema_tickets_e2e.feature   # Escenarios Gherkin (E2E)
        ├── serenity.conf                     # Configuración Serenity (HOCON)
        └── serenity.properties               # Propiedades Serenity
```

---

## Requisitos Previos

- **Java 17+**
- **Google Chrome** instalado (WebDriverManager descarga el driver automáticamente)
- **Sistema de Tickets corriendo**: levantar el stack completo con:

```bash
cd ../infra
docker-compose up -d
```

La aplicación estará disponible en `http://localhost:3000`.

---

## Ejecución de las Pruebas

### Ejecutar todos los escenarios

```bash
./gradlew test
```

### Ejecutar por tag

```bash
# Solo el flujo E2E completo (smoke test)
./gradlew test -Dcucumber.filter.tags="@smoke"

# Solo tests de login
./gradlew test -Dcucumber.filter.tags="@login"

# Solo tests de creación de tickets
./gradlew test -Dcucumber.filter.tags="@creacion-ticket"

# Tests del flujo admin
./gradlew test -Dcucumber.filter.tags="@admin"
```

### Ejecutar sin retraso de demo (más rápido)

```bash
./gradlew test -Ddemo.delay=0
```

### Cambiar la URL base

```bash
./gradlew test -Dwebdriver.base.url=http://mi-servidor:3000
```

---

## Ver el Reporte Serenity

Tras ejecutar los tests, el reporte HTML se genera en:

```
target/site/serenity/index.html
```

Abrir en el navegador para visualizar resultados detallados con capturas de pantalla.

---

## Configuración (serenity.properties)

| Propiedad | Valor por defecto | Descripción |
|-----------|-------------------|-------------|
| `webdriver.base.url` | `http://localhost:3000` | URL de la aplicación |
| `webdriver.driver` | `chrome` | Navegador (`chrome`, `firefox`, `edge`) |
| `demo.delay` | `1` | Segundos entre acciones (0 = sin retraso) |
| `serenity.take.screenshots` | `FOR_FAILURES` | Estrategia de capturas |

---

## Escenarios Cubiertos

| Tag | Escenario |
|-----|-----------|
| `@registro @happy-path` | Registro exitoso de usuario nuevo |
| `@registro @validacion` | Registro con contraseñas que no coinciden |
| `@login @happy-path` | Login exitoso con credenciales válidas |
| `@login @validacion` | Login con credenciales incorrectas |
| `@creacion-ticket @happy-path` | Creación de ticket exitosa |
| `@creacion-ticket @formulario` | Visualización del formulario de ticket |
| `@lista-tickets @happy-path` | Listado de tickets |
| `@detalle-ticket @happy-path` | Acceso al detalle de un ticket |
| `@flujo-e2e @smoke` | **Flujo E2E completo** (registro → ticket → detalle) |
| `@asignaciones @admin` | Acceso a la vista de asignaciones (admin) |
| `@logout` | Cierre de sesión exitoso |

---

## Credenciales del Administrador

El sistema tiene un usuario administrador creado por defecto:

- **Email**: `admin@sofkau.com`
- **Password**: `Admin@SofkaU_2026!`

---

## Stack Tecnológico

| Tecnología | Versión |
|-----------|---------|
| Java | 17 |
| Serenity BDD | 4.1.20 |
| Cucumber | 7.15.0 |
| Selenium | 4.18.1 |
| WebDriverManager | 5.8.0 |
| AssertJ | 3.25.3 |
| Gradle | 8.14 |
