package com.empresa.talenthub.reportes;

/**
 * TASK 2 - Record de reporte de desempenio mensual.
 *
 * LEGACY (Java 8/11): un POJO equivalente requeriria ~60 lineas de codigo:
 *   - 3 campos private final
 *   - constructor con 3 parametros y asignaciones
 *   - 3 getters manuales (getIdEmpleado, getPromedio, getFeedback)
 *   - toString() manual
 *   - equals() y hashCode() manuales
 *
 * Ejemplo del estilo viejo:
 *
 *   public class DesempenioReportPojo {
 *       private final int idEmpleado;
 *       private final double promedio;
 *       private final String feedback;
 *
 *       public DesempenioReportPojo(int idEmpleado, double promedio, String feedback) {
 *           this.idEmpleado = idEmpleado;
 *           this.promedio = promedio;
 *           this.feedback = feedback;
 *       }
 *       public int getIdEmpleado() { return idEmpleado; }
 *       public double getPromedio() { return promedio; }
 *       public String getFeedback() { return feedback; }
 *       // ... toString, equals, hashCode
 *   }
 *
 * MODERNO (Java 17+): una sola linea. El compilador genera automaticamente:
 *   - Constructor canonico
 *   - Accessors: idEmpleado(), promedio(), feedback() (sin prefijo "get")
 *   - toString() con todos los campos
 *   - equals() y hashCode() basados en todos los campos
 *
 * VENTAJA PRINCIPAL - INMUTABILIDAD:
 * Los campos de un record son `final` por diseno. No existen setters.
 * Esto significa que una vez emitido un reporte de desempenio, nadie
 * puede alterarlo. Si auditoria pide el reporte de Abril dentro de seis
 * meses, el valor leido es exactamente el que se escribio. Critico para
 * datos de RRHH y cumplimiento normativo.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public record DesempenioReport(int idEmpleado, double promedio, String feedback) {

    /**
     * Constructor compacto de validacion.
     * Se ejecuta ANTES de asignar los campos. Aplica el principio fail-fast:
     * si los datos son invalidos, el objeto nunca llega a construirse y no
     * contamina el resto del sistema.
     */
    public DesempenioReport {
        if (idEmpleado <= 0) {
            throw new IllegalArgumentException("El id del empleado debe ser positivo");
        }
        if (promedio < 0.0 || promedio > 5.0) {
            throw new IllegalArgumentException("El promedio debe estar entre 0.0 y 5.0");
        }
        if (feedback == null || feedback.isBlank()) {
            throw new IllegalArgumentException("El feedback no puede estar vacio");
        }
    }

    /**
     * Metodo de fabrica que genera un reporte con feedback automatico
     * segun el rango del promedio. Util para reportes masivos de fin de mes.
     */
    public static DesempenioReport generarAutomatico(int idEmpleado, double promedio) {
        String feedbackAuto;
        if (promedio >= 4.5) {
            feedbackAuto = "Desempenio sobresaliente - candidato a ascenso";
        } else if (promedio >= 3.5) {
            feedbackAuto = "Desempenio satisfactorio - mantener en rol actual";
        } else if (promedio >= 2.5) {
            feedbackAuto = "Desempenio aceptable - plan de mejora sugerido";
        } else {
            feedbackAuto = "Desempenio bajo - intervencion RRHH requerida";
        }
        return new DesempenioReport(idEmpleado, promedio, feedbackAuto);
    }
}