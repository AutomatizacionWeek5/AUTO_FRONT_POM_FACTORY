@validacion
Feature: Validaciones de autenticación del Sistema de Tickets

  Como usuario del Sistema de Tickets,
  quiero recibir mensajes de error claros al ingresar datos inválidos
  para poder corregir mis credenciales.

  @registro @edge-case
  Scenario Outline: Registro rechazado cuando las contraseñas no coinciden
    When el usuario intenta registrarse con usuario "<username>", email "<email>", contraseña "<password>" y confirmación "<confirmPassword>"
    Then el sistema rechaza el registro informando que las contraseñas no coinciden

    Examples:
      | username          | email                   | password      | confirmPassword   |
      | testuser_mismatch | mismatch@test.sofka.com | TestPass@2026 | DiferentPass@2026 |

  @login @edge-case
  Scenario Outline: Login rechazado con credenciales incorrectas
    When el usuario intenta iniciar sesión con email "<email>" y contraseña "<password>"
    Then el sistema rechaza el acceso con un mensaje de error

    Examples:
      | email            | password            |
      | admin@sofkau.com | ClaveIncorrecta123! |
