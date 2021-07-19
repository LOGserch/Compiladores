/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.codigo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.io.*;

/**
 *
 * @author Sergio
 */
public class Inicio {
    private static String Terminal;
    private AnalizadorLexico analizador = null;
    private Token token = null;
    private boolean errorEnLinea = false;//bandera -false->no hay error en instruccion,true-> hay error
    /*
    se requiere por supuesto de una tabla con todos los elementos terminales y no terminales
    representados primero:

    -como fila noTerminales
    -como columna Terminales
    -signo lambda representa la transicion lambda
    todo-> Analisis sintactico predictivo no recursivo -algoritmo
     */

    private final String[] noTerminales = {"S", "A", "B", "C"};
    private final String[] Terminales = {"a", "b", "c", "d","#"};
    private final String Lambda = "&";
    private final String[][] Tabla = {{"A B", "A B", "error", "error", "error"},
            {"a", "&", "error", "error", "error"},
            {"error", "b C d ", "error", "error", "error"},
            {"error", "error", "c", "&", "error"}};
    private Stack<String> pila = new Stack<>();
    private ArrayList<String> cadena = new ArrayList<>();
    private String topePila = "#";
    private String simbolo = "";
    private boolean error = false;


    private void siguienteToken() {
        try {
            token = analizador.yylex();
            if (token == null) {
                token = new Token("EOF", Sym.EOF, 0, 0);
                throw new IOException("Fin");
            }
        } catch (IOException ex) {
            System.out.println("Fin de Archivo");
        }
    }

    public static void main(String[] args) throws IOException {
        //app.cadena.addAll(Arrays.asList(app.Terminales));
        boolean value = false;
        try {

            //bloque de apertura para la lectura del documento de texto

            Inicio app = new Inicio();
            BufferedReader buf = new BufferedReader(new FileReader(System.getProperty("user.dir") +
                    "/fuente.txt"));
            app.analizador = new AnalizadorLexico(buf);
            app.cadena.addAll(Arrays.asList(app.Terminales));
            //System.out.println(app.cadena.get(0));

            /*
            primero se agregan los elementos iniciales a la pila de simbolos, es decir, se va
            a agregar primero # y despues S, nuetro simbolo de partida
             */
            app.pila.add(app.topePila);
            app.pila.add(app.noTerminales[0]);
            app.siguienteToken();
            //System.out.println(app.pila.lastElement());

            while (app.token.getLexema() != Sym.EOF) {
                /*
                try {

                }catch (IOException ex){
                    System.out.println(ex.getMessage());
                }

                 */

                //empezamos transiciones
                while (!app.pila.lastElement().equals(app.topePila)) {
                    if (app.pila.lastElement().equals(app.token.getToken())) {
                        app.pila.pop();
                        app.cadena.remove(0);
                        app.siguienteToken();
                    } else if (!app.pila.lastElement().equals(app.token.getToken())) {
                        app.simbolo = app.siNoEsTerminal(app.pila.lastElement(), app.token.getToken());
                        /*
                         aqui nose especificamente si hay que pasar el lexema o el token, aunque difiero
                        en que en definitiva es el token el que se le debe de pasar para la comparacion
                         */
                        app.pila.pop();
                        app.pila.add(app.simbolo);
                    }
                    if (app.simbolo.equals("error")) {
                        app.errorEnLinea =true;
                        throw new IOException("Error en el compilador"+" linea "+app.token.getLinea());
                    }

                    // else if (app.pila.equals())

                }
                if (!app.errorEnLinea) {
                    System.out.println("todo bien en linea: " + app.token.getLinea());

                } else {
                    System.out.println("error en linea: " + app.token.getLinea());
                }
                /*
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    *

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
             */
                app.siguienteToken();
                app.errorEnLinea = false;

            }
        }catch (FileNotFoundException ex){
                ex.printStackTrace();
            }
    }
        public String siNoEsTerminal (String noTerminal, String terminal) throws IOException{
        /*
        obtenemos la regla de produccion desde la Tabla de reglas con los indices
        ->columna = Simbolo terminal
        ->fila = simbolo no terminal
         */
            return Tabla[getFila(noTerminal)][getColumna(terminal)];
        }

        //funciones para obtener las filas y columnas correspondientes

    public int getFila (String noTerminal) throws IOException{
        int contador=0;
        for (int i =0; i <= noTerminales.length; i++) {
            if (noTerminales[i] == noTerminal) {
                noTerminal = noTerminales[i];
            } else contador++;
        }
        return contador;
    }

    public int getColumna (String Terminal) throws IOException{
        int contador=0;
        for (int i =0; i <= Terminales.length; i++) {
            if (Terminales[i] == Terminal) {
                Terminal = Terminales[i];
            }else contador++;
        }
        return contador;
    }

    public void AgregarElementos(String elemento){
        if(elemento.equals("A B")){
            pila.add("B");
            pila.add("A");

        }else if(elemento.equals("b C d")){
            pila.add("d");
            pila.add("C");
            pila.add("b");
        }
    }
}



