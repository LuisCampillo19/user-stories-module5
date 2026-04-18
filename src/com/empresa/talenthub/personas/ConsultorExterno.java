package com.empresa.talenthub.personas;

/**
 * TASK 1 - Consultor externo: persona contratada de forma temporal por
 * una empresa proveedora.
 *
 * Marcada `final` porque la jerarquia se cierra aqui: ninguna clase puede
 * heredar de ConsultorExterno. La sealed class padre (Persona) obliga a
 * decidir entre final / sealed / non-sealed, y aqui no tiene sentido
 * permitir mas especializaciones.
 *
 * Encapsulamiento total: todos los campos son `private final`. Se fijan
 * en el constructor y no se pueden modificar despues.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public final class ConsultorExterno extends Persona {

    private final String empresaProveedora;
    private final double tarifaHora;

    public ConsultorExterno(int id, String nombre, String empresaProveedora, double tarifaHora) {
        super(id, nombre);
        this.empresaProveedora = empresaProveedora;
        this.tarifaHora = tarifaHora;
    }

    public String getEmpresaProveedora() {
        return empresaProveedora;
    }

    public double getTarifaHora() {
        return tarifaHora;
    }

    @Override
    public String identificarse() {
        return "Consultor externo #" + id + " - " + nombre
                + " (empresa: " + empresaProveedora + ")";
    }
}