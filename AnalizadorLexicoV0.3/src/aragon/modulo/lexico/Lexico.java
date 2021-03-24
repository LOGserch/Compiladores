package aragon.modulo.lexico;

import aragon.herramientas.ErrorLexico;
import aragon.herramientas.Herramientas;

import java.io.IOException;
import java.util.ArrayList;

public class Lexico {

    private ArrayList<String> codigo;
    private ArrayList<ErrorLexico> errores;
    private ArrayList<Simbolo> simbolos;
    private String[] palabrasReservadas;
    private int numeroLinea;
    private int numeroErrores;
    private int estado;
    private int indiceSimbolos;
    private String tokenReconocidos;
    private Herramientas hr;

    public Lexico() throws ErrorLexico {
        hr = new Herramientas();
        this.numeroLinea =0;
        this.numeroErrores=0;
        this.tokenReconocidos ="";
        this.estado =0;
        this.indiceSimbolos++;
        this.errores =new ArrayList<>();
        this.simbolos = new ArrayList<>();
        try {

            this.codigo =hr.lectura();
            this.crearTablas();
        }catch (IOException ex){
            numeroErrores++;
            this.errores.add(new ErrorLexico("error en el archivo", hr.getColumnaLinea()+1,numeroLinea+1));
            throw new ErrorLexico("Error en el archivo");

        }
    }

    private void crearTablas(){
        this.palabrasReservadas= new String[8];
        this.palabrasReservadas[0]="inicio";
        this.palabrasReservadas[1]="fin";
        this.palabrasReservadas[2]="entero";
        this.palabrasReservadas[3]="real";
        this.palabrasReservadas[4]="mientras";
        this.palabrasReservadas[5]="fin-mientras";
        this.palabrasReservadas[6]="si";
        this.palabrasReservadas[7]="si-no";
    }

    public Token siguienteToken(){
        hr.setLinea(codigo.get(this.numeroLinea));
        char simbolo = hr.siguienteCaracter();
        boolean tokenEncontrado = false;
        Token t = null;
        Simbolo s = null;
        String palabra = "";
        while (numeroLinea < this.codigo.size() && numeroErrores < 5 && !tokenEncontrado){
           switch (this.estado){
               case 0:
                   //System.out.print(simbolo);
                   if(simbolo == ' ' || simbolo == '\t'){
                       this.estado =0;
                       this.numeroLinea++;
                       if(numeroLinea<this.codigo.size()){
                           hr.setLinea(codigo.get(this.numeroLinea));
                           hr.setColumnaLinea(0);
                           simbolo=hr.siguienteCaracter();
                       }else{
                           break;
                       }

                   }else if(simbolo == ':'){
                       this.estado=9;
                   }
                   break;
               case 9:
                   palabra+=simbolo;
                   simbolo=hr.siguienteCaracter();
                   if(simbolo=='='){
                       this.estado=10;
                   }else{
                       this.estado=0;
                       ErrorLexico error = new ErrorLexico("Expresion mal estructurada",
                               hr.getColumnaLinea()+1, numeroLinea+1);
                       this.errores.add(error);
                       this.numeroErrores++;
                   }
                   break;
               case 10:
                   this.estado=0;
                   tokenEncontrado=true;
                   palabra+=simbolo;
                   t=new Token(0,hr.getColumnaLinea(),numeroLinea+1,-1,palabra,palabra);
                   tokenReconocidos+=t.getToken()+"\n";

           }
        }
        return t;
    }

    public ArrayList<ErrorLexico> getErrores() {
        return errores;
    }

    public String getTokenReconocidos() {
        return tokenReconocidos;
    }
}
