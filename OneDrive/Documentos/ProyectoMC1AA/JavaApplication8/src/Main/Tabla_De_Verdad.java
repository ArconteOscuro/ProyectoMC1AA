package Main;

import java.util.ArrayList;

public class Tabla_De_Verdad {

    private int[][] matriz;
    private ArrayList<String> nombres;
    private int cantidadVars;

    public Tabla_De_Verdad() {
        matriz = null;
        nombres = new ArrayList<>();
        cantidadVars = 0;
    }

    
    public void generarTabla(ArrayList<String> nombres) {
        this.nombres = nombres;
        this.cantidadVars = nombres.size();

        int filas = (int) Math.pow(2, cantidadVars);
        matriz = new int[filas][cantidadVars + 1]; // +1 para la salida

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
    }

    
    public void setSalidas(int[] salidas) {
        int filasEsperadas = (int) Math.pow(2, cantidadVars);

        if (salidas.length != filasEsperadas) {
            throw new IllegalArgumentException("Cantidad de salidas no coincide con la tabla de verdad.");
        }

        for (int i = 0; i < salidas.length; i++) {
            matriz[i][cantidadVars] = salidas[i];
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
