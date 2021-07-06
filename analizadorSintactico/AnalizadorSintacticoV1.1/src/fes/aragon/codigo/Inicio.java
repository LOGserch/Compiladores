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
                        System.out.println("todo bien en"+app.token.getLinea());
                    }else {
                        System.out.println("todo bien en"+app.token.getLinea());
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
        E();
        if (token.getLexema() !=Sym.PUNTOCOMA){
            errorEnLinea =true;
            throw new IOException("Error en el compilador"+token.getLinea());
        }else {
            errorEnLinea=false;
        }
    }
    private void E() throws IOException{
        T();
        if (token.getLexema()==Sym.AND){
            siguienteToken();
            E();
        }
    }
    private void T() throws IOException{
        F();
        if (token.getLexema()==Sym.AND){
            siguienteToken();
            T();
        }
    }
    private void F() throws IOException {
        if (token.getLexema() == Sym.NOT){
            siguienteToken();
            F();
        }else if ((token.getLexema()==Sym.TRUE)||(token.getLexema()==Sym.FALSE)){
            siguienteToken();

        }else if (token.getLexema()==Sym.PARA){
            siguienteToken();
            E();
            if (token.getLexema()==Sym.PARC){
                siguienteToken();
            }
        }else {
            errorEnLinea=true;
            throw new IOException("Error en el compilador"+token.getLinea());
        }
    }
    
}


