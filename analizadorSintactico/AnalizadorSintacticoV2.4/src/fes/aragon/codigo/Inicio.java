/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.codigo;
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
    private final String[] Terminales = {"a", "b", "c", "d", "#"};
    private final String Lambda = "&";
    private final String[][] Tabla = {{"A B", "A B", "error", "error", "error"},
            {"a", "&", "error", "error", "error"},
            {"error", "b C d", "error", "error", "error"},
            {"error", "error", "c", "&", "error"}};
    private Stack<String> pila = new Stack<>();
    private ArrayList<String> cadena = new ArrayList<>();
    private String topePila = "#";
    private String simbolo = "";
    private boolean error = false;


    private void siguienteToken() {
        try {
            this.token = this.analizador.yylex();
            if (this.token == null) {
                this.token = new Token("EOF", Sym.EOF, 0, 0);
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
//            BufferedReader buf = new BufferedReader(new FileReader(System.getProperty("user.dir") +
//                    "/fuente.txt"));
            String filePath = new File("fuente.txt").getAbsolutePath();
            BufferedReader buf = new BufferedReader(new FileReader(filePath));
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

                try {
                    if ((app.pila.lastElement().equals("#")) && (!app.token.getToken().equals(app.topePila))){
                        app.pila.add(app.noTerminales[0]);
                        buf.readLine();
                    }
                    while (!app.pila.lastElement().equals(app.topePila)) {
                        if (app.pila.lastElement().equals(app.token.getToken())) {
                            app.Consumir();
                            app.siguienteToken();
                        }
                        else if (!app.pila.lastElement().equals(app.token.getToken())) {
                            app.siNoEsTerminal(app.pila.lastElement(), app.token.getToken());
                        }
                        else if (app.simbolo.equals("error")) {
                            app.errorEnLinea =true;
                            throw new IOException("Error en el compilador"+" linea "+app.token.getLinea());
                        }
                        else if ((!app.pila.isEmpty()) && (app.token.getToken().equals("#"))){
                            throw new IOException("Error de sintaxis"+" linea "+app.token.getLinea());
                        }

                    }
                    if (app.errorEnLinea) {
                        System.out.println("error en linea: " + app.token.getLinea());

                    } else {
                        if (app.token.getLexema() == Sym.GATITO){
                            System.out.println("todo bien en linea: " + app.token.getLinea());

                        }
                    }
                    //app.pila.add(app.noTerminales[0]);
                    app.siguienteToken();
                    app.errorEnLinea = false;

                }catch (IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }catch (FileNotFoundException ex){
                ex.printStackTrace();
            }
    }
        public void siNoEsTerminal (String noTerminal, String terminal) throws IOException{
        /*
        obtenemos la regla de produccion desde la Tabla de reglas con los indices
        ->columna = Simbolo terminal
        ->fila = simbolo no terminal
         */
            if (terminal.equals(" ")){
                this.siguienteToken();
            }else {
                String regla = this.Tabla[getFila(noTerminal)][getColumna(terminal)];

                if (regla.equals("A B")) {
                    this.pila.pop();
                    AgregarElementos(regla);
                    //return regla;
                }else if (regla.equals("b C d")){
                    this.pila.pop();
                    AgregarElementos(regla);
                    // return regla;
                }else if (regla.equals("error")){
                    this.simbolo=regla;
                }else if(regla.equals("&")){
                    //pila.pop();
                }else if (regla.equals("a")||regla.equals("c")) {
                    this.pila.pop();
                    AgregarElementos(regla);
                }
            }

        }

        //funciones para obtener las filas y columnas correspondientes

    public int getFila (String noTerminal) throws IOException{
        int i;
        for (i =0; i < this.noTerminales.length; i++) {
            if (!this.noTerminales[i].equals(noTerminal)) {
                continue;
            }else break;
        }
        return i;
    }

    public int getColumna (String Terminal) throws IOException{
        int i;
        for (i=0; i < this.Terminales.length; i++) {
            if (!this.Terminales[i].equals(Terminal)) {
                continue;
            }else break;
        }
        return i;
    }

    public void AgregarElementos(String elemento) throws IOException{
        if(elemento.equals("A B")){
            this.pila.add("B");
            this.pila.add("A");

        }else if(elemento.equals("b C d")){
            this.pila.add("d");
            this.pila.add("C");
            this.pila.add("b");
        }else if(elemento.equals("&")){

        }else this.pila.add(elemento);
    }

    public void Consumir(){
        this.pila.pop();
        this.cadena.remove(0);
    }
}



