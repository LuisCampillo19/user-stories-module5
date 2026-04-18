package com.empresa.talenthub.personas;

import com.empresa.talenthub.comportamiento.Promocionable;

/**
 * TASK 3 - Desarrollador: empleado con especialidad tecnica.
 *
 * Extiende de Empleado (que es `non-sealed`), asi que no necesita aparecer
 * en ningun `permits`. Se marca `final` para cerrar la jerarquia aqui.
 *
 * Implementa Promocionable (TASK 4), lo que obliga a definir el calculo
 * del bono de ascenso especifico para este rol.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public final class Desarrollador extends Empleado implements Promocionable {

    // private final: encapsulamiento total + inmutabilidad del lenguaje principal.
    private final String lenguajePrincipal;

    public Desarrollador(int id, String nombre, double salarioBase, String lenguajePrincipal) {
        super(id, nombre, salarioBase);
        this.lenguajePrincipal = lenguajePrincipal;
    }

    public String getLenguajePrincipal() {
        return lenguajePrincipal;
    }

    @Override
    public String identificarse() {
        return "Desarrollador #" + id + " - " + nombre
                + " [lenguaje: " + lenguajePrincipal + "]";
    }

    /**
     * Regla de negocio: un desarrollador recibe el 15% del salario base
     * como bono de ascenso.
     */
    @Override
    public double calcularBonoAscenso() {
        return salarioBase * 0.15;
    }
}