@validacion
Feature: Validaciones de autenticación del Sistema de Tickets

  Como usuario del Sistema de Tickets,
  quiero recibir mensajes de error claros al ingresar datos inválidos
  para poder corregir mis credenciales.

  Background:
    Given el usuario navega a la aplicación

  @registro @edge-case
  Scenario Outline: Registro rechazado cuando las contraseñas no coinciden
    When el usuario navega a la página de registro
    And introduce el nombre de usuario "<username>"
    And introduce el email "<email>"
    And introduce la contraseña "<password>"
    And introduce la confirmación de contraseña "<confirmPassword>"
    And hace click en "Crear cuenta"
    Then debería ver el error "Las contraseñas no coinciden"

    Examples:
      | username          | email                   | password      | confirmPassword   |
      | testuser_mismatch | mismatch@test.sofka.com | TestPass@2026 | DiferentPass@2026 |

  @login @edge-case
  Scenario Outline: Login rechazado con credenciales incorrectas
    When el usuario introduce el email "<email>"
    And el usuario introduce la contraseña "<password>"
    And el usuario hace click en "Iniciar sesión"
    Then debería ver un mensaje de error de autenticación

    Examples:
      | email               | password            |
      | admin@sofkau.com    | ClaveIncorrecta123! |
