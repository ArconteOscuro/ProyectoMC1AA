package Main;

import java.util.ArrayList;
import java.util.Scanner;

public class JavaApplication8 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Tabla_De_Verdad tabla = new Tabla_De_Verdad();

        // Acá se hace la pregunta inicial al usuario
        System.out.print("¿Deseas subir archivo XML? (si/no): ");
        String opcion = scanner.nextLine().trim().toLowerCase();

        // Acá es la lectura si se subió XML o la entrada 
        if (opcion.equals("si")) {
            System.out.print("Escribe la ruta del archivo (ej: C:\\\\Users\\\\TuUsuario\\\\archivo.xml): ");
            String ruta = scanner.nextLine();

            if (LectorXML.cargarDesdeXML(ruta, tabla)) {
                System.out.println("XML subido correctamente.");
            } else {
                System.out.println("No subiste correctamente el XML.");
                return;
            }
        } else {
            tabla.entradaManual();
        }

        // Se imprime la tabla de verdad
        tabla.imprimir();

        // Imprimir mapa de Karnaugh directamente desde aquí
        imprimirMapaKarnaugh(
            tabla.getMatriz(),
            tabla.getCantidadVars(),
            tabla.getNombres()
        );

        // Se muestra la función canónica
        String canonica = Simplificador.obtenerFuncionCanonica(
            tabla.getMatriz(),
            tabla.getNombres(),
            tabla.getCantidadVars()
        );
        System.out.println("\nFunción Booleana Canónica: " + canonica);

        // Se muestra la función simplificada
        String simplificada = Simplificador.simplificar(
            tabla.getMatriz(),
            tabla.getCantidadVars(),
            tabla.getNombres()
        );
        System.out.println("Función Simplificada: " + simplificada);

        // Se muestra el conteo de compuertas
        Simplificador.contarCompuertas(simplificada);

        scanner.close();
    }

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

        System.out.print("     ");
        for (String col : colLabels) {
            System.out.print(col + "  ");
        }
        System.out.println();

        for (String row : rowLabels) {
            System.out.print(row + " | ");
            for (String col : colLabels) {
                String combinacion = row + col;
                int index = Integer.parseInt(combinacion, 2);

                if (index < tabla.length && tabla[index].length > 0) {
                    System.out.print(tabla[index][0] + "   ");
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println();
        }
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
