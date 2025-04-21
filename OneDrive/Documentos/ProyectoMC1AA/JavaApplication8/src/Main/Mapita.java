package Main;

import java.util.ArrayList;

public class Mapita {

    public static String generarMapaKarnaugh(int[][] tabla, int numVars, ArrayList<String> vars) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n Mapa de Karnaugh:\n\n");

        if (numVars < 2 || numVars > 5) {
            sb.append("No se puede generar mapa para ").append(numVars).append(" variables.\n");
            return sb.toString();
        }

        int rowVars = numVars / 2;
        int colVars = numVars - rowVars;

        ArrayList<String> rowLabels = generarGrayCode(rowVars);
        ArrayList<String> colLabels = generarGrayCode(colVars);

        sb.append(" Variables:\n");
        sb.append("   Filas: ").append(vars.subList(0, rowVars)).append("\n");
        sb.append("   Columnas: ").append(vars.subList(rowVars, numVars)).append("\n\n");

        
        sb.append("       ");
        for (String col : colLabels) {
            sb.append(String.format("%-5s", col));
        }
        sb.append("\n");
        sb.append("     " + "-".repeat(colLabels.size() * 5));
        sb.append("\n");

       
        for (String row : rowLabels) {
            sb.append(String.format("%-3s | ", row));
            for (String col : colLabels) {
                String[] combinacionFinal = new String[numVars];
                int index = 0;

                for (int i = 0; i < rowVars; i++) {
                    combinacionFinal[index++] = String.valueOf(row.charAt(i));
                }

                for (int i = 0; i < colVars; i++) {
                    combinacionFinal[index++] = String.valueOf(col.charAt(i));
                }

                int salida = buscarValor(tabla, combinacionFinal);
                sb.append(String.format("%-5s", salida));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private static int buscarValor(int[][] tabla, String[] bin) {
        for (int[] fila : tabla) {
            boolean match = true;
            for (int j = 0; j < bin.length; j++) {
                if (fila[j] != Integer.parseInt(bin[j])) {
                    match = false;
                    break;
                }
            }
            if (match) return fila[bin.length];
        }
        return 0;
    }

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
