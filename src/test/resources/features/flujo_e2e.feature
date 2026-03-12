@flujo-e2e
Feature: Flujo E2E completo del Sistema de Tickets

  Como usuario del Sistema de Tickets,
  quiero poder iniciar sesión, crear tickets y visualizarlos
  para gestionar mis solicitudes de soporte de extremo a extremo.

  Background:
    Given el usuario navega a la aplicación

  @smoke
  Scenario Outline: Flujo E2E completo desde login hasta verificación de ticket
    Given el usuario "<username>" con email "<email>" y contraseña "<password>" existe previamente en el sistema
    And el usuario navega a la página de login
    When el usuario ingresa las credenciales almacenadas
    And hace click en el botón de login
    Then debería ser redirigido a la lista de tickets
    When el usuario navega a "Crear Ticket"
    And completa el formulario de ticket con título "<titulo>" y descripción "<descripcion>"
    And envía el formulario del ticket
    Then debería ser redirigido a la lista de tickets
    And el ticket "<titulo>" debería aparecer en la lista
    When el usuario hace click en el ticket "<titulo>"
    Then debería ver el detalle del ticket
    And el título del detalle debería contener "<titulo>"

    Examples:
      | username    | email                      | password      | titulo                  | descripcion                                            |
      | e2eflow2027 | e2eflow2027@test.sofka.com | TestPass@2026 | Ticket de demo    | tercer ticket creado en flujo automatizado sss           |
