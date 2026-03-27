package com.empresa.talenthub.arquitectura;

/**
 * Clase de documentación arquitectura del sistema Corporate Talent Hub.
 * @author LuisCampillo
 * @version 1.0
 * @since 2026
 */
public class NotasArquitectura {

    /**
     * ¿Por qué se le dice Legacy al Java 8?
     * ¿Qué significa que Java 17 y 21 sean de 'LTS' (Long Term Support)?
     * Features nuevas de Java 17
     * ¿Por qué una empresa debería migrarse del 8 al 17?
     */

    String legacyJava = """
            
            Java 8 fue la versión lanzada en 2014 y tuvo buum muy popular al introducir:
            Lammdas, Streams API y Programación funcional básica. En pocas palabras, se le dice
            legacy porque es muy antigua, alrededor de más de 10 años, muchas empresas la siguen usando
            por la estabilidad que otorga, pero ya está bastante desactualizada frente a muchos estándares actuales
            tiene menos soporte y mejoras modernas. Simplemente no es mala, es que ya se quedó atrás.
            """;
    String significadoLTS = """
            
            LTS (Long Term Support) son versiones con soporte a largo plazo, reciben generalmente parches de seguridad,
            correcciones de bug y estabilidad garantizada. Unos ejemplos son Java 8, Java 11, Java 17 y Java 21, por lo general
            las empresas usan LTS porque son seguras para producción.
            """;
    String featuresNuevas = """
            
            I. Records: Estas fueron una implementación de las clases, para hacerlas más simples al momento de escribirlas
            Ejemplo: record persona(String nombre, int edad) {}
            Evita los getters, setters y constructores manuales
            
            II. Text Blocks: Son cadenas de texto multilínea (Strings) más limpios y organizados.
            El ejemplo, son las que estan aquí actualmente, abres y cierras con tres comillas.
            
            III. Helpful NullPointerExceptions: La mejora en la versión 17 de java, era clarificar de mejor forma los errores
            en tiempo real, indicando exactamente qué varaible era nula, que método se intentó llamar o qué campo en la
            línea fallo.
            """;

    String migrarJava = """
            Por más establidad y parches de seguridad que tenga Java 8, la posibilidad de migrar o tenerlo contemplado es importante
            debido a que temas más específicos de redmimento y mejoras continuas. Código compacto, mejor gestión de memoria, y compatibilidad
            con herramientas modernas reduciendo costos a futuro.
            """;

    String gestionMemoria = """
        La JVM (Java Virtual Machine) organiza la memoria en dos zonas principales:
        - Stack: almacena las variables locales y las llamadas a métodos. Se libera automáticamente
          cuando el método termina.
        - Heap: aquí viven todos los objetos creados con 'new'. El Garbage Collector (GC) es el
          responsable de limpiar esta zona.
        
        El Garbage Collector detecta objetos que ya no tienen ninguna referencia apuntándolos
        y los elimina, liberando memoria sin que el programador lo haga manualmente.
        En Java 8 el GC por defecto era Parallel GC. En Java 17/21 el predeterminado es
        G1GC (Garbage First), más eficiente para aplicaciones de baja latencia.
        """;
}

