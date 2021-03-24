/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aragon.herramientas;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author sergi
 */
public class Herramientas {
 
    private int longitudLinea =0;
    private int columnaLinea =0;
    private String linea;
    
    public Herramientas(){
    }
    public ArrayList<String> lectura() throws FileNotFoundException, IOException{
    //guarda lineas de lo que se lee en el archivo
        JFileChooser archivo = new JFileChooser(System.getProperty("user.dir"));//ruta de trabajo desde el directorio
        archivo.setMultiSelectionEnabled(false);//agregar solo un archivo
        
        //filtro para documentos solo de texto
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo de texto", "txt");
        archivo.setFileFilter(filtro);
        
        ArrayList<String> lineas = new ArrayList<>();
        int seleccion = archivo.showOpenDialog(null);
        if(seleccion == JFileChooser.APPROVE_OPTION){
            File f= archivo.getSelectedFile();
            FileReader fr = new FileReader(f);
            BufferedReader bf = new BufferedReader(fr);
            String cad ="";//para leer linea a linea
            //para leer linea a linea
            while((cad = bf.readLine()) != null){//mientras cadena sea diferente de nulo
                lineas.add(cad);//agregamos a lineas lo que se lee
            }
            //cerramos los archivos
            bf.close();
            fr.close();
            }else{
                System.exit(0);//para que no marque alguna excepcion mas adelante
        }
        //retomamos lineas que leemos
        return lineas;
    }
    //metodo para que me de las palabras
    
    public void setLinea(String linea){
        this.linea = linea;
        this.longitudLinea = linea.length();
    }
    
    public char siguienteCaracter(){
        char c = ' ';
        if(columnaLinea < longitudLinea){
            c= linea.charAt(columnaLinea);
            columnaLinea++;
        }
        return c;
    }

    public int getColumnaLinea() {
        return columnaLinea;
    }

    public void setColumnaLinea(int columnaLinea) {
        this.columnaLinea = columnaLinea;
    }
}
