package com.empresa.talenthub.matriz;

import java.util.Scanner;

/**
 * TASK 3 - Matrices de desempenio y casting
 *
 * Conceptos aplicados:
 * - Matriz (double[][]) para almacenar calificaciones de 3 trimestres por empleado.
 * - Bucles for anidados para recorrer la matriz y calcular promedios.
 * - Casting explicito de double a int para "Puntaje Simplificado".
 *
 * Sobre el Casting (double -> int):
 *   - El casting explicito (int) trunca la parte decimal, NO redondea.
 *   - Ejemplo: (int) 4.7 = 4, (int) 4.2 = 4 -> se pierde precision.
 *   - Esto es util para reportes simplificados donde no se necesitan decimales,
 *     pero debe documentarse para evitar confusiones en los datos.
 */
public class MatrizDesempenio {

    private static final int TRIMESTRES = 3;
    private static final double NOTA_MINIMA = 0.0;
    private static final double NOTA_MAXIMA = 5.0;

    /**
     * Captura las calificaciones trimestrales para todos los empleados registrados.
     *
     * @param scanner         instancia de Scanner compartida
     * @param nombres         arreglo de nombres de empleados
     * @param totalEmpleados  cantidad de empleados registrados
     * @return matriz double[empleados][trimestres] con las calificaciones
     */
    public static double[][] capturarCalificaciones(Scanner scanner, String[] nombres, int totalEmpleados) {
        if (totalEmpleados == 0) {
            System.out.println("No hay empleados registrados. Registre al menos uno primero.");
            return new double[0][0];
        }

        // Matriz: filas = empleados, columnas = trimestres
        var calificaciones = new double[totalEmpleados][TRIMESTRES];

        System.out.println("\n--- Registro de Calificaciones Trimestrales ---");

        // Bucle for externo: recorre empleados
        for (var i = 0; i < totalEmpleados; i++) {
            System.out.println("\nEmpleado: " + nombres[i]);

            // Bucle for interno: recorre trimestres
            for (var j = 0; j < TRIMESTRES; j++) {
                var notaValida = false;

                do {
                    System.out.print("  Trimestre " + (j + 1) + " (" + NOTA_MINIMA + " - " + NOTA_MAXIMA + "): ");
                    var entrada = scanner.nextLine();

                    try {
                        var nota = Double.parseDouble(entrada);

                        if (nota < NOTA_MINIMA || nota > NOTA_MAXIMA) {
                            System.out.println("  Error: La nota debe estar entre "
                                    + NOTA_MINIMA + " y " + NOTA_MAXIMA);
                        } else {
                            calificaciones[i][j] = nota;
                            notaValida = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("  Error: Ingrese un valor numerico valido.");
                    }
                } while (!notaValida);
            }
        }

        return calificaciones;
    }

    /**
     * Muestra el reporte de desempenio con promedios y puntaje simplificado.
     * Aplica casting explicito de double a int.
     *
     * @param calificaciones  matriz de calificaciones
     * @param nombres         arreglo de nombres de empleados
     * @param totalEmpleados  cantidad de empleados registrados
     */
    public static void mostrarReporteDesempenio(double[][] calificaciones, String[] nombres, int totalEmpleados) {
        if (totalEmpleados == 0 || calificaciones.length == 0) {
            System.out.println("No hay datos de desempenio para mostrar.");
            return;
        }

        System.out.println("\n=== REPORTE DE DESEMPENIO ===");
        System.out.printf("%-25s", "Empleado");
        for (var t = 1; t <= TRIMESTRES; t++) {
            System.out.printf("%-12s", "Trim " + t);
        }
        System.out.printf("%-12s %-12s%n", "Promedio", "Puntaje Int");
        System.out.println("-".repeat(85));

        // Recorrer la matriz con for anidados para calcular promedios
        for (var i = 0; i < totalEmpleados; i++) {
            var sumaNotas = 0.0;

            System.out.printf("%-25s", nombres[i]);

            for (var j = 0; j < TRIMESTRES; j++) {
                System.out.printf("%-12.1f", calificaciones[i][j]);
                sumaNotas += calificaciones[i][j];
            }

            // Calculo del promedio (double)
            var promedio = sumaNotas / TRIMESTRES;

            /*
             * CASTING EXPLICITO: double -> int
             *
             * El casting (int) TRUNCA la parte decimal, no redondea.
             * Ejemplo: promedio = 4.7 -> puntajeSimplificado = 4
             *          promedio = 3.2 -> puntajeSimplificado = 3
             *
             * PERDIDA DE PRECISION: Este puntaje es solo para reportes
             * simplificados. Para decisiones importantes (como promociones),
             * siempre usar el promedio con decimales (double).
             */
            int puntajeSimplificado = (int) promedio;

            System.out.printf("%-12.2f %-12d%n", promedio, puntajeSimplificado);
        }
    }
}