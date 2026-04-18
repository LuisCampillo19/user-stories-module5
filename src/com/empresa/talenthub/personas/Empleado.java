package com.empresa.talenthub.personas;

/**
 * TASK 1 - Empleado interno, subclase de la jerarquia sellada Persona.
 *
 * POR QUE `non-sealed`:
 * Cuando una clase padre es `sealed`, Java obliga a que cada subclase
 * declarada en `permits` sea una de estas tres:
 *   - final        -> no puede tener hijos (cierra la rama).
 *   - sealed       -> puede tener hijos, pero solo los declarados en su permits.
 *   - non-sealed   -> "reabre" la jerarquia: cualquier clase puede extenderla.
 *
 * Empleado se marca `non-sealed` porque en la TASK 3 vamos a crear las
 * subclases Desarrollador y Gerente que extienden de Empleado. Si marcaramos
 * Empleado como `final`, no podriamos especializarlo. Si lo marcaramos
 * `sealed`, tendriamos que listar todos sus hijos aqui.
 *
 * NOTA: este Empleado vive en el paquete `personas` y es distinto al
 * `modelo.Empleado` legacy usado en las semanas 2 y 3. No se pisan porque
 * estan en paquetes distintos.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public non-sealed class Empleado extends Persona {

    // protected: accesible desde las subclases Desarrollador y Gerente
    // sin romper el encapsulamiento hacia el exterior.
    protected double salarioBase;

    public Empleado(int id, String nombre, double salarioBase) {
        super(id, nombre);
        this.salarioBase = salarioBase;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    @Override
    public String identificarse() {
        return "Empleado interno #" + id + " - " + nombre;
    }
}