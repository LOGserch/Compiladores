/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.codigo;

import java.io.*;

/**
 *
 * @author mash
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
                    if (!app.errorEnLinea){
                        System.out.println("todo bien en linea: "+app.token.getLinea());
                    }else {
                        System.out.println("todo bien en linea: "+app.token.getLinea());
                    }
                }catch (IOException ex) {
                    System.out.println(ex.getMessage());

                }
                //System.out.println(app.token.getToken());
                app.siguienteToken();
                app.errorEnLinea=false;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }
    //funciones sintacticas
    private void S() throws IOException{
        //todo: esta funcion es una funcion de inicio para toda gramatica
        //System.out.println("S->".replaceAll("\n",""));
        System.out.println(token.getToken());
        if (token.getLexema() == Sym.CONDICION_IF) siguienteToken();
        if (token.getLexema() == Sym.PARA){
            System.out.println(token.getToken());
            siguienteToken();
        }
        A();
        if (token.getLexema() == Sym.PARC) {
            System.out.println(token.getToken());
            siguienteToken();
        }
        F();
        B();

        if (token.getLexema() != Sym.TERMINAR){
            errorEnLinea =true;
            throw new IOException("Error en el compilador"+" linea "+token.getLinea());
        }else {
            System.out.println(token.getToken()+"->".replaceAll("\n"," "));
            errorEnLinea=false;
        }
    }
    private void A() throws  IOException{
        //funcion con reconocimiento de condicion dentro de un if
        //System.out.println("A->".replaceAll("\n",""));
        C();
        D();
        C();
    }
    private void B() throws  IOException{
        //funcion con reconocimiento de condicion dentro de un if
        //System.out.println("B->".replaceAll("\n",""));

//        if (token.getLexema() == Sym.TERMINAR){
//            //System.out.println(token.getToken());
//        }
        if (token.getLexema() == Sym.CONDICION_ELSE){
            System.out.println(token.getToken());
            siguienteToken();

            F();

            if (token.getLexema() == Sym.TERMINAR){
                System.out.println(token.getToken());
                siguienteToken();
            }
        }

    }
    private void C() throws  IOException{
        //System.out.println("C->".replaceAll("\n",""));
        //funcion con reconocimiento de condicion dentro de un if
        if (token.getLexema()== Sym.IDENTIFICADOR){
            System.out.println(token.getToken());
            siguienteToken();
        }
        else E();

    }
    private void D() throws  IOException{
        //System.out.println("D->".replaceAll("\n",""));
        System.out.println(token.getToken());
        //funcion con reconocimiento de condicion dentro de un if
        if (token.getLexema() == Sym.IGUAL) siguienteToken();
        if (token.getLexema() == Sym.MENOR) siguienteToken();
        if (token.getLexema() == Sym.MAYOR) siguienteToken();
        if (token.getLexema() == Sym.MAYOR_QUE) siguienteToken();
        if (token.getLexema() == Sym.MENOR_QUE) siguienteToken();
        if (token.getLexema() == Sym.DIFERENTE) siguienteToken();
    }
    private void E() throws IOException{
        //esta funcion reconoce AND
        //System.out.println("E->".replaceAll("\n"," "));

        if (token.getLexema() == Sym.NUM_ENTERO){
            System.out.println(token.getToken());
            siguienteToken();
        }

    }
    private void F() throws IOException {
        //esta funcion reconoce los token NOT,TRUE Y PARENTESIS
        //System.out.println("F->".replaceAll("\n"," "));
        G();
        if (token.getLexema() == Sym.PUNTOCOMA){
            System.out.println(token.getToken());
            siguienteToken();
        }
        else Fprima();

    }
    private void Fprima() throws IOException{
        //esta funcion reconoce and
        //hay que revisar el funcionamiento de esta funcion
        //System.out.println("F prima->".replaceAll("\n"," "));
        if ((token.getLexema() == Sym.ASIGNACION) || (token.getLexema() == Sym.CONDICION_IF)) {
            System.out.println(token.getToken());
            F();
        }


    }
    private void G() throws IOException{
        //esta funcion reconoce and
        //System.out.println("G->".replaceAll("\n"," "));

        if (token.getLexema() == Sym.CONDICION_IF){
            System.out.println(token.getToken());
            S();
        }
        if (token.getLexema() == Sym.IDENTIFICADOR){
            H();
        }


    }
    private void H() throws IOException{
        //esta funcion reconoce and
        //System.out.println("H->".replaceAll("\n"," "));

        if (token.getLexema() == Sym.IDENTIFICADOR){
            System.out.println(token.getToken());
            siguienteToken();
        }
        if (token.getLexema() == Sym.ASIGNACION){
            System.out.println(token.getToken());
            siguienteToken();
        }
        E();

    }
    
}


