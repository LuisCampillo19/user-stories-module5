package com.empresa.talenthub.scanner;

import java.util.Scanner;

/**
 * TASK 2 - Captura dinamica y tipado inferido (Java 11+)
 *
 * Comparacion: Declaracion explicita (Java 8) vs Inferencia de tipos con 'var' (Java 11+)
 *
 * LEGACY (Java 8):
 *   String nombre = scanner.nextLine();
 *   double salario = scanner.nextDouble();
 *   int edad = scanner.nextInt();
 *   - Requiere declarar el tipo explicitamente siempre.
 *   - Mas verboso pero mas explicito en la lectura.
 *
 * MODERNO (Java 11+):
 *   var nombre = scanner.nextLine();
 *   var salario = scanner.nextDouble();
 *   var edad = scanner.nextInt();
 *   - El compilador infiere el tipo automaticamente.
 *   - Menos verboso, ideal para variables locales donde el tipo es obvio.
 *   - SOLO funciona en variables locales, no en atributos de clase ni parametros.
 */
public class CapturaEmpleado {

    // Constantes para validacion de rangos (tipos primitivos)
    private static final int EDAD_MINIMA = 18;
    private static final int EDAD_MAXIMA = 65;
    private static final double SALARIO_MINIMO = 1_160_000.0;  // SMMLV Colombia 2025
    private static final double SALARIO_MAXIMO = 50_000_000.0;
    private static final int MAX_EMPLEADOS = 10;

    // Arreglo para almacenar nombres de empleados registrados
    private static final String[] nombresEmpleados = new String[MAX_EMPLEADOS];
    private static final double[] salariosEmpleados = new double[MAX_EMPLEADOS];
    private static final int[] edadesEmpleados = new int[MAX_EMPLEADOS];
    private static int contadorEmpleados = 0;

    /**
     * Registra un empleado capturando datos por consola con Scanner.
     * Usa 'var' (Java 11+) para inferencia de tipos en variables locales.
     * Usa bucle do-while para reintentar en caso de datos invalidos.
     *
     * @param scanner instancia de Scanner compartida
     * @return true si el empleado fue registrado, false si se alcanzo el limite
     */
    public static boolean registrarEmpleado(Scanner scanner) {
        if (contadorEmpleados >= MAX_EMPLEADOS) {
            System.out.println("Error: Se alcanzo el limite maximo de " + MAX_EMPLEADOS + " empleados.");
            return false;
        }

        System.out.println("\n--- Registro de Empleado ---");

        // Captura de nombre con var (Java 11+)
        // En Java 8 seria: String nombre = "";
        System.out.print("Ingrese nombre completo: ");
        var nombre = scanner.nextLine();

        // Validacion con if/else: nombre no puede estar vacio
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacio.");
            return false;
        }

        // Captura de edad con validacion en do-while
        var edadValida = false;
        var edad = 0;

        do {
            System.out.print("Ingrese edad (" + EDAD_MINIMA + " - " + EDAD_MAXIMA + "): ");
            // En Java 8 seria: String entradaEdad = scanner.nextLine();
            var entradaEdad = scanner.nextLine();

            try {
                edad = Integer.parseInt(entradaEdad);

                // Validacion con if/else para rangos de tipos primitivos
                if (edad < EDAD_MINIMA) {
                    System.out.println("Error: La edad minima permitida es " + EDAD_MINIMA + " anios.");
                } else if (edad > EDAD_MAXIMA) {
                    System.out.println("Error: La edad maxima permitida es " + EDAD_MAXIMA + " anios.");
                } else {
                    edadValida = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero entero valido.");
            }
        } while (!edadValida);

        // Captura de salario con validacion en do-while
        var salarioValido = false;
        var salario = 0.0;

        do {
            System.out.print("Ingrese salario base ($" + String.format("%,.0f", SALARIO_MINIMO)
                    + " - $" + String.format("%,.0f", SALARIO_MAXIMO) + "): ");
            var entradaSalario = scanner.nextLine();

            try {
                salario = Double.parseDouble(entradaSalario);

                // Validacion de rango para tipo double
                if (salario < SALARIO_MINIMO) {
                    System.out.println("Error: El salario no puede ser menor al SMMLV ($"
                            + String.format("%,.0f", SALARIO_MINIMO) + ").");
                } else if (salario > SALARIO_MAXIMO) {
                    System.out.println("Error: El salario excede el maximo permitido ($"
                            + String.format("%,.0f", SALARIO_MAXIMO) + ").");
                } else {
                    salarioValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un valor numerico valido.");
            }
        } while (!salarioValido);

        // Almacenar empleado
        nombresEmpleados[contadorEmpleados] = nombre;
        edadesEmpleados[contadorEmpleados] = edad;
        salariosEmpleados[contadorEmpleados] = salario;
        contadorEmpleados++;

        System.out.println("Empleado '" + nombre + "' registrado exitosamente. ("
                + contadorEmpleados + "/" + MAX_EMPLEADOS + ")");
        return true;
    }

    /**
     * Muestra todos los empleados registrados.
     */
    public static void consultarEmpleados() {
        if (contadorEmpleados == 0) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        System.out.println("\n--- Empleados Registrados (" + contadorEmpleados + ") ---");
        System.out.printf("%-5s %-25s %-8s %-18s%n", "No.", "Nombre", "Edad", "Salario");
        System.out.println("-".repeat(60));

        for (var i = 0; i < contadorEmpleados; i++) {
            System.out.printf("%-5d %-25s %-8d $%,.0f%n",
                    (i + 1), nombresEmpleados[i], edadesEmpleados[i], salariosEmpleados[i]);
        }
    }

    // Getters para acceso desde otros modulos
    public static int getContadorEmpleados() { return contadorEmpleados; }
    public static String[] getNombresEmpleados() { return nombresEmpleados; }
    public static double[] getSalariosEmpleados() { return salariosEmpleados; }
}