package com.empresa.talenthub.demo;

import com.empresa.talenthub.logica.ValidadorRoles;
import com.empresa.talenthub.personas.ConsultorExterno;
import com.empresa.talenthub.personas.Desarrollador;
import com.empresa.talenthub.personas.Empleado;
import com.empresa.talenthub.personas.Gerente;
import com.empresa.talenthub.personas.Persona;
import com.empresa.talenthub.reportes.DesempenioReport;

/**
 * Demo integradora de la SEMANA 4 - POO Avanzada.
 *
 * Reune las 4 tasks en un solo flujo ejecutable para verificar que
 * la arquitectura funciona end-to-end:
 *
 *   - Task 1 -> Crear instancias de la jerarquia sealed (Persona y sus hijos).
 *   - Task 2 -> Emitir reportes de desempenio con el record DesempenioReport.
 *   - Task 3 -> Comparar los tres estilos de inspeccion de tipos.
 *   - Task 4 -> Ejercitar la interfaz Promocionable con su metodo default.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public class DemoPOOAvanzada {

    public static void ejecutar() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("  DEMO SEMANA 4 - POO AVANZADA            ");
        System.out.println("==========================================");

        // ---------- TASK 1: Jerarquia sealed ----------
        Desarrollador dev = new Desarrollador(1001, "Laura Gomez", 4_500_000, "Java 21");
        Gerente gerente = new Gerente(2001, "Carlos Rojas", 7_000_000, 15_000_000);
        ConsultorExterno consultor = new ConsultorExterno(
                3001, "Ana Torres", "Globant", 120_000);
        Empleado empleadoGenerico = new Empleado(4001, "Pedro Martinez", 3_000_000);

        Persona[] personas = { dev, gerente, consultor, empleadoGenerico };

        System.out.println("\n--- TASK 1: Jerarquia sealed (Persona permits ...) ---");
        for (Persona p : personas) {
            System.out.println("  " + p.identificarse());
        }

        // ---------- TASK 3: Pattern Matching (tres estilos) ----------
        System.out.println("\n--- TASK 3a: instanceof + casting manual (LEGACY Java 8) ---");
        for (Persona p : personas) {
            System.out.println("  " + ValidadorRoles.describirRolLegacy(p));
        }

        System.out.println("\n--- TASK 3b: Pattern Matching for instanceof (Java 17) ---");
        for (Persona p : personas) {
            System.out.println("  " + ValidadorRoles.describirRolModerno(p));
        }

        System.out.println("\n--- TASK 3c: Switch Pattern Matching (Java 21) ---");
        for (Persona p : personas) {
            System.out.println("  " + ValidadorRoles.describirRolSwitchPattern(p));
        }

        // ---------- TASK 2: Records inmutables ----------
        System.out.println("\n--- TASK 2: Records inmutables (DesempenioReport) ---");
        DesempenioReport r1 = DesempenioReport.generarAutomatico(dev.getId(), 4.7);
        DesempenioReport r2 = DesempenioReport.generarAutomatico(gerente.getId(), 3.8);
        DesempenioReport r3 = DesempenioReport.generarAutomatico(empleadoGenerico.getId(), 2.1);
        System.out.println("  " + r1);
        System.out.println("  " + r2);
        System.out.println("  " + r3);
        System.out.println("  Acceso directo al promedio de r1: " + r1.promedio());

        // Demostracion de la validacion fail-fast
        try {
            new DesempenioReport(9999, 7.5, "Rango invalido");
        } catch (IllegalArgumentException ex) {
            System.out.println("  [OK] El record rechaza datos invalidos: " + ex.getMessage());
        }

        // ---------- TASK 4: Interfaz Promocionable con default method ----------
        System.out.println("\n--- TASK 4: Promocionable con default method ---");
        // El metodo ascender() es DEFAULT: no lo implementan Desarrollador
        // ni Gerente, lo heredan de la interfaz. Solo implementan el metodo
        // abstracto calcularBonoAscenso().
        dev.ascender(dev);
        gerente.ascender(gerente);

        System.out.println("\n==========================================");
        System.out.println("  Demo completada                         ");
        System.out.println("==========================================");
    }

    /** Permite ejecutar la demo directamente si se quiere correr aislada. */
    public static void main(String[] args) {
        ejecutar();
    }
}