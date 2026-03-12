# AUTO_FRONT_POM_FACTORY — Automatización E2E con Serenity BDD

Proyecto de automatización de pruebas **End-to-End (E2E)** para el **Sistema de Tickets**, implementado con **Java + Serenity BDD + Cucumber + Gradle** siguiendo el patrón **Page Object Model (POM) con Page Factory** y el principio de **Single Responsibility (SRP)** en step definitions y page objects.

---

## Estructura del Proyecto

```
src/
└── test/
    ├── java/
    │   └── org/pom/
    │       ├── context/                      # Contexto compartido entre steps
    │       │   └── TestContext.java          # ThreadLocal: almacena datos del escenario
    │       ├── pages/                        # Page Objects (POM + Page Factory)
    │       │   ├── auth/
    │       │   │   ├── LoginPage.java
    │       │   │   └── RegisterPage.java
    │       │   ├── tickets/
    │       │   │   ├── CreateTicketPage.java
    │       │   │   ├── TicketListPage.java
    │       │   │   ├── TicketDetailPage.java
    │       │   │   └── AssignmentListPage.java
    │       │   └── shared/
    │       │       ├── NavBarPage.java
    │       │       └── NotificationListPage.java
    │       ├── stepdefs/                     # Step Definitions por responsabilidad
    │       │   ├── Hooks.java                # @Before / @After (reset de contexto)
    │       │   ├── UserSetupSteps.java       # Precondiciones: crear/verificar usuario
    │       │   ├── LoginSteps.java           # Pasos de autenticación
    │       │   ├── RegisterSteps.java        # Pasos de registro
    │       │   ├── CreateTicketSteps.java    # Pasos de creación de ticket
    │       │   ├── TicketListSteps.java      # Pasos de lista y detalle de tickets
    │       │   └── NavigationSteps.java      # Pasos de navegación general
    │       ├── runner/                       # Test Runner
    │       │   └── TicketSystemTestRunner.java
    │       └── utils/                        # Utilidades
    │           ├── api/ApiHelper.java        # Llamadas HTTP para setup de datos
    │           ├── config/TestConfig.java    # URLs y configuración centralizada
    │           ├── driver/DriverFactory.java # Fábrica de WebDriver
    │           └── wait/WaitUtils.java       # Esperas explícitas
    └── resources/
        ├── features/
        │   ├── registro.feature          # HU-1: Registro (Scenario Outline)
        │   ├── validaciones.feature      # Edge cases: registro y login inválidos (Scenario Outline)
        │   ├── flujo_e2e.feature         # HU-5: Flujo completo E2E (Scenario Outline)
        │   └── gestion.feature           # HU-6/7: Asignaciones, notificaciones y logout
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

```powershell
./gradlew test
```

---

> **Nota PowerShell:** el carácter `@` es especial en PowerShell. El argumento `-D` siempre debe ir entre comillas dobles externas: `"-Dpropiedad=valor"`.

---

### 1. Solo registro (happy path)

Ejecuta el `Scenario Outline` de `registro.feature` — crea usuarios nuevos vía UI (2 iteraciones).

```powershell
# PowerShell
./gradlew test "-Dcucumber.filter.tags=@registro and @happy-path"
```
```bash
# Bash / Git Bash / Linux / Mac
./gradlew test -Dcucumber.filter.tags="@registro and @happy-path"
```

---

### 2. Solo validaciones (edge cases)

Ejecuta los dos `Scenario Outline` de `validaciones.feature`: contraseñas no coinciden + credenciales incorrectas (2 iteraciones cada uno).

```powershell
# Todas las validaciones juntas
./gradlew test "-Dcucumber.filter.tags=@edge-case"

# Solo validaciones de registro
./gradlew test "-Dcucumber.filter.tags=@registro and @edge-case"

# Solo validaciones de login
./gradlew test "-Dcucumber.filter.tags=@login and @edge-case"
```
```bash
./gradlew test -Dcucumber.filter.tags="@edge-case"
./gradlew test -Dcucumber.filter.tags="@registro and @edge-case"
./gradlew test -Dcucumber.filter.tags="@login and @edge-case"
```

---

### 3. Solo flujo E2E

Ejecuta el `Scenario Outline` de `flujo_e2e.feature`: login → crear ticket → verificar detalle (2 iteraciones).

```powershell
./gradlew test "-Dcucumber.filter.tags=@flujo-e2e"

