package Main;

import java.util.ArrayList;
import java.util.Scanner;

public class Tabla_De_Verdad {

    private int[][] matriz;
    private ArrayList<String> nombres;
    private int cantidadVars;

    public Tabla_De_Verdad() {
        matriz = null;
        nombres = new ArrayList<>();
        cantidadVars = 0;
    }

    public void entradaManual() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("¿Cuántas variables vas a usar, recordá que son de 2-5: ");
        cantidadVars = scanner.nextInt();
        scanner.nextLine();

        if (cantidadVars < 2 || cantidadVars > 5) {
            System.out.println("Número no válido, debe ser entre 2 y 5.");
            return;
        }

        for (int i = 0; i < cantidadVars; i++) {
            System.out.print("Nombre que le vas a poner a la variable " + (i + 1) + ": ");
            String nombre = scanner.nextLine();
            nombres.add(nombre);
        }

        int filas = (int) Math.pow(2, cantidadVars);
        matriz = new int[filas][cantidadVars + 1];

        for (int col = 0; col < cantidadVars; col++) {
            int contar = 0, valor = 0;
            int cambioCada = (int) Math.pow(2, cantidadVars - col - 1);

            for (int fila = 0; fila < filas; fila++) {
                matriz[fila][col] = valor;
                contar++;
                if (contar == cambioCada) {
                    valor = (valor == 0) ? 1 : 0;
                    contar = 0;
                }
            }
        }

        System.out.println("\nAhora pone los valores de salida, recordá solo 0 o 1:");

        for (int i = 0; i < filas; i++) {
            while (true) {
                System.out.print("Para (");
                for (int j = 0; j < cantidadVars; j++) {
                    System.out.print(nombres.get(j) + "=" + matriz[i][j]);
                    if (j != cantidadVars - 1) System.out.print(", ");
                }
                System.out.print(") → f1: ");

                if (scanner.hasNextInt()) {
                    int salida = scanner.nextInt();
                    if (salida == 0 || salida == 1) {
                        matriz[i][cantidadVars] = salida;
                        break;
                    } else {
                        System.out.println("Solo se acepta 0 o 1.");
                    }
                } else {
                    System.out.println("Eso no es un número válido (0 o 1).");
                    scanner.next();
                }
            }
        }
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public ArrayList<String> getNombres() {
        return nombres;
    }

    public int getCantidadVars() {
        return cantidadVars;
    }

    public void cargarDesdeXML(ArrayList<String> nombres, int[][] matriz) {
        this.nombres = nombres;
        this.matriz = matriz;
        this.cantidadVars = nombres.size();
    }

    public void imprimir() {
        System.out.println("\nAcá va la tabla de verdad:");
        for (String nombre : nombres) {
            System.out.print(nombre + "\t");
        }
        System.out.println("f1");

        for (int i = 0; i < cantidadVars; i++) {
            System.out.print("----\t");
        }
        System.out.println("----");

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j <= cantidadVars; j++) {
                System.out.print(matriz[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
