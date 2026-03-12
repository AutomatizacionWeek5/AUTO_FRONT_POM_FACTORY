@gestion
Feature: Gestión del Sistema de Tickets

  Como usuario autenticado del Sistema de Tickets,
  quiero poder acceder a asignaciones, notificaciones y cerrar sesión
  para administrar correctamente la plataforma.

  @asignaciones @admin
  Scenario: Administrador consulta la lista de asignaciones
    Given el administrador está autenticado en el sistema
    When consulta las asignaciones
    Then puede ver la lista de asignaciones

  @notificaciones
  Scenario: Usuario autenticado consulta sus notificaciones
    Given el usuario está autenticado en el sistema
    When consulta sus notificaciones
    Then puede ver el panel de notificaciones

  @logout
  Scenario: Cierre de sesión exitoso
    Given el usuario está autenticado en el sistema
    When el usuario cierra sesión
    Then la sesión queda cerrada y es redirigido al inicio de sesión
