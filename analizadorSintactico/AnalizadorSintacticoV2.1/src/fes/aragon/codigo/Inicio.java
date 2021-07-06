/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.fes.aragon.codigo;

import jdk.swing.interop.SwingInterOpUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        System.out.println("S->".replaceAll("\n",""));
        E();
        if (token.getLexema() !=Sym.PUNTOCOMA){
            errorEnLinea =true;
            throw new IOException("Error en el compilador"+token.getLinea());
        }else {
            System.out.println(token.getToken()+"->".replaceAll("\n"," "));
            errorEnLinea=false;
        }
    }
    private void E() throws IOException{
        System.out.println("E->".replaceAll("\n"," "));
        T();
        if (token.getLexema()==Sym.AND){
            System.out.println(token.getToken()+"->".replaceAll("\n"," "));
            siguienteToken();
            E();
        }
    }
    private void T() throws IOException{
        System.out.println("T->".replaceAll("\n"," "));
        F();
        if (token.getLexema()==Sym.AND){
            System.out.println(token.getToken()+"->".replaceAll("\n"," "));
            siguienteToken();
            T();
        }
    }
    private void F() throws IOException {
        System.out.println("F->".replaceAll("\n"," "));
        if (token.getLexema() == Sym.NOT){
            System.out.println(token.getToken()+"->".replaceAll("\n"," "));
            siguienteToken();
            F();
        }else if ((token.getLexema()==Sym.TRUE)||(token.getLexema()==Sym.FALSE)){
            System.out.println(token.getToken()+"->".replaceAll("\n"," "));
            siguienteToken();

        }else if (token.getLexema()==Sym.PARA){
            System.out.println(token.getToken()+"->".replaceAll("\n"," "));
            siguienteToken();
            E();
            if (token.getLexema()==Sym.PARC){
                System.out.println(token.getToken()+"->".replaceAll("\n"," "));
                siguienteToken();
            }
        }else {
            errorEnLinea=true;
            throw new IOException("Error en el compilador"+token.getLinea());
        }
    }
    
}


