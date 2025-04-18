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
                System.out.println("XML subido correctamente papu.");
            } else {
                System.out.println("No subiste correctamente papu el XML.");
                return;
            }
        } else {
            tabla.entradaManual();
        }

        // Se genera la tabla de verdad
        tabla.imprimir();

        // Acá se genera la función booleana canónica
        String canonica = Simplificador.obtenerFuncionCanonica(
            tabla.getMatriz(),
            tabla.getNombres(),
            tabla.getCantidadVars()
        );
        System.out.println("\n🧮 Función Canónica:\n" + canonica);

        // Se simplifica la ecuación va
        String simplificada = Simplificador.simplificar(
            tabla.getMatriz(),
            tabla.getCantidadVars(),
            tabla.getNombres()
        );
        System.out.println("\n🧪 Función Simplificada:\n" + simplificada);

        // Se hace el calculo de compuertas necesarias para el circuito
        Simplificador.contarCompuertas(simplificada);

        scanner.close();
    }
}

