package com.empresa.talenthub.model.dto;

/**
 * Record Java 17+ para mapear resultados de consultas SELECT complejas.
 *
 * HU5 — TASK 4: Integración de Records en la persistencia
 *
 * ============================================================
 * ANÁLISIS: Records + JDBC Moderno vs POJOs Tradicionales (Java 8)
 * ============================================================
 *
 * EN JAVA 8 (POJO tradicional), mapear un SELECT requería:
 *   1. Declarar una clase con 5+ campos privados          (~5 líneas)
 *   2. Escribir un constructor                             (~8 líneas)
 *   3. Escribir getters para cada campo                    (~15 líneas)
 *   4. Override de toString(), equals(), hashCode()         (~25 líneas)
 *   TOTAL: ~53 líneas de código "ceremonial" (boilerplate)
 *
 * EN JAVA 17+ (Record), TODO eso se reduce a UNA línea:
 *   record EmpleadoReporte(int id, String nombre, String departamento, double salario, boolean activo) {}
 *
 * El compilador genera automáticamente:
 *   - Constructor canónico (todos los campos como parámetros)
 *   - Getters: id(), nombre(), departamento(), salario(), activo()
 *   - toString(): "EmpleadoReporte[id=1, nombre=Carlos, ...]"
 *   - equals() y hashCode() basados en TODOS los campos
 *
 * VENTAJAS PARA MANTENIMIENTO:
 *   1. INMUTABILIDAD: los campos son final → no hay setters → el objeto
 *      no puede cambiar después de creado. Esto elimina bugs por mutación
 *      accidental (especialmente en entornos concurrentes con múltiples hilos).
 *
 *   2. MENOS CÓDIGO = MENOS BUGS: agregar un nuevo campo a un POJO requiere
 *      modificar constructor, getter, toString, equals y hashCode (5 lugares).
 *      En un Record, solo agregas el campo en la declaración (1 lugar).
 *
 *   3. SEMÁNTICA CLARA: un Record es un "portador de datos transparente".
 *      Cuando otro desarrollador ve un Record, sabe inmediatamente que es
 *      un DTO (Data Transfer Object) sin lógica de negocio.
 *
 *   4. COMBINACIÓN CON JDBC: al mapear un ResultSet, se crea un Record
 *      directamente con los valores del SELECT. Como es inmutable, podemos
 *      pasarlo entre capas (DAO → Controller → View) sin riesgo de que
 *      alguna capa modifique los datos accidentalmente.
 *
 * @param id           Identificador del empleado
 * @param nombre       Nombre completo
 * @param departamento Departamento asignado
 * @param salario      Salario base actual
 * @param activo       true si el empleado está activo en la empresa
 *
 * @author LuisCampillo
 * @version 1.0
 */
public record EmpleadoReporte(
        int id,
        String nombre,
        String departamento,
        double salario,
        boolean activo
) {
    /**
     * Constructor compacto de validación.
     * Los Records permiten validar los datos antes de que se asignen a los campos.
     */
    public EmpleadoReporte {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del empleado no puede estar vacío en el reporte.");
        }
        if (salario < 0) {
            throw new IllegalArgumentException("El salario no puede ser negativo en el reporte.");
        }
    }

    /**
     * Formato legible usando Text Block (Java 17+).
     * Sobrescribe el toString() por defecto del Record para un formato más bonito.
     */
    @Override
    public String toString() {
        return """
                [%d] %s | %s | $%,.0f | %s""".formatted(
                id, nombre, departamento, salario, activo ? "Activo" : "Inactivo"
        );
    }
}
