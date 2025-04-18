package Main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Simplificador {

    public static String obtenerFuncionCanonica(int[][] tabla, ArrayList<String> nombres, int cantidadVars) {
        StringBuilder canonica = new StringBuilder();
        boolean primero = true;

        for (int i = 0; i < tabla.length; i++) {
            if (tabla[i][cantidadVars] == 1) {
                if (!primero) canonica.append(" + ");
                for (int j = 0; j < cantidadVars; j++) {
                    canonica.append((tabla[i][j] == 0) ? nombres.get(j) + "'" : nombres.get(j));
                }
                primero = false;
            }
        }

        return canonica.length() == 0 ? "0" : canonica.toString();
    }

    public static String simplificar(int[][] tabla, int cantidadVars, ArrayList<String> nombres) {
        ArrayList<String> minterms = new ArrayList<>();
        for (int[] fila : tabla) {
            if (fila[cantidadVars] == 1) {
                StringBuilder bin = new StringBuilder();
                for (int j = 0; j < cantidadVars; j++) {
                    bin.append(fila[j]);
                }
                minterms.add(bin.toString());
            }
        }

        Set<String> implicantes = new HashSet<>(minterms);
        boolean cambio;

        do {
            cambio = false;
            Set<String> siguiente = new HashSet<>();
            Set<String> usados = new HashSet<>();

            for (String a : implicantes) {
                for (String b : implicantes) {
                    if (a.equals(b)) continue;

                    int dif = 0, pos = -1;
                    for (int i = 0; i < a.length(); i++) {
                        if (a.charAt(i) != b.charAt(i)) {
                            dif++;
                            pos = i;
                        }
                    }

                    if (dif == 1) {
                        StringBuilder combinado = new StringBuilder(a);
                        combinado.setCharAt(pos, '-');
                        siguiente.add(combinado.toString());
                        usados.add(a);
                        usados.add(b);
                        cambio = true;
                    }
                }
            }

            for (String imp : implicantes) {
                if (!usados.contains(imp)) siguiente.add(imp);
            }

            implicantes = siguiente;

        } while (cambio);

        ArrayList<String> resultado = new ArrayList<>();
        for (String imp : implicantes) {
            StringBuilder term = new StringBuilder();
            for (int i = 0; i < imp.length(); i++) {
                if (imp.charAt(i) == '-') continue;
                term.append(imp.charAt(i) == '1' ? nombres.get(i) : nombres.get(i) + "'");
            }
            resultado.add(term.toString());
        }

        return resultado.isEmpty() ? "0" : String.join(" + ", resultado);
    }

    public static void contarCompuertas(String expresion) {
        int not = 0, and = 0, or;

        String[] terminos = expresion.split("\\+");
        for (String termino : terminos) {
            int letras = 0;
            for (char c : termino.trim().toCharArray()) {
                if (Character.isLetter(c)) letras++;
                if (c == '\'') not++;
            }
            if (letras > 1) and += letras - 1;
        }

        or = terminos.length > 1 ? terminos.length - 1 : 0;

        System.out.println("\n🔌 Compuertas necesarias:");
        System.out.println("NOT (¬): " + not);
        System.out.println("AND (∧): " + and);
        System.out.println("OR  (∨): " + or);
        System.out.println("Total: " + (not + and + or));
    }
}
