@gestion
Feature: Gestión del Sistema de Tickets

  Como usuario autenticado del Sistema de Tickets,
  quiero poder acceder a asignaciones, notificaciones y cerrar sesión
  para administrar correctamente la plataforma.

  Background:
    Given el usuario navega a la aplicación

  @asignaciones @admin
  Scenario: Administrador accede a la vista de asignaciones
    Given el usuario está autenticado con email "admin@sofkau.com" y contraseña "Admin@SofkaU_2026!"
    When el administrador navega a "Asignaciones"
    Then la página de asignaciones debería estar cargada

  @notificaciones
  Scenario: Usuario autenticado accede al panel de notificaciones
    Given el usuario está autenticado con email "admin@sofkau.com" y contraseña "Admin@SofkaU_2026!"
    When el usuario navega a "Notificaciones"
    Then la página de notificaciones debería estar cargada

  @logout
  Scenario: Cierre de sesión exitoso
    Given el usuario está autenticado con email "admin@sofkau.com" y contraseña "Admin@SofkaU_2026!"
    When el usuario cierra sesión
    Then debería ser redirigido a la página de login
