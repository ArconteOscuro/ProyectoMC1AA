package Main;

import java.util.ArrayList;
import javax.swing.JOptionPane;

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
        
        String inputVars = JOptionPane.showInputDialog(null, "¿Cuántas variables vas a usar? (de 2 a 5)");
        if (inputVars == null) return; // Usuario canceló
        try {
            cantidadVars = Integer.parseInt(inputVars);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada no válida.");
            return;
        }

        if (cantidadVars < 2 || cantidadVars > 5) {
            JOptionPane.showMessageDialog(null, "La cantidad debe estar entre 2 y 5.");
            return;
        }

        
        nombres = new ArrayList<>();
        String inputNombres = JOptionPane.showInputDialog(null,
                "Escribe los nombres de las " + cantidadVars + " variables separados por coma (ej: A, B, C):");

        if (inputNombres == null || inputNombres.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Entrada vacía. Operación cancelada.");
            return;
        }

        String[] partes = inputNombres.split(",");
        if (partes.length != cantidadVars) {
            JOptionPane.showMessageDialog(null, "Debes escribir exactamente " + cantidadVars + " nombres.");
            return;
        }

        for (String nombre : partes) {
            nombres.add(nombre.trim());
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

        
        for (int i = 0; i < filas; i++) {
            StringBuilder combinacion = new StringBuilder();
            for (int j = 0; j < cantidadVars; j++) {
                combinacion.append(nombres.get(j)).append("=").append(matriz[i][j]).append(" ");
            }

            String salidaStr = JOptionPane.showInputDialog(null, "¿Cuál es la salida para: " + combinacion.toString().trim() + "? (0 o 1)");
            if (salidaStr == null) return;
            if (!salidaStr.equals("0") && !salidaStr.equals("1")) {
                JOptionPane.showMessageDialog(null, "Solo se permite 0 o 1.");
                i--; 
                continue;
            }
            matriz[i][cantidadVars] = Integer.parseInt(salidaStr);
        }

        JOptionPane.showMessageDialog(null, "¡Tabla ingresada correctamente!");
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