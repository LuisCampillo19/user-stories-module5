package com.empresa.talenthub.modelo;

/**
 * Clase tradicional estilo Java 8 que modela un empleado del sistema.
 *
 * ANÁLISIS — Clase vs Record:
 * Esta clase requiere declarar manualmente constructor, getters, setters,
 * toString(), equals() y hashCode(). Son fácilmente más de 80 líneas de
 * código "ceremonial" solo para sostener datos.
 *
 * Un Record de Java 17 reemplaza todo esto con una sola línea:
 *   record Empleado(int id, String nombre, double salario) {}
 * El compilador genera el constructor, los getters y toString() de forma
 * automática. Además, los Records son INMUTABLES por diseño: una vez creado
 * el objeto, sus campos no pueden modificarse. Esta clase Empleado, en cambio,
 * es mutable — cualquiera puede llamar a un setter y cambiar sus valores.
 *
 * @author LuisCampillo
 * @version 1.0
 */

public class Empleado {
    // 8 tipos primitivos

    private byte nivelAcceso; // Ideal niveles de acceso o categorías pequeñas
    private short codigoDepartamento; // Util para códigos de sede o departamento
    private int idEmpleo; // El estándar de Java, numeros enteros
    private long numeroDocumento; // más espacio en memoria que el INT
    private float porcentajeDescuento; // Menor presición que el double. Numero flotante
    private double salarioBase; // Precisión estandar para valores monetarios
    private char genero; // un unico caracter
    private boolean esActivo; // falso o verdadero, estado logico
    private String nombre; // No es primitivo, es una clase como tal. Hace referencia en el Heap.

    // metodo constructor
    public Empleado(byte nivelAcceso, short codigoDepartamento, int idEmpleo, long numeroDocumento, float porcentajeDescuento, double salarioBase, char genero, boolean esActivo, String nombre){

        this.nivelAcceso = nivelAcceso;
        this.codigoDepartamento = codigoDepartamento;
        this.idEmpleo = idEmpleo;
        this.numeroDocumento = numeroDocumento;
        this.porcentajeDescuento = porcentajeDescuento;
        this.salarioBase = salarioBase;
        this.genero = genero;
        this.esActivo = esActivo;
        this.nombre = nombre;
    }

    // getters y setters


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEsActivo() {
        return esActivo;
    }

    public void setEsActivo(boolean esActivo) {
        this.esActivo = esActivo;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public float getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(float porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public int getIdEmpleo() {
        return idEmpleo;
    }

    public void setIdEmpleo(int idEmpleo) {
        this.idEmpleo = idEmpleo;
    }

    public short getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(short codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public byte getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(byte nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    // toString - información legible
    @Override // anotación que le dice a Java que esta sobrescribiendo un método de la clase padre
    public String toString(){
        return "Empleado{" +
                "id=" + idEmpleo +
                ", nombre='" + nombre +'\'' +
                ", salario=" + salarioBase +
                ", activo=" + esActivo +
                '}';
    }


}
