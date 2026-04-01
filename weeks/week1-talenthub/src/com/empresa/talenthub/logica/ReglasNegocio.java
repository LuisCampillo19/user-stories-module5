package com.empresa.talenthub.logica;

/**
 * Motor de reglas de negocio del sistema Corporate Talent Hub.
 * Demuestra el uso de operadores aritméticos, lógicos y de asignación,
 * aplicando correctamente la jerarquía de precedencia de Java.
 *
 * @author LuisCampillo
 * @version 1.0
 */

public class ReglasNegocio {

    private int idEmpleado;
    private double salarioBase;
    private double bonoMensual;
    private int puntajeTest;
    private int edad;
    private int idSede;
    private boolean esActivo;

    public ReglasNegocio(int idEmpleado, double salarioBase, double bonoMensual, int puntajeTest, int edad, int idSede, boolean esActivo){

        this.idEmpleado = idEmpleado;
        this.salarioBase = salarioBase;
        this.bonoMensual = bonoMensual;
        this.puntajeTest = puntajeTest;
        this.edad = edad;
        this.idSede = idSede;
        this.esActivo = esActivo;
    }

    /**
     * Calcula el salario final aplicando bono y descuento.
     *
     * Expresión: (salarioBase + (bonoMensual * 1.10)) - (salarioBase * 0.05)
     *
     * ORDEN DE EJECUCIÓN (jerarquía de operadores):
     *   1. Paréntesis internos primero — Java siempre resuelve de adentro hacia afuera:
     *      a) (bonoMensual * 1.10)  → bono con incremento del 10%
     *      b) (salarioBase * 0.05)  → deducción del 5% sobre el salario base
     *   2. Suma: salarioBase + resultado_de_(a)
     *   3. Resta: resultado_de_suma - resultado_de_(b)
     *
     * Sin paréntesis, * tendría precedencia sobre + y - de todas formas,
     * pero usarlos hace el código legible y elimina ambigüedad.
     */
    public double calcularSalarioFinal() {
        double salarioFinal = (salarioBase + (bonoMensual * 1.10)) - (salarioBase * 0.05);
        return salarioFinal;
    }

    /**
     * Determina si el empleado recibe un bono extra por tener ID par.
     *
     * OPERADOR MÓDULO (%): devuelve el residuo de la división.
     *   idEmpleado % 2 == 0  →  si el residuo es 0, el ID es par → bono extra.
     *   Ejemplo: 1002 % 2 = 0 → par → true
     *            1001 % 2 = 1 → impar → false
     */
    public boolean tieneBonoExtra() {
        return (idEmpleado % 2 == 0);
    }

    /**
     * Aplica el bono extra sumándolo al bonoMensual existente.
     *
     * ASIGNACIÓN COMPUESTA (+=):
     *   bonoMensual += 500000  es exactamente igual a:
     *   bonoMensual = bonoMensual + 500000
     *   Solo es más conciso y es la convención estándar en Java.
     */
    public void aplicarBonoExtra(double montoExtra) {
        bonoMensual += montoExtra;
        System.out.println("Bono actualizado con +=: $" + bonoMensual);
    }

    /**
     * Valida si un candidato es elegible según reglas de negocio compuestas.
     *
     * Expresión: (puntajeTest > 85 && edad < 30) || (idSede == 1 && !esActivo)
     *
     * ORDEN DE PRECEDENCIA lógica (de mayor a menor):
     *   1. ! (NOT)   — se evalúa primero: !esActivo invierte el booleano
     *   2. && (AND)  — se evalúa antes que ||
     *      a) (puntajeTest > 85 && edad < 30)  → joven con alto puntaje
     *      b) (idSede == 1 && !esActivo)        → sede 1 e inactivo
     *   3. || (OR)   — si cualquiera de los dos grupos es true, el resultado es true
     *
     * Regla de negocio: es elegible si tiene buen puntaje siendo joven,
     * O si pertenece a la sede principal y está inactivo (para reactivación).
     */
    public boolean validarElegibilidad() {
        boolean elegible = (puntajeTest > 85 && edad < 30) || (idSede == 1 && !esActivo);
        return elegible;
    }

    // getters del main

    public double getBonoMensual() { return bonoMensual; }
    public int getIdEmpleado() { return idEmpleado; }
}

