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
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            String rootName = root.getNodeName();

            ArrayList<String> nombres = new ArrayList<>();
            int[][] matriz;

            if (rootName.equalsIgnoreCase("mapa")) {
                int numVars = Integer.parseInt(root.getAttribute("v"));
                for (int i = 1; i <= numVars; i++) {
                    String nombre = root.getAttribute("val" + i);
                    if (nombre == null || nombre.isEmpty()) {
                        nombre = "X" + i;
                    }
                    nombres.add(nombre);
                }

                matriz = new int[(int) Math.pow(2, nombres.size())][nombres.size() + 1];
                NodeList valores = root.getElementsByTagName("valor");
                for (int i = 0; i < valores.getLength(); i++) {
                    Element valor = (Element) valores.item(i);
                    int min = Integer.parseInt(valor.getAttribute("min"));
                    int salida = Integer.parseInt(valor.getTextContent().trim());

                    String bin = String.format("%" + nombres.size() + "s", Integer.toBinaryString(min)).replace(' ', '0');
                    for (int j = 0; j < nombres.size(); j++) {
                        matriz[min][j] = Character.getNumericValue(bin.charAt(j));
                    }
                    matriz[min][nombres.size()] = salida;
                }

            } else if (rootName.equalsIgnoreCase("tabla")) {
                NodeList varList = root.getElementsByTagName("var");
                int numVars = varList.getLength();
                for (int i = 0; i < numVars; i++) {
                    nombres.add(varList.item(i).getTextContent().trim());
                }

                matriz = new int[(int) Math.pow(2, nombres.size())][nombres.size() + 1];
                NodeList filas = root.getElementsByTagName("fila");
                for (int i = 0; i < filas.getLength(); i++) {
                    Element fila = (Element) filas.item(i);
                    String entrada = fila.getAttribute("entrada").trim();
                    int salida = Integer.parseInt(fila.getAttribute("salida").trim());

                    for (int j = 0; j < nombres.size(); j++) {
                        matriz[i][j] = Character.getNumericValue(entrada.charAt(j));
                    }

                    matriz[i][nombres.size()] = salida;
                }

            } else {
                JOptionPane.showMessageDialog(null, "Formato XML no compatible. Se esperaba <mapa> o <tabla>.");
                return false;
            }

            tabla.cargarDesdeXML(nombres, matriz);
            return true;

        } catch (IOException | NumberFormatException | ParserConfigurationException | DOMException | SAXException e) {
            JOptionPane.showMessageDialog(null, "Error leyendo XML: " + e.getMessage());
            return false;
        }
    }
}
