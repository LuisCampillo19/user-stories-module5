package com.empresa.talenthub.model.entity;

/**
 * Entidad que representa un empleado persistido en la tabla 'empleados'.
 *
 * ANÁLISIS — Clase POJO vs Record (Java 17+):
 * Esta clase es mutable (tiene setters) lo cual es necesario para las
 * operaciones de UPDATE donde se modifican campos individuales.
 * Para lectura inmutable, usamos Records (ver EmpleadoReporte en task4).
 *
 * @author LuisCampillo
 * @version 1.0
 */
public class Empleado {

    private int id;
    private String nombre;
    private long documento;
    private String departamento;
    private double salarioBase;
    private char genero;
    private boolean esActivo;
    private String fechaIngreso;

    // Constructor vacío (necesario para mapear desde ResultSet)
    public Empleado() {}

    // Constructor completo (para INSERT — sin id, lo genera la BD)
    public Empleado(String nombre, long documento, String departamento,
                    double salarioBase, char genero, boolean esActivo, String fechaIngreso) {
        this.nombre = nombre;
        this.documento = documento;
        this.departamento = departamento;
        this.salarioBase = salarioBase;
        this.genero = genero;
        this.esActivo = esActivo;
        this.fechaIngreso = fechaIngreso;
    }

    // Constructor con id (para UPDATE y mapeo desde SELECT)
    public Empleado(int id, String nombre, long documento, String departamento,
                    double salarioBase, char genero, boolean esActivo, String fechaIngreso) {
        this(nombre, documento, departamento, salarioBase, genero, esActivo, fechaIngreso);
        this.id = id;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public long getDocumento() { return documento; }
    public void setDocumento(long documento) { this.documento = documento; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

    public char getGenero() { return genero; }
    public void setGenero(char genero) { this.genero = genero; }

    public boolean isEsActivo() { return esActivo; }
    public void setEsActivo(boolean esActivo) { this.esActivo = esActivo; }

    public String getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(String fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    @Override
    public String toString() {
        return "Empleado{id=%d, nombre='%s', documento=%d, depto='%s', salario=%.0f, activo=%s}"
                .formatted(id, nombre, documento, departamento, salarioBase, esActivo);
    }
}
