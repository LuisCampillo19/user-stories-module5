package com.empresa.talenthub.personas;

/**
 * TASK 1 - Herencia sellada (Java 17+).
 *
 * Clase abstracta base de la jerarquia de perfiles del sistema.
 *
 * LEGACY (Java 8/11): una clase abstract podia ser heredada por CUALQUIER
 * clase del proyecto. Si alguien anadia una subclase nueva sin avisar
 * (por ejemplo "Robot extends Persona"), rompia invariantes del dominio
 * y no habia forma de impedirlo a nivel de lenguaje.
 *
 * MODERNO (Java 17+): la clausula `sealed ... permits` restringe de forma
 * EXPLICITA quien puede heredar. Aqui solo Empleado y ConsultorExterno
 * pueden extender Persona. Cualquier otra clase que intente hacerlo ni
 * siquiera compila.
 *
 * POR QUE LAS SEALED CLASSES SON MAS SEGURAS EN EL DISENO DE APIs:
 *   1. El dominio queda PROTEGIDO: nadie puede introducir perfiles nuevos
 *      sin tocar esta declaracion, lo cual obliga a pasar por revision
 *      de codigo.
 *   2. El compilador exige EXHAUSTIVIDAD en switch/pattern matching:
 *      si olvidamos un caso (p.ej. un nuevo tipo de Persona), el codigo
 *      no compila.
 *   3. Documenta el dominio: con solo mirar el `permits` sabes todas las
 *      variantes posibles sin tener que buscar en el proyecto.
 *   4. Evita herencia accidental desde librerias externas.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public abstract sealed class Persona
        permits Empleado, ConsultorExterno {

    protected final int id;
    protected final String nombre;

    protected Persona(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Comportamiento abstracto que cada tipo de Persona implementa a su manera.
     * Demuestra polimorfismo: la misma operacion se comporta distinto segun
     * el tipo real del objeto.
     */
    public abstract String identificarse();
}