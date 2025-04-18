package Main;

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

        // Se imprime el mapa de Karnaugh
        Mapita.imprimirMapaKarnaugh(
            tabla.getMatriz(),
            tabla.getCantidadVars(),
            tabla.getNombres()
        );

        scanner.close();
    }
}
