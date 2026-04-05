package com.empresa.talenthub;

import com.empresa.talenthub.menu.MenuPrincipal;
import com.empresa.talenthub.scanner.CapturaEmpleado;
import com.empresa.talenthub.matriz.Matrizdesempenio;
import com.empresa.talenthub.excepciones.ManejoExcepciones;

import java.util.Scanner;

/**
 * CORPORATE TALENT HUB v2.0 - Semana 2
 * Control de flujo y evolucion de versiones
 *
 * Este programa integra las 4 tasks de la historia de usuario:
 *   TASK 1: Switch Legacy (Java 8) vs Switch Expression (Java 17/21)
 *   TASK 2: Scanner con var (Java 11+), do-while y validaciones con if/else
 *   TASK 3: Matriz de desempenio (double[][]), for anidados y casting double->int
 *   TASK 4: Manejo de excepciones (try-catch), diagnostico Java 17/21, operador ternario
 *
 * @author Luis Campillo
 * @version 2.0
 */
public class Main {

    public static void main(String[] args) {
        // Scanner compartido para toda la aplicacion
        var scanner = new Scanner(System.in);

        // Variable para almacenar calificaciones (se llena en opcion 3)
        double[][] calificaciones = null;

        // Variable de control para el bucle principal
        var sistemaActivo = true;

        System.out.println("========================================");
        System.out.println("  Bienvenido a Corporate Talent Hub     ");
        System.out.println("  Version 2.0 - Java 21                 ");
        System.out.println("========================================");

        // Demostrar manejo de excepciones al inicio (Task 4)
        ManejoExcepciones.demostrarManejoErrores();

        // Bucle principal do-while (Task 2: mantiene el sistema activo)
        do {
            // Mostrar menu (Task 1)
            MenuPrincipal.mostrarMenu();

            // Leer opcion de forma segura (Task 4: try-catch)
            var opcion = ManejoExcepciones.leerEnteroSeguro(scanner, "");

            if (opcion == -1) {
                System.out.println("Intente de nuevo con una opcion valida.\n");
                continue;
            }

            // Mostrar que opcion se selecciono usando switch legacy (Task 1)
            System.out.println(">> " + MenuPrincipal.obtenerOpcionMenuLegacy(opcion) + "\n");

            // Procesar opcion usando switch expression moderno (Task 1)
            switch (opcion) {
                case 1 -> {
                    // Task 2: Registro de empleado con Scanner, var, do-while, if/else
                    CapturaEmpleado.registrarEmpleado(scanner);
                }
                case 2 -> {
                    // Task 2: Consulta de empleados registrados
                    CapturaEmpleado.consultarEmpleados();

                    // Task 1: Mostrar categoria salarial de cada empleado
                    var total = CapturaEmpleado.getContadorEmpleados();
                    if (total > 0) {
                        var salarios = CapturaEmpleado.getSalariosEmpleados();
                        var nombres = CapturaEmpleado.getNombresEmpleados();
                        System.out.println("\n--- Categorias Salariales (Switch Expression Java 17/21) ---");
                        for (var i = 0; i < total; i++) {
                            System.out.println("  " + nombres[i] + ": "
                                    + MenuPrincipal.obtenerCategoriaSalarial(salarios[i]));
                        }
                    }
                }
                case 3 -> {
                    // Task 3: Matriz de desempenio, for anidados, casting
                    var total = CapturaEmpleado.getContadorEmpleados();
                    if (total == 0) {
                        System.out.println("Debe registrar al menos un empleado primero.");
                    } else {
                        calificaciones = Matrizdesempenio.capturarCalificaciones(
                                scanner,
                                CapturaEmpleado.getNombresEmpleados(),
                                total
                        );
                        Matrizdesempenio    .mostrarReporteDesempenio(
                                calificaciones,
                                CapturaEmpleado.getNombresEmpleados(),
                                total
                        );

                        // Task 4: Evaluar promocion con operador ternario
                        System.out.println("\n--- Evaluacion de Promocion (Operador Ternario) ---");
                        var umbral = 3.5;
                        for (var i = 0; i < total; i++) {
                            var suma = 0.0;
                            for (var j = 0; j < calificaciones[i].length; j++) {
                                suma += calificaciones[i][j];
                            }
                            var promedio = suma / calificaciones[i].length;
                            System.out.println("  " + CapturaEmpleado.getNombresEmpleados()[i] + ": "
                                    + ManejoExcepciones.evaluarPromocion(promedio, umbral));
                        }
                    }
                }
                case 4 -> {
                    // Task 1: Consultar categoria salarial de un salario ingresado
                    System.out.print("Ingrese un salario para consultar su categoria: $");
                    var salarioConsulta = ManejoExcepciones.leerDecimalSeguro(scanner, "");
                    if (salarioConsulta >= 0) {
                        System.out.println("Resultado: " + MenuPrincipal.obtenerCategoriaSalarial(salarioConsulta));
                    }
                }
                case 5 -> {
                    sistemaActivo = false;
                    System.out.println("Cerrando Corporate Talent Hub. Hasta pronto!");
                }
                default -> System.out.println("Opcion no valida. Seleccione entre 1 y 5.");
            }

            System.out.println();

        } while (sistemaActivo);

        scanner.close();
    }
}