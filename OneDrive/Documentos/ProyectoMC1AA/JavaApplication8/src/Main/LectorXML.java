package Main;

import org.w3c.dom.*; 
import javax.xml.parsers.*; 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.xml.sax.SAXException;

public class LectorXML {

    
    public static boolean cargarDesdeXML(String rutaArchivo, Tabla_De_Verdad tabla) {
        try {
            File archivo = new File(rutaArchivo);

            // Configura el lector de XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            
            NodeList vars = doc.getElementsByTagName("var");
            ArrayList<String> nombres = new ArrayList<>();
            for (int i = 0; i < vars.getLength(); i++) {
                nombres.add(vars.item(i).getTextContent().trim());
            }

            int numVars = nombres.size();
            int[][] matriz = new int[(int) Math.pow(2, numVars)][numVars + 1];

           
            NodeList filas = doc.getElementsByTagName("fila");
            for (int i = 0; i < filas.getLength(); i++) {
                Element fila = (Element) filas.item(i);
                String entrada = fila.getAttribute("entrada").trim();
                int salida = Integer.parseInt(fila.getAttribute("salida").trim());

                for (int j = 0; j < numVars; j++) {
                    matriz[i][j] = Character.getNumericValue(entrada.charAt(j));
                }

                matriz[i][numVars] = salida;
            }

         
            tabla.cargarDesdeXML(nombres, matriz);
            return true;

         } catch (IOException | NumberFormatException | ParserConfigurationException | DOMException | SAXException e) {
            System.out.println("Error leyendo XML: " + e.getMessage());
            return false;
        }
    }
}


