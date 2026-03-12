@validacion
Feature: Validaciones de autenticación del Sistema de Tickets

  Como usuario del Sistema de Tickets,
  quiero recibir mensajes de error claros al ingresar datos inválidos
  para poder corregir mis credenciales.

  @registro @edge-case
  Scenario Outline: Registro rechazado cuando el usuario ya existe en el sistema
    When el usuario intenta registrarse con usuario "<username>", email "<email>", contraseña "<password>" y confirmación "<confirmPassword>"
    Then el sistema rechaza el registro informando que el usuario ya existe

    Examples:
      | username | email            | password           | confirmPassword    |
      | admin    | admin@sofkau.com | Admin@SofkaU_2026! | Admin@SofkaU_2026! |

  @login @edge-case
  Scenario Outline: Login rechazado con credenciales incorrectas
    When el usuario intenta iniciar sesión con email "<email>" y contraseña "<password>"
    Then el sistema rechaza el acceso con un mensaje de error

    Examples:
      | email            | password            |
      | admin@sofkau.com | ClaveIncorrecta123! |
