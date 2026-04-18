package com.empresa.talenthub.comportamiento;

import com.empresa.talenthub.personas.Empleado;

/**
 * TASK 4 - Contrato de comportamiento para empleados que pueden ser ascendidos.
 *
 * EVOLUCION DE INTERFACES (Java 8+):
 *
 * Antes de Java 8, anadir un metodo nuevo a una interfaz EXISTENTE rompia
 * todas las clases que ya la implementaban: cada una tenia que implementar
 * el metodo nuevo aunque no lo necesitara. Esto hacia que las interfaces
 * publicas de las librerias fueran practicamente inmodificables.
 *
 * Java 8 introdujo los metodos `default`: metodos que llevan su propia
 * implementacion dentro de la interfaz. Las clases que ya implementaban
 * la interfaz heredan automaticamente el metodo default sin cambiar nada.
 * Esto permitio que la Collections API pudiera crecer sin romper codigo
 * existente (por ejemplo, `Iterable.forEach`, `List.sort`).
 *
 * EN ESTA INTERFAZ:
 *   - calcularBonoAscenso()    es ABSTRACTO  -> cada clase decide su regla.
 *   - registrarLogPromocion()  es DEFAULT    -> implementacion comun reutilizable.
 *   - ascender()               es DEFAULT    -> orquesta las dos anteriores.
 *
 * Si anadieramos registrarLogPromocion() o ascender() como metodos
 * abstractos, todas las clases Promocionable existentes tendrian que
 * implementarlos. Al ser `default`, se pueden incorporar sin romper nada.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public interface Promocionable {

    /**
     * Metodo abstracto: cada clase que implemente Promocionable decide
     * segun sus propias reglas cuanto bono le corresponde al ascenso.
     * Sin implementacion por defecto - es una decision de negocio que
     * no puede ser generica.
     */
    double calcularBonoAscenso();

    /**
     * Metodo default (Java 8+): implementacion comun que registra el log
     * de la operacion de ascenso.
     *
     * Si manana necesitamos anadir otro metodo default a esta interfaz
     * (por ejemplo, notificarJefe()), las clases Desarrollador y Gerente
     * NO tendrian que cambiar nada. Esa es la promesa de los default methods:
     * evolucionar la interfaz sin romper a quienes la implementan.
     */
    default void registrarLogPromocion(Empleado e, double bono) {
        System.out.println("[LOG-PROMOCION] Empleado #" + e.getId()
                + " (" + e.getNombre() + ") "
                + "recibe bono de ascenso por $"
                + String.format("%,.0f", bono));
    }

    /**
     * Metodo default que orquesta el flujo completo de ascenso.
     * Combina el calculo (abstracto, lo decide cada clase) con el log
     * (default, reutilizable). El cliente solo llama a ascender() y el
     * flujo queda encapsulado aqui.
     */
    default void ascender(Empleado e) {
        double bono = calcularBonoAscenso();
        registrarLogPromocion(e, bono);
    }
}