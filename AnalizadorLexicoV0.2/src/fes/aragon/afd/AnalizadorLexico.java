package fes.aragon.afd;
import fes.aragon.herramientas.ErrorLexico;
import fes.aragon.herramientas.Herramientas;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class AnalizadorLexico {
    private final Herramientas hr=new Herramientas();
    private ArrayList<String> lineas=new ArrayList<>();
    private int linea=0;
    private char simbolo=' ';
    private final boolean valido=true;

    public static void main(String[] args) {
        AnalizadorLexico app=new AnalizadorLexico();
        try {
            app.lineas=app.hr.lectura();
            app.hr.setPalabra(app.lineas.get(app.linea));
            app.simbolo=app.hr.siguienteCaracter();
            for (int i=0; i<app.lineas.size(); i++) {
                try {
                    boolean valor = app.estado_A();
                    if(valor){
                        System.out.print("cadena valida, linea"+(app.linea+1));
                    }

                }catch (ErrorLexico ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                app.linea++;
            }

        }catch (IOException ex){
            JOptionPane.showMessageDialog(null, "Error en el archivo");
        }
    }
    private boolean estado_A() throws ErrorLexico{
        switch (simbolo) {
            case 'a' :return estado_B();
            case 'b': return estado_C();
            default : throw new ErrorLexico("caracter no valido, linea:" + (linea + 1));
        }
    }
    private boolean estado_B() throws ErrorLexico{
        char c= hr.siguienteCaracter();
        switch (c) {
            case 'a' : return estado_B();
            case 'b'  :return estado_D();
            default  :throw new ErrorLexico("caracter no valido, linea:" + (linea + 1));
        }
    }
    private boolean estado_C() throws ErrorLexico{
        char c= hr.siguienteCaracter();
        switch (c) {
            case 'a': return estado_B();
            case 'b': return estado_C();
            default: throw new ErrorLexico("caracter no valido, linea:" + (linea + 1));
        }
    }
    private boolean estado_D() throws ErrorLexico{
        char c= hr.siguienteCaracter();
        switch (c) {
            case 'a':return estado_B();
            case 'b':return estado_E();
            default: throw new ErrorLexico("caracter no valido, linea:" + (linea + 1));
        }
    }
    private boolean estado_E() throws ErrorLexico{
        char c= hr.siguienteCaracter();
        switch (c) {
            case 'a':estado_B();
            case 'b':estado_C();
            case ';':return valido;
            default: throw new ErrorLexico("caracter no valido, linea:" + (linea + 1));
        }
    }
}
