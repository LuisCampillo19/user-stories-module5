package com.empresa.talenthub.view;

import com.empresa.talenthub.model.entity.Empleado;

import java.util.List;
import java.util.Scanner;

/**
 * Capa de Vista del patrón MVC.
 *
 * HU5 — TASK 3: Separación de responsabilidades
 *
 * REGLA MVC: toda interacción con el usuario (Scanner, System.out) ocurre
 * ÚNICAMENTE en esta capa. El Controlador NO lee del teclado ni imprime
 * directamente — solo coordina entre Vista y Modelo.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public class EmpleadoView {

    private final Scanner scanner;

    public EmpleadoView() {
        this.scanner = new Scanner(System.in);
    }

    // ==================== Menú ====================

    public int mostrarMenu() {
        System.out.println("""
                
                ╔═══════════════════════════════════════╗
                ║   CORPORATE TALENT HUB — CRUD JDBC   ║
                ╠═══════════════════════════════════════╣
                ║  1. Registrar empleado                ║
                ║  2. Listar empleados                  ║
                ║  3. Buscar empleado por ID             ║
                ║  4. Actualizar empleado               ║
                ║  5. Eliminar empleado                 ║
                ║  6. Reporte consolidado (Record)      ║
                ║  7. Probar conexión a BD              ║
                ║  0. Salir                             ║
                ╚═══════════════════════════════════════╝
                """);
        System.out.print("  Seleccione una opción: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // ==================== Formularios de entrada ====================

    /**
     * Solicita los datos de un nuevo empleado al usuario.
     * El Scanner vive aquí — el Controller nunca toca System.in.
     */
    public Empleado pedirDatosEmpleado() {
        System.out.println("\n  ===== REGISTRAR EMPLEADO =====");

        System.out.print("  Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("  Documento: ");
        long documento = Long.parseLong(scanner.nextLine().trim());

        System.out.print("  Departamento: ");
        String departamento = scanner.nextLine().trim();

        System.out.print("  Salario base: ");
        double salario = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("  Género (M/F): ");
        char genero = scanner.nextLine().trim().toUpperCase().charAt(0);

        System.out.print("  ¿Está activo? (true/false): ");
        boolean activo = Boolean.parseBoolean(scanner.nextLine().trim());

        System.out.print("  Fecha de ingreso (yyyy-MM-dd): ");
        String fecha = scanner.nextLine().trim();

        return new Empleado(nombre, documento, departamento, salario, genero, activo, fecha);
    }

    /**
     * Pide un ID al usuario. Retornado como int.
     */
    public int pedirId(String accion) {
        System.out.printf("  Ingrese el ID del empleado a %s: ", accion);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    /**
     * Solicita los datos actualizados, mostrando los valores actuales.
     */
    public Empleado pedirDatosActualizacion(Empleado actual) {
        System.out.println("\n  ===== ACTUALIZAR EMPLEADO (dejar vacío para conservar) =====");

        System.out.printf("  Nombre actual [%s]: ", actual.getNombre());
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) nombre = actual.getNombre();

        System.out.printf("  Documento actual [%d]: ", actual.getDocumento());
        String docStr = scanner.nextLine().trim();
        long documento = docStr.isEmpty() ? actual.getDocumento() : Long.parseLong(docStr);

        System.out.printf("  Departamento actual [%s]: ", actual.getDepartamento());
        String depto = scanner.nextLine().trim();
        if (depto.isEmpty()) depto = actual.getDepartamento();

        System.out.printf("  Salario actual [%.0f]: ", actual.getSalarioBase());
        String salStr = scanner.nextLine().trim();
        double salario = salStr.isEmpty() ? actual.getSalarioBase() : Double.parseDouble(salStr);

        System.out.printf("  Género actual [%c]: ", actual.getGenero());
        String genStr = scanner.nextLine().trim();
        char genero = genStr.isEmpty() ? actual.getGenero() : genStr.toUpperCase().charAt(0);

        System.out.printf("  Activo actual [%s]: ", actual.isEsActivo());
        String actStr = scanner.nextLine().trim();
        boolean activo = actStr.isEmpty() ? actual.isEsActivo() : Boolean.parseBoolean(actStr);

        System.out.printf("  Fecha ingreso actual [%s]: ", actual.getFechaIngreso());
        String fecha = scanner.nextLine().trim();
        if (fecha.isEmpty()) fecha = actual.getFechaIngreso();

        return new Empleado(actual.getId(), nombre, documento, depto, salario, genero, activo, fecha);
    }

    // ==================== Mensajes de salida ====================

    public void mostrarEmpleado(Empleado e) {
        String texto = """
                  ┌──────────────────────────────────────
                  │ ID:          %d
                  │ Nombre:      %s
                  │ Documento:   %d
                  │ Departamento: %s
                  │ Salario:     $%,.0f
                  │ Género:      %c
                  │ Activo:      %s
                  │ Fecha:       %s
                  └──────────────────────────────────────
                """.formatted(e.getId(), e.getNombre(), e.getDocumento(),
                e.getDepartamento(), e.getSalarioBase(), e.getGenero(),
                e.isEsActivo() ? "Sí" : "No", e.getFechaIngreso());
        System.out.println(texto);
    }

    public void mostrarListaEmpleados(List<Empleado> empleados) {
        if (empleados.isEmpty()) {
            System.out.println("  No hay empleados registrados.");
            return;
        }

        System.out.println("\n  ===== LISTA DE EMPLEADOS =====");
        System.out.printf("  %-4s %-20s %-15s %-18s %12s %-8s%n",
                "ID", "Nombre", "Documento", "Departamento", "Salario", "Activo");
        System.out.println("  " + "-".repeat(85));

        for (Empleado e : empleados) {
            System.out.printf("  %-4d %-20s %-15d %-18s %,12.0f %-8s%n",
                    e.getId(), e.getNombre(), e.getDocumento(),
                    e.getDepartamento(), e.getSalarioBase(),
                    e.isEsActivo() ? "Sí" : "No");
        }
        System.out.printf("%n  Total: %d empleado(s)%n", empleados.size());
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println("  " + mensaje);
    }

    public void mostrarError(String error) {
        System.out.println("  ❌ " + error);
    }

    public void cerrar() {
        scanner.close();
    }
}
