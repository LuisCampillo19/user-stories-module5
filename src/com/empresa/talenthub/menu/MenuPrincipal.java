package com.empresa.talenthub.menu;

/**
 * TASK 1 - El gran cambio: Switch Legacy vs Moderno
 *
 * Comparacion: Switch Legacy (Java 8) vs Switch Expression (Java 17/21)
 *
 * LEGACY (Java 8) - switch con case/break:
 *   - Requiere 'break' obligatorio en cada case.
 *   - Si olvidas el 'break', ocurre "fall-through": la ejecucion continua
 *     al siguiente case sin detenerse, causando bugs silenciosos.
 *   - No retorna valor directamente; necesitas una variable auxiliar.
 *
 * MODERNO (Java 17/21) - Switch Expression con ->:
 *   - Usa la sintaxis de flecha (->) que elimina el riesgo de fall-through.
 *   - Cada rama es una expresion que retorna valor directamente.
 *   - Es mas conciso, seguro y legible.
 *   - El compilador obliga a cubrir todos los casos (exhaustividad).
 */
public class MenuPrincipal {

    /**
     * Menu principal del sistema usando switch LEGACY (Java 8).
     * Notese el uso obligatorio de 'break' para evitar fall-through.
     */
    public static String obtenerOpcionMenuLegacy(int opcion) {
        String resultado;

        // Switch clasico Java 8: case + break obligatorio
        switch (opcion) {
            case 1:
                resultado = "Registrar empleado";
                break; // Si olvidamos este break, se ejecutaria tambien el case 2 (fall-through)
            case 2:
                resultado = "Consultar empleados";
                break;
            case 3:
                resultado = "Matriz de desempenio";
                break;
            case 4:
                resultado = "Categoria salarial";
                break;
            case 5:
                resultado = "Salir del sistema";
                break;
            default:
                resultado = "Opcion no valida";
                break;
        }

        return resultado;
    }

    /**
     * Determina la categoria salarial usando Switch Expression MODERNO (Java 17/21).
     *
     * Ventajas sobre el switch legacy:
     * - No hay riesgo de fall-through (no se usa break).
     * - Retorna valor directamente con la sintaxis de flecha (->).
     * - El compilador exige que todos los casos esten cubiertos.
     *
     * @param salarioBase salario base del empleado
     * @return categoria salarial del empleado
     */
    public static String obtenerCategoriaSalarial(double salarioBase) {
        // Switch Expression moderna (Java 17/21) con sintaxis de flecha
        int rango = (int) (salarioBase / 1_000_000);

        return switch (rango) {
            case 0 -> "Categoria E - Practicante (< $1.000.000)";
            case 1 -> "Categoria D - Junior ($1.000.000 - $1.999.999)";
            case 2 -> "Categoria C - Semi-Senior ($2.000.000 - $2.999.999)";
            case 3 -> "Categoria B - Senior ($3.000.000 - $3.999.999)";
            default -> {
                if (rango >= 4) {
                    yield "Categoria A - Lead ($4.000.000+)";
                } else {
                    yield "Salario no valido";
                }
            }
        };
        /*
         * Notese: 'yield' se usa dentro de bloques {} en switch expressions
         * cuando necesitamos mas de una linea de logica.
         * En Java 8 esto requeriria un if-else-if encadenado o un switch
         * con variable auxiliar y breaks.
         */
    }

    /**
     * Muestra el menu en consola (formato visual).
     */
    public static void mostrarMenu() {
        System.out.println("========================================");
        System.out.println("   CORPORATE TALENT HUB v2.0            ");
        System.out.println("========================================");
        System.out.println("  1. Registrar empleado                 ");
        System.out.println("  2. Consultar empleados                ");
        System.out.println("  3. Matriz de desempenio               ");
        System.out.println("  4. Consultar categoria salarial       ");
        System.out.println("  5. Salir                              ");
        System.out.println("========================================");
        System.out.print("Seleccione una opcion: ");
    }
}