# Alias smoke (mismo escenario)
./gradlew test "-Dcucumber.filter.tags=@smoke"
```
```bash
./gradlew test -Dcucumber.filter.tags="@flujo-e2e"
./gradlew test -Dcucumber.filter.tags="@smoke"
```

---

### 4. Solo gestión (asignaciones + notificaciones + logout juntos)

Ejecuta los tres escenarios de `gestion.feature` en una sola pasada.

```powershell
./gradlew test "-Dcucumber.filter.tags=@gestion"
```
```bash
./gradlew test -Dcucumber.filter.tags="@gestion"
```

Si necesitas ejecutar cada uno por separado:

```powershell
# Solo asignaciones
./gradlew test "-Dcucumber.filter.tags=@asignaciones"

# Solo notificaciones
./gradlew test "-Dcucumber.filter.tags=@notificaciones"

# Solo logout
./gradlew test "-Dcucumber.filter.tags=@logout"
```

---

### 5. Todo excepto edge cases

Ejecuta registro, flujo E2E y gestión — omite los `Scenario Outline` de validaciones.

```powershell
./gradlew test "-Dcucumber.filter.tags=not @edge-case"
```
```bash
./gradlew test -Dcucumber.filter.tags="not @edge-case"
```

---

### 6. Suite completa

```powershell
./gradlew test
```

---

### Opciones adicionales

```powershell
# Sin retraso entre pasos (ejecución más rápida)
./gradlew test "-Ddemo.delay=0"

# Cambiar la URL base de la aplicación
./gradlew test "-Dwebdriver.base.url=http://mi-servidor:3000"

# Combinar opciones: flujo E2E sin delay
./gradlew test "-Dcucumber.filter.tags=@smoke" "-Ddemo.delay=0"
```

---

## Reporte Serenity BDD

### Dónde se genera

Después de ejecutar los tests, el reporte HTML se genera automáticamente en:

```
target/site/serenity/index.html
```

Abrir ese archivo en el navegador para visualizar:
- Resultados por escenario y tag
- Capturas de pantalla de cada paso
- Historial de pasos Gherkin ejecutados
- Estadísticas de pasos pasados / fallidos / pendientes

### Regenerar el reporte sin volver a correr los tests

Si ya se ejecutaron los tests y solo quieres regenerar el HTML a partir de los resultados JSON existentes:

```powershell
./gradlew aggregate
```

El reporte se actualizará en `target/site/serenity/index.html` sin volver a lanzar el navegador.

### Flujo completo recomendado

```powershell
# 1. Ejecutar los tests
./gradlew test

# 2. (Opcional) Regenerar el reporte si fue necesario limpiar
./gradlew aggregate

# 3. Abrir el reporte en el navegador (Windows)
start target/site/serenity/index.html
```

---

## Archivos Ignorados por Git

El `.gitignore` excluye los siguientes archivos y carpetas para evitar subir artefactos innecesarios:

| Patrón | Descripción |
|--------|-------------|
| `build/` | Artefactos de compilación y reportes generados |
| `target/` | Directorio de salida alternativo (Maven/Serenity) |
| `.gradle/` | Caché local de Gradle |
| `test_*.txt` | Logs de ejecución de tests (diagnóstico local) |
| `.idea/` | Configuración del IDE IntelliJ IDEA |
| `bin/` | Binarios compilados del IDE |

> **Nota:** El wrapper de Gradle (`gradle/wrapper/gradle-wrapper.jar`) sí está incluido en Git para garantizar que cualquier colaborador pueda ejecutar el proyecto sin instalar Gradle manualmente.

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

| Feature | Tag(s) | Tipo | Iteraciones |
|---------|--------|------|-------------|
| `registro.feature` | `@registro @happy-path` | Scenario Outline | 2 (anyi398, maria398) |
| `validaciones.feature` | `@registro @edge-case` | Scenario Outline | 2 (passwords no coinciden) |
| `validaciones.feature` | `@login @edge-case` | Scenario Outline | 2 (credenciales inválidas) |
| `flujo_e2e.feature` | `@flujo-e2e @smoke` | Scenario Outline | 2 (e2eflow2026, e2eflow2027) |
| `gestion.feature` | `@asignaciones @admin` | Scenario | 1 |
| `gestion.feature` | `@notificaciones` | Scenario | 1 |
| `gestion.feature` | `@logout` | Scenario | 1 |

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
