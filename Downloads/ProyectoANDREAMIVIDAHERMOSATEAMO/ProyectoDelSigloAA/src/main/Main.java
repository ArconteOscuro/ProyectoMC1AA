/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author alema
 */
public class Main {
    package main;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // PASO 1: SOLICITAR EL NÚMERO Y NOMBRE DE VARIABLES
        System.out.print("Ingrese el número de variables (2-5): ");
        int numeroVariables = scanner.nextInt();
        scanner.nextLine();

        if (numeroVariables < 2 || numeroVariables > 5) {
            System.out.println("Error: El número de variables debe estar entre 2 y 5.");
            scanner.close();
            return;
        }

        ArrayList<String> nombresVariables = new ArrayList<>();
        for (int i = 0; i < numeroVariables; i++) {
            System.out.print("Ingrese el nombre de la variable " + (i + 1) + ": ");
            nombresVariables.add(scanner.nextLine());
        }

        // PASO 2: GENERAR LA TABLA DE VERDAD BINARIA
        int numeroFilas = (int) Math.pow(2, numeroVariables);
        int[][] tablaVerdad = new int[numeroFilas][numeroVariables + 1];

        for (int columna = 0; columna < numeroVariables; columna++) {
            int contador = 0;
            int valor = 0;
            int limite = (int) Math.pow(2, numeroVariables - columna - 1);

            for (int fila = 0; fila < numeroFilas; fila++) {
                tablaVerdad[fila][columna] = valor;
                contador++;
                if (contador == limite) {
                    valor = 1 - valor;
                    contador = 0;
                }
            }
        }

        // PASO 3: SOLICITAR LOS VALORES DE LA FUNCIÓN
        System.out.println("\nIngrese los valores de la función (0 o 1):");
        for (int fila = 0; fila < numeroFilas; fila++) {
            while (true) {
                System.out.print("Para (");
                for (int columna = 0; columna < numeroVariables; columna++) {
                    System.out.print(nombresVariables.get(columna) + "=" + tablaVerdad[fila][columna] + " ");
                }
                System.out.print(") → f1: ");

                if (scanner.hasNextInt()) {
                    int entrada = scanner.nextInt();
                    if (entrada == 0 || entrada == 1) {
                        tablaVerdad[fila][numeroVariables] = entrada;
                        break;
                    } else {
                        System.out.println("❌ Error: Ingrese solo 0 o 1.");
                    }
                } else {
                    System.out.println("❌ Error: Solo números (0 o 1).");
                    scanner.next();
                }
            }
        }

        // PASO 4: IMPRIMIR LA TABLA DE VERDAD COMPLETA
        System.out.println("\nTabla de Verdad:");
        for (String variable : nombresVariables) {
            System.out.print(variable + "\t");
        }
        System.out.println("f1");

        for (int i = 0; i < numeroVariables; i++) {
            System.out.print("----\t");
        }
        System.out.println("---");

        for (int fila = 0; fila < numeroFilas; fila++) {
            for (int columna = 0; columna <= numeroVariables; columna++) {
                System.out.print(tablaVerdad[fila][columna] + "\t");
            }
            System.out.println();
        }

        // PASO 5: FUNCIÓN BOOLEANA CANÓNICA
        String funcionBooleana = obtenerFuncionCanonica(tablaVerdad, nombresVariables, numeroVariables);
        System.out.println("\nFunción Booleana Canónica: " + funcionBooleana);

        // PASO 6: MAPA DE KARNAUGH
        imprimirMapaKarnaugh(tablaVerdad, numeroVariables, nombresVariables);

        // PASO 7: COMPUESTAS
        contarCompuertas(funcionBooleana);

        scanner.close();
    }

    public static String obtenerFuncionCanonica(int[][] tabla, ArrayList<String> vars, int numVars) {
        String funcion = "";
        boolean primerTermino = true;

        for (int fila = 0; fila < tabla.length; fila++) {
            if (tabla[fila][numVars] == 1) {
                if (!primerTermino) {
                    funcion += " + ";
                }
                for (int columna = 0; columna < numVars; columna++) {
                    if (tabla[fila][columna] == 0) {
                        funcion += vars.get(columna) + "'";
                    } else {
                        funcion += vars.get(columna);
                    }
                }
                primerTermino = false;
            }
        }

        if (funcion.length() == 0) {
            return "0";
        }

        return funcion;
    }

    public static void contarCompuertas(String funcion) {
        int nots = 0;
        int ands = 0;
        int ors = 0;

        String[] terminos = funcion.split("\\+");

        for (String termino : terminos) {
            termino = termino.trim();
            int cantidadAND = termino.length();

            for (char c : termino.toCharArray()) {
                if (c == '\'') {
                    nots++;
                    cantidadAND--;
                }
            }

            if (cantidadAND > 1) {
                ands++;
            }
        }

        ors = (terminos.length > 1) ? 1 : 0;

        int total = nots + ands + ors;

        System.out.println("\n🔌 Compuertas necesarias:");
        System.out.println("NOT (¬): " + nots);
        System.out.println("AND (∧): " + ands);
        System.out.println("OR (∨): " + ors);
        System.out.println("🔢 Total: " + total);
    }

    public static void imprimirMapaKarnaugh(int[][] tabla, int numVars, ArrayList<String> vars) {
        System.out.println("\nMapa de Karnaugh:");
        int[] codigoGray = {0, 1, 3, 2};

        if (numVars == 2) {
            int[][] mapa = new int[2][2];
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    int pos = j * 2 + i;
                    mapa[i][j] = tabla[pos][numVars];
                }
            }

            System.out.println("Mapa para " + vars.get(1) + " (filas) y " + vars.get(0) + " (columnas):");
            System.out.println("\\ " + vars.get(0) + " 0 1");
            System.out.println(vars.get(1) + " \\---");

            for (int i = 0; i < 2; i++) {
                System.out.print(i + " | ");
                for (int j = 0; j < 2; j++) {
                    System.out.print(" " + mapa[i][j] + " ");
                }
                System.out.println();
            }

        } else if (numVars == 3) {
            int[][] mapa = new int[2][4];

            for (int z = 0; z < 2; z++) {
                for (int xy = 0; xy < 4; xy++) {
                    int gray = codigoGray[xy];
                    int x = (gray >> 1) & 1;
                    int y = gray & 1;
                    int pos = x * 4 + y * 2 + z;
                    mapa[z][xy] = tabla[pos][numVars];
                }
            }

            System.out.println("Mapa para " + vars.get(2) + " (filas) y " + vars.get(0) + vars.get(1) + " (columnas):");
            System.out.print("\\ " + vars.get(0) + vars.get(1));
            System.out.println(" 00 01 11 10");
            System.out.println(vars.get(2) + " \\--------");

            for (int z = 0; z < 2; z++) {
                System.out.print(z + " | ");
                for (int xy = 0; xy < 4; xy++) {
                    System.out.print(" " + mapa[z][xy] + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("El mapa de Karnaugh solo está implementado para 2 o 3 variables.");
        }
    }
}

}
