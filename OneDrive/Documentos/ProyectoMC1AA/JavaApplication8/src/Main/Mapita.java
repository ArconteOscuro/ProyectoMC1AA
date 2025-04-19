// @author Andrea Salazar

package Main;

import java.util.ArrayList;

public class Mapita {

    // Este método imprime el mapa de Karnaugh en consola
    public static void imprimirMapaKarnaugh(int[][] tabla, int numVars, ArrayList<String> vars) {
        System.out.println("\nMapa de Karnaugh:");

        // Solo aceptamos entre 2 y 5 variables porque más ya se vuelve muy confuso
        if (numVars < 2 || numVars > 5) {
            System.out.println("No se puede generar mapa para " + numVars + " variables.");
            return;
        }

        // Dividimos las variables en dos grupos, unas van como filas y otras como columnas
        int rowVars = numVars / 2;
        int colVars = numVars - rowVars;

        ArrayList<String> rowLabels = generarGrayCode(rowVars);   // combinaciones de fila
        ArrayList<String> colLabels = generarGrayCode(colVars);   // combinaciones de columna

        // Mostramos qué variables se usaron en qué parte
        System.out.println("Variables:");
        System.out.println("Filas: " + vars.subList(0, rowVars));
        System.out.println("Columnas: " + vars.subList(rowVars, numVars));
        System.out.println();

        // Imprimimos el encabezado del mapa (las columnas)
        System.out.print("     ");
        for (String col : colLabels) {
            System.out.print(col + "  ");
        }
        System.out.println();

        // Recorremos cada fila y combinamos con cada columna para buscar la salida
        for (String row : rowLabels) {
            System.out.print(row + " | ");
            for (String col : colLabels) {
                String[] combinacionFinal = new String[numVars];
                int index = 0;

                // Ponemos los bits de la fila primero
                for (int i = 0; i < rowVars; i++) {
                    combinacionFinal[index++] = String.valueOf(row.charAt(i));
                }

                // Luego los de la columna
                for (int i = 0; i < colVars; i++) {
                    combinacionFinal[index++] = String.valueOf(col.charAt(i));
                }

                // Buscamos la salida que corresponde a esta combinación
                int salida = buscarValor(tabla, combinacionFinal);
                System.out.print(salida + "   ");
            }
            System.out.println();
        }
    }

    // Compara una combinación binaria con las filas de la tabla para encontrar la salida
    private static int buscarValor(int[][] tabla, String[] bin) {
        for (int[] fila : tabla) {
            boolean match = true;
            for (int j = 0; j < bin.length; j++) {
                if (fila[j] != Integer.parseInt(bin[j])) {
                    match = false;
                    break;
                }
            }
            if (match) return fila[bin.length]; // cuando encontramos la combinación, devolvemos su salida
        }
        return 0; // si no se encuentra, devolvemos 0 por defecto
    }

    // Genera una lista de combinaciones en código Gray para que el mapa tenga sentido
    private static ArrayList<String> generarGrayCode(int n) {
        ArrayList<String> grayCodes = new ArrayList<>();
        for (int i = 0; i < (1 << n); i++) {
            int gray = i ^ (i >> 1); // operación para convertir a Gray
            String bin = String.format("%" + n + "s", Integer.toBinaryString(gray)).replace(' ', '0');
            grayCodes.add(bin);
        }
        return grayCodes;
    }
}
