package com.empresa.talenthub.colecciones;

import com.empresa.talenthub.modelo.Empleado;

import java.util.ArrayList;

/**
 * TASK 4 - Filtrado avanzado con removeIf y tipado con var.
 *
 * EVOLUCION DEL TIPADO:
 *
 *   Java 8 (explicito):
 *     for (Iterator<Empleado> it = lista.iterator(); it.hasNext(); ) {
 *         Empleado e = it.next();
 *         ...
 *     }
 *
 *   Java 11+ (con var):
 *     for (var e : lista) { ... }
 *
 * El uso de var reduce ruido visual en el tipo del lado izquierdo cuando
 * el tipo ya es obvio del lado derecho. No es "tipado dinamico": sigue
 * siendo tipado estatico, solo que inferido por el compilador.
 *
 * FILTRADO CON removeIf:
 * removeIf recibe un Predicate y elimina en sitio todos los elementos que lo
 * cumplan. Reemplaza el patron clasico de Iterator + if + it.remove(), que
 * era verboso y propenso a ConcurrentModificationException si se hacia mal.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public class ReporteTalento {

    /**
     * Elimina del ArrayList los empleados con salario base por debajo del minimo.
     * @return cuantos fueron eliminados.
     */
    public static int filtrarPorSalarioMinimo(ArrayList<Empleado> empleados, double minimo) {
        var tamanioAntes = empleados.size();
        empleados.removeIf(e -> e.getSalarioBase() < minimo);
        return tamanioAntes - empleados.size();
    }

    /** Imprime el reporte final: total de empleados y promedio de salarios. */
    public static void imprimirReporte(ArrayList<Empleado> empleados) {
        System.out.println("\n======== REPORTE FINAL ========");
        System.out.println("Total de empleados: " + empleados.size());

        if (empleados.isEmpty()) {
            System.out.println("Promedio de salarios: N/A (sin datos)");
            System.out.println("===============================");
            return;
        }

        double suma = 0.0;
        // 'var' infiere Empleado a partir del tipo generico de la coleccion.
        for (var e : empleados) {
            suma += e.getSalarioBase();
        }
        var promedio = suma / empleados.size();
        System.out.printf("Promedio de salarios: %.2f%n", promedio);
        System.out.println("===============================");
    }
}