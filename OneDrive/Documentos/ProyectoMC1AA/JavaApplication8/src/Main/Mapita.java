// @author Andrea Salazar

package Main;

import java.util.ArrayList;

public class Mapita {

    public static void imprimirMapaKarnaugh(int[][] tabla, int numVars, ArrayList<String> vars) {
        System.out.println("\nMapa de Karnaugh (formato tradicional):");

        if (numVars < 2 || numVars > 5) {
            System.out.println("No se puede generar mapa para " + numVars + " variables.");
            return;
        }

        int rowVars = numVars / 2;
        int colVars = numVars - rowVars;

        ArrayList<String> rowLabels = generarGrayCode(rowVars);
        ArrayList<String> colLabels = generarGrayCode(colVars);

        System.out.println("Variables:");
        System.out.println("Filas: " + vars.subList(0, rowVars));
        System.out.println("Columnas: " + vars.subList(rowVars, numVars));
        System.out.println();

        // Encabezado
        System.out.print("     ");
        for (String col : colLabels) {
            System.out.print(col + "  ");
        }
        System.out.println();

        // Cuerpo del mapa
        for (String row : rowLabels) {
            System.out.print(row + " | ");
            for (String col : colLabels) {
                String combinacion = row + col;
                int salida = buscarValor(tabla, combinacion, numVars);
                System.out.print(salida + "   ");
            }
            System.out.println();
        }
    }

    // Busca en la tabla de verdad el valor de salida para una combinación
    private static int buscarValor(int[][] tabla, String bin, int numVars) {
        for (int[] fila : tabla) {
            StringBuilder combinacion = new StringBuilder();
            for (int j = 0; j < numVars; j++) {
                combinacion.append(fila[j]);
            }
            if (combinacion.toString().equals(bin)) {
                return fila[numVars];
            }
        }
        return 0;
    }

    // Código Gray
    private static ArrayList<String> generarGrayCode(int n) {
        ArrayList<String> grayCodes = new ArrayList<>();
        for (int i = 0; i < (1 << n); i++) {
            int gray = i ^ (i >> 1);
            String bin = String.format("%" + n + "s", Integer.toBinaryString(gray)).replace(' ', '0');
            grayCodes.add(bin);
        }
        return grayCodes;
    }
}
