/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.codigo;

import java.io.*;

/**
 *
 * @author sergio
 */
public class Inicio {
    private AnalizadorLexico analizador=null;
    private Token token=null;
    //bandera -false->no hay error en instruccion,true-> hay error
    private boolean errorEnLinea=false;
    private void siguienteToken(){
        try {
            token=analizador.yylex();
            if(token==null){
                token=new Token("EOF", Sym.EOF, 0, 0);
                throw new IOException("Fin");
            }
        } catch (IOException ex) {
            System.out.println("Fin de Archivo");
        }        
    }
    public static void main(String[] args) {
        try {
            Inicio app=new Inicio();
            BufferedReader buf;
            buf=new BufferedReader(new FileReader(System.getProperty("user.dir")+
                    "/fuente.txt"));
            app.analizador=new AnalizadorLexico(buf);
            app.siguienteToken();

            while(app.token.getLexema()!=Sym.EOF){
                try {
                    app.S();
                    if ((!app.errorEnLinea) && (!app.token.getToken().equals("\r") && (!app.token.getToken().equals("\n")))){
                        System.out.println("todo bien en linea: "+app.token.getLinea());

                    }else if (!app.token.getToken().equals("\r") && (!app.token.getToken().equals("\n"))){
                        System.out.println("error en linea: "+app.token.getLinea());
                    }
                }catch (IOException ex) {
                    System.out.println(ex.getMessage());

                }
                //System.out.println(app.token.getToken());
                app.siguienteToken();
                //app.errorEnLinea = false;

            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }
    //funciones sintacticas
    private void S() throws IOException{
        //todo: esta funcion es una funcion de inicio para toda gramatica
        //se trata de acceder al Estado no-Terminal E
        //cuando se termine el proceso, al final debe haber como lexema un punto y coma para
        //validar con exito el token
        E();
        if (token.getLexema() != Sym.PUNTOCOMA && token.getLexema() != Sym.SALTOLINEA){
            errorEnLinea =true;
            throw new IOException("Error en el compilador"+" linea "+token.getLinea());
        }else {
            //System.out.println(token.getToken()+"->");
            errorEnLinea=false;
           
        }
    }

    private void E() throws IOException{
        //System.out.println("E->".replaceAll("\n"," "));
        /*
        se accede al no terminal H, dependiendo del lexema siguiente, se podra validar un numero solo
        o por el contrario una operacion aritmetica con algun operador validado por la funcion
        OPERADOR
         */
        H();
        if (token.getLexema() == Sym.SUMA){
            OPERADOR();
            E();
        }else if(token.getLexema() ==Sym.RESTA){
            OPERADOR();
            E();

        }else  if (token.getLexema() == Sym.MULTIPLICACION){
            OPERADOR();
            E();

        }else if (token.getLexema() == Sym.DIVISION){
            OPERADOR();
            E();
        }
    }
    private void OPERADOR() throws IOException{
        //System.out.println("OPERADOR->".replaceAll("\n"," "));
        /*
        esta funcion solo acepta elementos terminales de suma, resta,multiplicacion y division
         */
        if (token.getLexema() == Sym.SUMA){
            //System.out.println(token.getToken());
            siguienteToken();
            if (token.getLexema() == Sym.ESPACIO){
                siguienteToken();
            }
        }
        if (token.getLexema() == Sym.RESTA) {
            //System.out.println(token.getToken());
            siguienteToken();
            if (token.getLexema() == Sym.ESPACIO){
                siguienteToken();
            }
        }
        if (token.getLexema() == Sym.MULTIPLICACION) {
            //System.out.println(token.getToken());
            siguienteToken();
            if (token.getLexema() == Sym.ESPACIO){
                siguienteToken();
            }
        }
        if (token.getLexema() == Sym.DIVISION) {
            //System.out.println(token.getToken());
            siguienteToken();
            if (token.getLexema() == Sym.ESPACIO){
                siguienteToken();
            }
        }

    }


    private void H() throws IOException{
        //esta funcion reconoce and
        //System.out.println("H->".replaceAll("\n"," "));
        /*
        H valida en si los elementos a considerar para la operacion como lo son
        el numero ya sea entero o real, cualquier identificador o un argumento
        encerrado con parentesis
         */
        if ((token.getLexema() ==Sym.NUM_ENTERO)||(token.getLexema() == Sym.NUM_REAL)) {
            //System.out.print(token.getToken());
            siguienteToken();
            if (token.getLexema() == Sym.ESPACIO){
                siguienteToken();
            }
        }
        if (token.getLexema() == Sym.IDENTIFICADOR){
            //System.out.println(token.getToken());
            siguienteToken();
            if (token.getLexema() == Sym.ESPACIO){
                siguienteToken();
            }
        }
        if (token.getLexema() == Sym.PARA){
            //System.out.println(token.getToken());
            siguienteToken();
            if (token.getLexema() == Sym.ESPACIO){
                siguienteToken();
            }
            E();
            if (token.getLexema() == Sym.PARC){
                //System.out.println(token.getToken());
                siguienteToken();
            }
        }


    }
    
}


