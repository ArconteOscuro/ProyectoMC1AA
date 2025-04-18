// @author Andrea Salazar

package Main;

import java.util.ArrayList;

public class Mapita {

    // Imprime un mapa de Karnaugh simple en consola basado en la tabla de verdad
    public static void imprimirMapaKarnaugh(int[][] tabla, int numVars, ArrayList<String> vars) {
        System.out.println("\nMapa de Karnaugh (versión simple por consola):");

        if (numVars < 2 || numVars > 5) {
            System.out.println("Mapa no soportado para esta cantidad de variables (" + numVars + ").");
            return;
        }

        System.out.println("Variables usadas: " + String.join(", ", vars));

        for (int i = 0; i < tabla.length; i++) {
            for (int j = 0; j < numVars; j++) {
                System.out.print(tabla[i][j]);
            }
            System.out.println(" | " + tabla[i][numVars]);
        }

        System.out.println("(Este es solo un formato básico. Para agrupar visualmente se puede usar Excel o una interfaz gráfica).");
    }
}
