package com.empresa.talenthub.modelo;

/**
 * Record moderno Java 17+ que modela los datos básicos de la empresa.
 *
 * INMUTABILIDAD: los Records no tienen setters. Sus campos son finales
 * desde el momento de la construcción. Esto los hace seguros para usar
 * en entornos concurrentes y como Value Objects en arquitecturas limpias.
 *
 * Esta única línea genera automáticamente:
 *   - Constructor canónico
 *   - Getters (nombre(), nit(), anioFundacion())
 *   - equals(), hashCode() y toString()
 *
 * @author LuisCampillo
 * @version 1.0
 */

public record EmpresaRecord(String nombre, String nit, int anioFundacion) {
    /**
     * Constructor compacto de validación (buena práctica en Records).
     * Se ejecuta antes de asignar los campos — ideal para reglas de negocio.
     */
    public EmpresaRecord {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(("El nombre de la empresa no puede estar vacio"));
        }
        if(anioFundacion < 1800 || anioFundacion > 2100) {
            throw new IllegalArgumentException("Año de fundación fuera del rango establecido");
        }
    }
}
