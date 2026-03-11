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

```powershell
./gradlew test
```

---

### Ejecutar por tag (escenario individual)

> **Nota PowerShell:** el carácter `@` es un operador especial en PowerShell. El argumento completo `-D` debe ir entre comillas dobles externas. Siempre usar `"-Dpropiedad=valor"`.

| Qué ejecuta | PowerShell (Windows) | Bash / Git Bash / Linux / Mac |
|---|---|---|
| Flujo E2E completo (smoke) | `./gradlew test "-Dcucumber.filter.tags=@smoke"` | `./gradlew test -Dcucumber.filter.tags="@smoke"` |
| Flujo E2E completo | `./gradlew test "-Dcucumber.filter.tags=@flujo-e2e"` | `./gradlew test -Dcucumber.filter.tags="@flujo-e2e"` |
| Registro de usuario | `./gradlew test "-Dcucumber.filter.tags=@registro"` | `./gradlew test -Dcucumber.filter.tags="@registro"` |
| Solo registro exitoso | `./gradlew test "-Dcucumber.filter.tags=@registro and @happy-path"` | `./gradlew test -Dcucumber.filter.tags="@registro and @happy-path"` |
| Solo validaciones de registro | `./gradlew test "-Dcucumber.filter.tags=@registro and @validacion"` | `./gradlew test -Dcucumber.filter.tags="@registro and @validacion"` |
| Login | `./gradlew test "-Dcucumber.filter.tags=@login"` | `./gradlew test -Dcucumber.filter.tags="@login"` |
| Solo login exitoso | `./gradlew test "-Dcucumber.filter.tags=@login and @happy-path"` | `./gradlew test -Dcucumber.filter.tags="@login and @happy-path"` |
| Solo validaciones de login | `./gradlew test "-Dcucumber.filter.tags=@login and @validacion"` | `./gradlew test -Dcucumber.filter.tags="@login and @validacion"` |
| Asignaciones (admin) | `./gradlew test "-Dcucumber.filter.tags=@asignaciones"` | `./gradlew test -Dcucumber.filter.tags="@asignaciones"` |
| Notificaciones | `./gradlew test "-Dcucumber.filter.tags=@notificaciones"` | `./gradlew test -Dcucumber.filter.tags="@notificaciones"` |
| Logout | `./gradlew test "-Dcucumber.filter.tags=@logout"` | `./gradlew test -Dcucumber.filter.tags="@logout"` |

---

### Combinar y excluir tags

```powershell
# Ejecutar smoke Y login
./gradlew test "-Dcucumber.filter.tags=@smoke or @login"

# Ejecutar todo excepto validaciones
./gradlew test "-Dcucumber.filter.tags=not @validacion"

# Ejecutar happy-path de todos los módulos
./gradlew test "-Dcucumber.filter.tags=@happy-path"
```

---

### Opciones adicionales

```powershell
# Sin retraso de demo (ejecución más rápida)
./gradlew test "-Ddemo.delay=0"

# Cambiar la URL base de la aplicación
./gradlew test "-Dwebdriver.base.url=http://mi-servidor:3000"

# Combinar: smoke sin demo delay
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

| Tag | Escenario |
|-----|-----------|
| `@registro @happy-path` | Registro exitoso de usuario nuevo |
| `@registro @validacion` | Registro con contraseñas que no coinciden |
| `@login @happy-path` | Login exitoso con credenciales válidas |
| `@login @validacion` | Login con credenciales incorrectas |
| `@flujo-e2e @smoke` | **Flujo E2E completo** (login → crear ticket → verificar detalle) |
| `@asignaciones @admin` | Acceso a la vista de asignaciones (administrador) |
| `@notificaciones` | Acceso al panel de notificaciones (usuario autenticado) |
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
