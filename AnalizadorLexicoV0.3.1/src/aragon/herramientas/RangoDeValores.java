/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aragon.herramientas;

/**
 *
 * @author sergi
 * metodos para validar enteros y strings para mi automata de validacion
 * por medio de la tabla de los caracteres ASCII
 */
public class RangoDeValores {
   public static boolean enterosPositivos(char entero){
       boolean valido = false;
       if((entero>=48) && (entero<=57)){
           valido = true;
       }
       return valido;
   } 


    public static boolean letra(char letra){
        boolean valido=false;
        if((letra>=65) && (letra<=90) || (letra>=97) && (letra<=122) ){
            valido = true;
        }
        return valido;
    }

    public static boolean CaracteresEspecificos(char caracter){
       boolean valido = false;
       if((caracter>=40)&&(caracter<= 47)||(caracter>=58)&&(caracter<=61)){
           valido = true;
       }
       return valido;
    }

    public static boolean finCadena(char caracter){
       boolean valido = false;
       if(caracter == 59){
           valido = true;
       }
       return valido;
    }
}