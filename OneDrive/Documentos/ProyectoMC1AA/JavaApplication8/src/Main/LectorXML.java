// En este archivo se podrá leer archivos xml si se suben y subir los datos de la tabla de la true xd

package Main;

import org.w3c.dom.*; 
import javax.xml.parsers.*; 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.xml.sax.SAXException;

public class LectorXML {

    // Este método es el que se encarga de leer todo el XML y llenar la tabla
    public static boolean cargarDesdeXML(String rutaArchivo, Tabla_De_Verdad tabla) {
        try {
            // Acá se crea un objeto tipo con la ruta dada
            File archivo = new File(rutaArchivo);

            // Estas líneas son las necesarias para que el archivo XML se pueda leer
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo); // Se "abre" el archivo
            doc.getDocumentElement().normalize();  // Esto lo vi en internet que ayuda por si hay espacios raros

            // Va, se leen las variables de nombre tipo A, B, C...
            NodeList vars = doc.getElementsByTagName("var");
            ArrayList<String> nombres = new ArrayList<>();
            for (int i = 0; i < vars.getLength(); i++) {
                nombres.add(vars.item(i).getTextContent()); // Se guarda cada nombre en una lista
            }

            int numVars = nombres.size(); // Contamos cuántas variables hay, ya sean 2, 3, 4 o 5
            
            // Se crea la tabla 
            int[][] matriz = new int[(int) Math.pow(2, numVars)][numVars + 1];

            // Leemos las filas del xml, cada una tiene una entrada y una salida
            NodeList filas = doc.getElementsByTagName("fila");
            for (int i = 0; i < filas.getLength(); i++) {
                Element fila = (Element) filas.item(i);
                String entrada = fila.getAttribute("entrada"); // Se ponen las partes del mapa como 001, 111...
                int salida = Integer.parseInt(fila.getAttribute("salida")); // El resultado de esa entrada

                // Recorremos cada carácter de la entrada para ponerlo como número en la tabla
                for (int j = 0; j < numVars; j++) {
                    // Convertimos cada caracter a número de 0 o 1 a 0 o 1
                    matriz[i][j] = Character.getNumericValue(entrada.charAt(j));
                }

                // Guardamos el resultado al final de la fila
                matriz[i][numVars] = salida;
            }

            // Hacemos que la clase guarde la tabla
            tabla.cargarDesdeXML(nombres, matriz); 

            return true; // Si todo salió gud, devolvemos el simón (true)

        } catch (IOException | NumberFormatException | ParserConfigurationException | DOMException | SAXException e) {
            // Si la carlitos, ponemos un mensaje para saber que puchis xdxd
            System.out.println("❌ Error leyendo XML: " + e.getMessage());
            return false; // Y decimos que no la hicimos bien
        }
    }
}

