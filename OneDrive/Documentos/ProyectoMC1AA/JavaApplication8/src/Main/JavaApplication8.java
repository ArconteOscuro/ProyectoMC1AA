package Main;

import java.util.ArrayList;
import java.util.Scanner;

public class JavaApplication8 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Tabla_De_Verdad tabla = new Tabla_De_Verdad();

        // Acá se hace la pregunta inicial, es sobre si se desea subir un archivo xml
        System.out.print("¿Quéres subir archivo XML? (si/no): ");
        String opcion = scanner.nextLine().trim().toLowerCase();

        // Donde se lee o no el archivo XML, si no se hace manual
        if (opcion.equals("si")) {
            System.out.print("Pone la ruta del archivo (ejemplo: C:\\\\Usuario\\\\TuUsuario\\\\archivo.xml): ");
            String ruta = scanner.nextLine();

            if (LectorXML.cargarDesdeXML(ruta, tabla)) {
                System.out.println("Se subió correctamente el XML.");
            } else {
                System.out.println("No se subió correctamente el XML.");
                return;
            }
        } else {
            tabla.entradaManual();
        }

        // Donde se imprime la tabla de verdad
        tabla.imprimir();

        // Se le habla a Mapita xdxd
        Mapita.imprimirMapaKarnaugh(
            tabla.getMatriz(),
            tabla.getCantidadVars(),
            tabla.getNombres()
        );

        // Donde se da la función canonica, se hace canon de golpe
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

        // Se cuentan las funciones simplificadas 
        Simplificador.contarCompuertas(simplificada);

        scanner.close();
    }
}
