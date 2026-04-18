package com.empresa.talenthub.colecciones;

import java.util.List;
import java.util.Map;

/**
 * TASK 2 - Inicializacion y Factory Methods (Legacy Java 9/11).
 *
 * Java 9 introdujo los metodos de factoria List.of() y Map.of() para crear
 * colecciones pequenias con una sola linea de codigo.
 *
 * POR QUE SON MAS SEGURAS QUE UN ArrayList TRADICIONAL:
 *   - Son INMUTABLES: el contenido queda fijado en el momento de la creacion.
 *     Nadie puede llamar .add(), .remove() o .set() sobre ellas sin que lance
 *     UnsupportedOperationException. Esto evita que otra parte del programa
 *     modifique por accidente una lista de configuracion.
 *   - No aceptan valores null, asi que fallan temprano si intentamos meter uno.
 *   - Son mas compactas en memoria que un ArrayList vacio.
 *
 * LIMITACION HONESTA: precisamente por ser inmutables, si necesitamos anadir
 * una tecnologia o una sede nueva en tiempo de ejecucion, hay que crear una
 * lista nueva (por ejemplo con new ArrayList<>(List.of(...))). No sirven para
 * datos que cambian; solo para catalogos de configuracion estables.
 *
 * @author LuisCampillo
 * @version 1.0
 */
public class ConfiguracionCorporativa {

    /** Catalogo de tecnologias soportadas por la empresa. Inmutable. */
    public static final List<String> TECNOLOGIAS = List.of(
            "Java 21",
            "Spring Boot",
            "Power Apps",
            "Power Automate",
            "SharePoint",
            "Azure"
    );

    /** Mapa de codigo de sede -> ciudad. Inmutable. */
    public static final Map<String, String> SEDES = Map.of(
            "MED", "Medellin",
            "BOG", "Bogota",
            "CLO", "Cali",
            "BAQ", "Barranquilla"
    );

    public static void imprimirCatalogo() {
        System.out.println("Tecnologias oficiales: " + TECNOLOGIAS);
        System.out.println("Sedes corporativas:    " + SEDES);

        // Demostracion de la inmutabilidad: el siguiente add() compilaria,
        // pero en tiempo de ejecucion lanzaria UnsupportedOperationException.
        try {
            TECNOLOGIAS.add("COBOL");
        } catch (UnsupportedOperationException ex) {
            System.out.println("[OK] TECNOLOGIAS es inmutable, no se pudo agregar: " + ex.getClass().getSimpleName());
        }
    }
}