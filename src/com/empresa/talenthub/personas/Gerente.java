package com.empresa.talenthub.personas;

import com.empresa.talenthub.comportamiento.Promocionable;

/**
 * TASK 3 - Gerente: empleado con responsabilidad de manejo de presupuesto.
 *
 * Igual que Desarrollador, extiende Empleado (non-sealed) y se cierra con
 * `final`. Implementa Promocionable con su propia regla de bono.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public final class Gerente extends Empleado implements Promocionable {

    private final double presupuestoMensual;

    public Gerente(int id, String nombre, double salarioBase, double presupuestoMensual) {
        super(id, nombre, salarioBase);
        this.presupuestoMensual = presupuestoMensual;
    }

    public double getPresupuestoMensual() {
        return presupuestoMensual;
    }

    @Override
    public String identificarse() {
        return "Gerente #" + id + " - " + nombre
                + " (presupuesto mensual: $" + String.format("%,.0f", presupuestoMensual) + ")";
    }

    /**
     * Regla de negocio: un gerente recibe el 20% del salario base como
     * bono de ascenso (mas alto que el desarrollador por la responsabilidad
     * adicional de manejo de presupuesto).
     */
    @Override
    public double calcularBonoAscenso() {
        return salarioBase * 0.20;
    }
}