package Main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Simplificador {

    //  Forma canónica
    public static String obtenerFuncionCanonica(int[][] tabla, ArrayList<String> nombres, int cantidadVars) {
        StringBuilder canonica = new StringBuilder();
        boolean primero = true;

        for (int i = 0; i < tabla.length; i++) {
            if (tabla[i][cantidadVars] == 1) {
                if (!primero) canonica.append(" + ");
                for (int j = 0; j < cantidadVars; j++) {
                    canonica.append(tabla[i][j] == 0 ? nombres.get(j) + "'" : nombres.get(j));
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

            ArrayList<String> lista = new ArrayList<>(implicantes);
            for (int i = 0; i < lista.size(); i++) {
                for (int j = i + 1; j < lista.size(); j++) {
                    String a = lista.get(i);
                    String b = lista.get(j);
                    int dif = 0, pos = -1;
                    for (int k = 0; k < a.length(); k++) {
                        if (a.charAt(k) != b.charAt(k)) {
                            dif++;
                            pos = k;
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
            if (term.length() > 0) resultado.add(term.toString());
        }

        return resultado.isEmpty() ? "1" : String.join(" + ", resultado);
    }

    
    public static String contarCompuertas(String expresion) {
        int not = 0, and = 0, or;

        String[] terminos = expresion.split("\\+");
        for (String termino : terminos) {
            int letras = 0;
            char[] chars = termino.trim().toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (Character.isLetter(chars[i])) letras++;
                if (i + 1 < chars.length && chars[i + 1] == '\'') {
                    not++;
                }
            }
            if (letras > 1) and += letras - 1;
        }

        or = terminos.length > 1 ? terminos.length - 1 : 0;
      
   

        return "\n Compuertas necesarias:\n"
             + "NOT (¬): " + not + "\n"
             + "AND (∧): " + and + "\n"
             + "OR  (∨): " + or + "\n"
             + "Total: " + (not + and + or);
    }
}
