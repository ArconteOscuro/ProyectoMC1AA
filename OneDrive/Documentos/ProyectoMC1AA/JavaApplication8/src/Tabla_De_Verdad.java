// Este archivo se llama Tabla_De_Verdad.java, así que la clase también debe llamarse igual (Ya la regue cuando me confundí xd con una mayuscula y no me corria)

import java.util.ArrayList;
import java.util.Scanner;

public class Tabla_De_Verdad {

    private int[][] matriz; // Aquí voy a guardar la tabla como tal
    private ArrayList<String> nombres; // Aquí pongo cómo se llaman las variables
    private int cantidadVars; // Para saber cuántas variables vamos a usar

    public Tabla_De_Verdad() {
        matriz = null;
        nombres = new ArrayList<>();
        cantidadVars = 0;
    }

    public void entradaManual() {
        Scanner scanner = new Scanner(System.in);

        // Pregunto cuántas variables va a querer el usuario
        System.out.print("Cuántas variables va a usar (mínimo 2 y máximo 5): ");
        cantidadVars = scanner.nextInt();
        scanner.nextLine(); // Pongo esto xd en el video del yiutu vi que se ponía

        // Validación, para que este condicionado xd
        if (cantidadVars < 2 || cantidadVars > 5) {
            System.out.println("Número no válido, debe ser entre 2 y 5.");
            return;
        }

        // Aquí se colocan los nombres de cada variable
        for (int i = 0; i < cantidadVars; i++) {
            System.out.print("Nombre de la variable " + (i + 1) + ": ");
            String nombre = scanner.nextLine();
            nombres.add(nombre);
        }

        int filas = (int) Math.pow(2, cantidadVars); // Total de combinaciones
        matriz = new int[filas][cantidadVars + 1]; // La +1 es para la salida (f1)

        // Lleno las combinaciones de 0 y 1 para cada variable
        for (int col = 0; col < cantidadVars; col++) {
            int contar = 0;
            int valor = 0;
            int cambioCada = (int) Math.pow(2, cantidadVars - col - 1);

            for (int fila = 0; fila < filas; fila++) {
                matriz[fila][col] = valor;
                contar++;
                if (contar == cambioCada) {
                    valor = (valor == 0) ? 1 : 0; // cambio de 0 a 1 o de 1 a 0
                    contar = 0;
                }
            }
        }

        // Ahora se piden los valores de salida para cada combinación
        System.out.println("\nAhora ingrese los valores de salida (solo 0 o 1):");

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
                        System.out.println("Solo se acepta 0 o 1, prueba de nuevo.");
                    }
                } else {
                    System.out.println("Eso no es un número válido (debe ser 0 o 1).");
                    scanner.next(); // Limpia lo que metieron mal
                }
            }
        }
    }

    // Devuelve la tabla generada
    public int[][] getMatriz() {
        return matriz;
    }

    // Devuelve los nombres de las variables que metió el usuario
    public ArrayList<String> getNombres() {
        return nombres;
    }

    // Devuelve cuántas variables se están usando
    public int getCantidadVars() {
        return cantidadVars;
    }

    // Muestra la tabla completita
    public void imprimir() {
        System.out.println("\nAquí va la tabla de verdad:");

        // Encabezados
        for (String nombre : nombres) {
            System.out.print(nombre + "\t");
        }
        System.out.println("f1");

        // Línea de separación
        for (int i = 0; i < cantidadVars; i++) {
            System.out.print("----\t");
        }
        System.out.println("----");

        // Datos
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j <= cantidadVars; j++) {
                System.out.print(matriz[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
