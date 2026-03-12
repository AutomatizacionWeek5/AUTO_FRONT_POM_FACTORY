@registro
Feature: Registro de usuario en el Sistema de Tickets

  Como usuario anónimo del Sistema de Tickets,
  quiero poder crear una cuenta nueva en la plataforma
  para acceder a la gestión de mis solicitudes de soporte.

  Background:
    Given el usuario navega a la aplicación

  @happy-path
  Scenario Outline: Registro exitoso con datos válidos
    When el usuario navega a la página de registro
    And completa el formulario de registro con username "<username>", email "<email>" y contraseña "<password>"
    Then debería ser redirigido a la lista de tickets
    And la barra de navegación debería estar visible

    Examples:
      | username | email                    | password        |
      | kevin398 | userKevin@test.sofka.com | Kevin2Tess@2027 |
