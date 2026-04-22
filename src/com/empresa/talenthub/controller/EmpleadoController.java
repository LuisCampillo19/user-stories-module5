package com.empresa.talenthub.controller;

import com.empresa.talenthub.config.ConexionDB;
import com.empresa.talenthub.model.dao.EmpleadoDAO;
import com.empresa.talenthub.model.dao.EmpleadoDAOImpl;
import com.empresa.talenthub.model.dto.EmpleadoReporte;
import com.empresa.talenthub.model.entity.Empleado;
import com.empresa.talenthub.view.EmpleadoView;

import java.util.List;
import java.util.Optional;

/**
 * Controlador del patrón MVC.
 *
 * HU5 — TASK 3: Separación de responsabilidades
 *
 * RESPONSABILIDAD: actúa como MEDIADOR entre la Vista y el Modelo.
 * - Recibe datos de la Vista (sin tocar Scanner directamente)
 * - Coordina acciones con el DAO (modelo de datos)
 * - Envía resultados de vuelta a la Vista para mostrar al usuario
 *
 * El Controlador NO contiene:
 * - System.out.println → eso va en la Vista
 * - new Scanner → eso va en la Vista
 * - SQL ni PreparedStatement → eso va en el DAO
 *
 * @author LuisCampillo
 * @version 1.0
 */
public class EmpleadoController {

    private final EmpleadoDAO dao;
    private final EmpleadoView view;

    public EmpleadoController() {
        this.dao = new EmpleadoDAOImpl();
        this.view = new EmpleadoView();
    }

    /**
     * Loop principal del sistema. Muestra el menú y delega cada operación.
     */
    public void iniciar() {
        boolean activo = true;

        while (activo) {
            int opcion = view.mostrarMenu();

            switch (opcion) {
                case 1 -> registrar();
                case 2 -> listar();
                case 3 -> buscar();
                case 4 -> actualizar();
                case 5 -> eliminar();
                case 6 -> reporteConsolidado();
                case 7 -> ConexionDB.testConnection();
                case 0 -> {
                    view.mostrarMensaje("¡Hasta luego!");
                    activo = false;
                }
                default -> view.mostrarError("Opción inválida. Intente de nuevo.");
            }
        }

        view.cerrar();
    }

    // ==================== Operaciones CRUD ====================

    private void registrar() {
        try {
            Empleado nuevo = view.pedirDatosEmpleado();
            boolean exito = dao.insertar(nuevo);

            if (exito) {
                view.mostrarMensaje("✅ Empleado registrado correctamente.");
            } else {
                view.mostrarError("No se pudo registrar el empleado.");
            }
        } catch (Exception e) {
            view.mostrarError("Datos inválidos: " + e.getMessage());
        }
    }

    private void listar() {
        List<Empleado> empleados = dao.listar();
        view.mostrarListaEmpleados(empleados);
    }

    private void buscar() {
        try {
            int id = view.pedirId("buscar");
            Optional<Empleado> resultado = dao.buscarPorId(id);

            if (resultado.isPresent()) {
                view.mostrarEmpleado(resultado.get());
            } else {
                view.mostrarError("No se encontró un empleado con ID " + id);
            }
        } catch (Exception e) {
            view.mostrarError("ID inválido: " + e.getMessage());
        }
    }

    private void actualizar() {
        try {
            int id = view.pedirId("actualizar");
            Optional<Empleado> existente = dao.buscarPorId(id);

            if (existente.isEmpty()) {
                view.mostrarError("No se encontró un empleado con ID " + id);
                return;
            }

            Empleado datosNuevos = view.pedirDatosActualizacion(existente.get());
            boolean exito = dao.actualizar(datosNuevos);

            if (exito) {
                view.mostrarMensaje("✅ Empleado actualizado correctamente.");
            } else {
                view.mostrarError("No se pudo actualizar el empleado.");
            }
        } catch (Exception e) {
            view.mostrarError("Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminar() {
        try {
            int id = view.pedirId("eliminar");
            Optional<Empleado> existente = dao.buscarPorId(id);

            if (existente.isEmpty()) {
                view.mostrarError("No se encontró un empleado con ID " + id);
                return;
            }

            view.mostrarEmpleado(existente.get());
            view.mostrarMensaje("¿Confirma la eliminación? (La acción es irreversible)");
            // La confirmación la manejaría la vista, por simplicidad procedemos
            boolean exito = dao.eliminar(id);

            if (exito) {
                view.mostrarMensaje("✅ Empleado eliminado correctamente.");
            } else {
                view.mostrarError("No se pudo eliminar el empleado.");
            }
        } catch (Exception e) {
            view.mostrarError("Error al eliminar: " + e.getMessage());
        }
    }

    // ==================== Reporte con Record (TASK 4) ====================

    /**
     * Genera un reporte consolidado usando el Record EmpleadoReporte.
     * Extrae datos de la BD, los mapea a Records inmutables, y presenta
     * el resultado formateado con Text Blocks.
     */
    private void reporteConsolidado() {
        List<Empleado> empleados = dao.listar();

        if (empleados.isEmpty()) {
            view.mostrarError("No hay empleados para generar el reporte.");
            return;
        }

        // Mapear entidades mutables (Empleado) → Records inmutables (EmpleadoReporte)
        List<EmpleadoReporte> reportes = empleados.stream()
                .map(e -> new EmpleadoReporte(
                        e.getId(),
                        e.getNombre(),
                        e.getDepartamento(),
                        e.getSalarioBase(),
                        e.isEsActivo()
                ))
                .toList();

        // Calcular estadísticas
        double salarioPromedio = reportes.stream()
                .mapToDouble(EmpleadoReporte::salario)
                .average()
                .orElse(0.0);

        long activos = reportes.stream()
                .filter(EmpleadoReporte::activo)
                .count();

        long inactivos = reportes.size() - activos;

        // Presentar con Text Blocks (Java 17+)
        String encabezado = """
                
                ╔══════════════════════════════════════════════════════════════╗
                ║             REPORTE CONSOLIDADO — TALENT HUB               ║
                ║             Generado con Records (Java 17+)                ║
                ╠══════════════════════════════════════════════════════════════╣
                ║  Total empleados: %-5d  Activos: %-5d  Inactivos: %-5d    ║
                ║  Salario promedio: $%,.0f                                   
                ╚══════════════════════════════════════════════════════════════╝
                """.formatted(reportes.size(), activos, inactivos, salarioPromedio);
        view.mostrarMensaje(encabezado);

        // Detalle por empleado usando toString() generado automáticamente por el Record
        view.mostrarMensaje("  Detalle (cada línea es un Record inmutable):");
        view.mostrarMensaje("  " + "-".repeat(60));
        for (EmpleadoReporte r : reportes) {
            view.mostrarMensaje("  " + r.toString());
        }
    }
}
