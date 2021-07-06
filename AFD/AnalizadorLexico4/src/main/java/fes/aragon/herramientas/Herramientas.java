/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.herramientas;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author sergi
 */
public class Herramientas {
 
    private int longitudPalabra=0;
    private int indicePalabra=0;
    private String palabra;
    
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
            }
        //retomamos lineas que leemos
        return lineas;
    }
    //metodo para que me de las palabras
    
    public void setPalabra(String palabra){
        this.palabra = palabra;
        this.longitudPalabra=palabra.length();
        this.indicePalabra=0;
    }
    
    public char siguienteCaracter(){
        char c = ' ';
        if(indicePalabra<longitudPalabra){
            c=palabra.charAt(indicePalabra);
            indicePalabra++;
        }
        return c;
    }
}
