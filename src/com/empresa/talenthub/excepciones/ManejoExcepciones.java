package com.empresa.talenthub.excepciones;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * TASK 4 - Robustez y diagnostico de errores
 *
 * Evolucion del diagnostico de excepciones en Java:
 *
 * JAVA 8:
 *   - Los mensajes de NullPointerException eran genericos:
 *     "java.lang.NullPointerException" sin indicar QUE fue null.
 *   - El desarrollador debia revisar la linea y deducir cual referencia fallo.
 *   - Los stack traces no senialaban la variable o metodo especifico.
 *
 * JAVA 17/21 (Helpful NullPointerExceptions - JEP 358):
 *   - Los mensajes ahora son descriptivos:
 *     "Cannot invoke String.length() because 'nombre' is null"
 *   - Indican EXACTAMENTE que variable fue null y que operacion fallo.
 *   - Esto reduce drasticamente el tiempo de depuracion.
 *   - Habilitado por defecto desde Java 17.
 *
 * Adicionalmente, Java 17/21 mejoro:
 *   - Mensajes de ClassCastException mas detallados.
 *   - Mensajes de ArrayIndexOutOfBoundsException con el indice y tamano del arreglo.
 *   - Mejores stack traces en NullPointer encadenados (a.b().c() indica cual fue null).
 */
public class ManejoExcepciones {

    /**
     * Captura un numero entero de forma segura usando try-catch.
     * Envuelve la captura del Scanner para manejar InputMismatchException.
     *
     * @param scanner instancia de Scanner compartida
     * @param mensaje mensaje a mostrar al usuario
     * @return numero entero ingresado, o -1 si la entrada fue invalida
     */
    public static int leerEnteroSeguro(Scanner scanner, String mensaje) {
        System.out.print(mensaje);

        try {
            // Intentamos capturar un entero del Scanner
            var entrada = scanner.nextLine();
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            /*
             * En Java 8, el mensaje seria simplemente:
             *   "java.lang.NumberFormatException: For input string: "abc""
             *
             * En Java 17/21, el mensaje es igual para NumberFormatException,
             * pero los Helpful NullPointerExceptions mejoran el diagnostico
             * cuando el error involucra referencias nulas.
             */
            System.out.println("Error: Entrada no valida. Se esperaba un numero entero.");
            System.out.println("  Detalle tecnico: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Captura un numero decimal de forma segura.
     *
     * @param scanner instancia de Scanner compartida
     * @param mensaje mensaje a mostrar al usuario
     * @return numero decimal ingresado, o -1.0 si la entrada fue invalida
     */
    public static double leerDecimalSeguro(Scanner scanner, String mensaje) {
        System.out.print(mensaje);

        try {
            var entrada = scanner.nextLine();
            return Double.parseDouble(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Error: Entrada no valida. Se esperaba un numero decimal.");
            return -1.0;
        }
    }

    /**
     * Determina el estado de promocion del empleado usando Operador Ternario.
     *
     * Operador Ternario: condicion ? valorSiTrue : valorSiFalse
     * Es una forma concisa de escribir un if-else simple en una sola linea.
     *
     * Equivalente en if-else:
     *   if (promedio >= umbralPromocion) {
     *       estado = "ELEGIBLE para promocion";
     *   } else {
     *       estado = "NO elegible para promocion";
     *   }
     *
     * @param promedio          promedio de desempenio del empleado
     * @param umbralPromocion   puntaje minimo para ser elegible
     * @return mensaje con el estado de promocion
     */
    public static String evaluarPromocion(double promedio, double umbralPromocion) {
        // Operador ternario para decidir estado de promocion
        var estado = (promedio >= umbralPromocion)
                ? "ELEGIBLE para promocion"
                : "NO elegible para promocion";

        // Segundo ternario para nivel de urgencia
        var nivel = (promedio >= 4.5) ? "Prioridad ALTA"
                : (promedio >= 3.5) ? "Prioridad MEDIA"
                  : "Prioridad BAJA";

        return estado + " (" + nivel + " | Promedio: " + String.format("%.2f", promedio) + ")";
    }

    /**
     * Demuestra el manejo de excepciones con diferentes tipos de error.
     */
    public static void demostrarManejoErrores() {
        System.out.println("\n--- Demostracion de Manejo de Excepciones ---");

        // 1. ArrayIndexOutOfBoundsException
        try {
            var arreglo = new int[]{1, 2, 3};
            System.out.println("Accediendo a indice 5 de arreglo de tamano 3...");
            var valor = arreglo[5];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("  Capturada: " + e.getClass().getSimpleName());
            System.out.println("  Mensaje (Java 17/21 muestra indice y tamano): " + e.getMessage());
        }

        // 2. NullPointerException con Helpful Messages (Java 17/21)
        try {
            String texto = null;
            System.out.println("Invocando .length() sobre variable null...");
            var longitud = texto.length();
        } catch (NullPointerException e) {
            System.out.println("  Capturada: " + e.getClass().getSimpleName());
            /*
             * En Java 17/21 este mensaje dira algo como:
             * "Cannot invoke "String.length()" because "texto" is null"
             *
             * En Java 8 solo diria:
             * "null"
             *
             * Esta mejora (JEP 358) es una de las mas valoradas en las versiones modernas.
             */
            System.out.println("  Mensaje descriptivo (Java 17/21): " + e.getMessage());
        }

        System.out.println("--- Fin de demostracion ---\n");
    }
}