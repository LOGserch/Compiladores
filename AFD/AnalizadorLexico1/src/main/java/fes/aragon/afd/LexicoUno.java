/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.afd;
import fes.aragon.herramientas.ErrorLexico;
import fes.aragon.herramientas.Herramientas;
import fes.aragon.herramientas.RangoDeValores;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author sergi
 */
public class LexicoUno {
    public static void main(String[] args) {
        int estado = 1;
        Herramientas hr = new Herramientas();
        ArrayList<String> lineas=null;
        char simbolo=' ';
        int linea=0;
        
        try {
            lineas = hr.lectura();
            try {
                for (int i = 0; i <= lineas.size(); i++) {

                    hr.setPalabra(lineas.get(linea));
                    simbolo = hr.siguienteCaracter();

                    while (simbolo != 32) {
                        switch (estado) {
                            case 1:
                                if (RangoDeValores.letra(simbolo)) {
                                    estado = 3;
                                } else if (RangoDeValores.enterosPositivos(simbolo)) {
                                    estado = 2;
                                } else {
                                    throw new ErrorLexico("Caracter no valido, linea" + (linea + 1));
                                }
                                break;
                            case 2:
                                throw new ErrorLexico("Caracter no valido, linea" + (linea + 1));
                            case 3:
                                if (RangoDeValores.letra(simbolo)) {
                                    estado = 3;
                                } else if (RangoDeValores.enterosPositivos(simbolo)) {
                                    estado = 3;
                                } else {
                                    throw new ErrorLexico("Caracter no valido, linea" + (linea + 1));
                                }
                                break;
                        }
                        simbolo = hr.siguienteCaracter();
                    }
                    if (estado == 3) {
                        System.out.println("variable valida, linea" + (linea + 1));
                    }
                    estado = 1;
                    simbolo = ' ';
                    linea++;
                }
            }catch (ErrorLexico ex){
                System.out.println("variable no valida, linea" + (linea + 1));
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();//imprime el error en consola
            JOptionPane.showMessageDialog(null, "error en el archivo");
        }
        
    }
    
}
