package aragon.modulo.lexico;

import aragon.herramientas.ErrorLexico;
import aragon.herramientas.Herramientas;
import aragon.herramientas.RangoDeValores;
import java.io.IOException;
import java.util.ArrayList;

public class Lexico {

    private final ArrayList<String> codigo;
    private final ArrayList<ErrorLexico> errores;
    private final ArrayList<Simbolo> simbolos;
    private String[] palabrasReservadas;
    private int numeroLinea;
    private int numeroErrores;
    private int estado;
    private int indiceSimbolos;
    private String tokenReconocidos;
    private final Herramientas hr;//con final,obtenemos una constante
    private char simbolo;

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
            hr.setLinea(codigo.get(this.numeroLinea));
            simbolo = hr.siguienteCaracter();
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

        boolean tokenEncontrado = false;
        Token t = null;
        Simbolo s = null;
        String palabra = "";
        while (numeroLinea < this.codigo.size() && numeroErrores < 5 && !tokenEncontrado){
           switch (this.estado){
               case 0:
                   //System.out.print(simbolo);
                   if(simbolo == ' ' || simbolo == '\t'){
                       this.numeroLinea++;
                       if(numeroLinea < this.codigo.size()){
                           hr.setLinea(codigo.get(this.numeroLinea));
                           hr.setColumnaLinea(0);
                           simbolo=hr.siguienteCaracter();
                       }else{
                           break;
                       }

                   }else if(simbolo == ':'){
                       this.estado=9;
                   }else if(RangoDeValores.letra(simbolo)){
                       this.estado=1;

                   }else if(RangoDeValores.enterosPositivos(simbolo)){
                       this.estado=3;
                   }
                   /*
                   else if(simbolo == '('){
                       this.estado=11;
                   }else if (simbolo == ')'){
                       this.estado=12;
                     }
                    */

                   else if(RangoDeValores.CaracteresEspecificos(simbolo)){
                       this.estado=8;
                   }else if(simbolo == '!'){
                       this.estado=13;
                   }else if(simbolo == '<'){
                       this.estado=20;
                   }else if(simbolo == '>'){
                       this.estado=17;
                   }
                   /*
                   else if(simbolo == '+'){
                       this.estado=23;
                   }else if(simbolo == '-'){
                       this.estado=24;
                   }else if(simbolo == '*'){
                       this.estado=25;
                   }else if(simbolo == '/'){
                       this.estado=26;
                   }else if(simbolo == '='){
                       this.estado=16;
                   }

                    */
                   break;
               case 1:
                   do {
                       if(RangoDeValores.letra(simbolo) || RangoDeValores.enterosPositivos(simbolo)){
                           palabra+=simbolo;
                       }
                       simbolo=hr.siguienteCaracter();
                   }while (RangoDeValores.letra(simbolo) || RangoDeValores.enterosPositivos(simbolo));
                   this.estado=2;
                   break;
               case 2:
               case 7:
               case 4:
                   this.estado=0;
                   tokenEncontrado=true;
                   t = identificadorAsignarId(palabra);
                   //System.out.println(t);
                   //simbolo=hr.siguienteCaracter();
                   break;
               case 3:
                   do {
                       if(RangoDeValores.enterosPositivos(simbolo)){
                           palabra+=simbolo;
                       }
                       simbolo=hr.siguienteCaracter();
                   }while (RangoDeValores.enterosPositivos(simbolo));
                   if (simbolo =='.'){
                      this.estado=5;
                   }
                   else {
                       this.estado=4;
                   }
                   break;
               case 5:
                   palabra+=simbolo;
                   simbolo=hr.siguienteCaracter();
                   if (RangoDeValores.enterosPositivos(simbolo)){
                       this.estado=6;
                   }else {
                       this.estado=0;
                       ErrorLexico error = new ErrorLexico("Expresion mal estructurada",
                               hr.getColumnaLinea()+1, numeroLinea+1);
                       this.errores.add(error);
                       this.numeroErrores++;
                   }
                   break;
               case 6:
                   do {
                       if (RangoDeValores.enterosPositivos(simbolo)){
                           palabra+=simbolo;
                       }
                       simbolo=hr.siguienteCaracter();
                   }while (RangoDeValores.enterosPositivos(simbolo));
                   this.estado=7;
                   break;
               case 8:
               case 10:
               case 14:
               case 16:
               case 18:
               case 19:
               case 21:
               case 22:
               case 12:
                   this.estado=0;
                   tokenEncontrado=true;
                   palabra+=simbolo;
                   simbolo=hr.siguienteCaracter();
                   t=new Token(0,hr.getColumnaLinea(),numeroLinea+1,-1,palabra,palabra);
                   tokenReconocidos+=t.getToken()+"\n";
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
               case 13:
                   palabra+=simbolo;
                   simbolo=hr.siguienteCaracter();
                   if (simbolo=='='){
                       this.estado=12;
                   }else {
                       this.estado=14;
                   }
                   break;
               case 17:
                   palabra+=simbolo;
                   simbolo=hr.siguienteCaracter();
                   if (simbolo=='='){
                       this.estado=18;
                   }else {
                       this.estado=19;
                   }
                   break;
               case 20:
                   palabra+=simbolo;
                   simbolo=hr.siguienteCaracter();
                   if(simbolo=='='){
                       this.estado=21;
                   }else
                       this.estado=22;



           }
        }
        return t;
    }

    private Token identificadorAsignarId(String palabra){
        //buscar si se encuentra el token en las palabras reservadas
        String tipoToken = "";
        Token t = null;
        Simbolo s = null;
        boolean encontrado=false;
        //comprobar palabra reservada del lenguaje
        for (int i=0; i<this.palabrasReservadas.length; i++){
            if(palabra.equals(this.palabrasReservadas[i])){
                tipoToken = this.palabrasReservadas[i];
                encontrado=true;
                break;
            }
        }
        if (encontrado){
            t=new Token(0, hr.getColumnaLinea()+1, numeroLinea+1, -1, palabra, tipoToken);
            tokenReconocidos+=t.getToken()+"\n";

        }else {
            //encontrado=false;
            if (palabra.length() < 16){
                //si ya esta en la tabla de simbolos
                int j=0;
                for (int i=0; i<this.simbolos.size(); i++){
                    if (palabra.equals(this.simbolos.get(i).getSimbolo())){
                        encontrado=true;
                        j=i;
                        break;
                    }
                }
                if (encontrado){
                    t = new Token(1,((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1, j, palabra,"Identificador");
                    tokenReconocidos+= t.getToken() +"\n";
                }else {
                    s = new Simbolo(1, ((hr.getColumnaLinea() - palabra.length()) + 1),
                            numeroLinea + 1, palabra, "", false, false);
                    simbolos.add(s);
                    t=new Token(1, ((hr.getColumnaLinea() - palabra.length()) + 1)
                            ,numeroLinea +1 ,j,palabra,"Identificador");
                    tokenReconocidos += t.getToken()+"\n";
                }

            }else {
                numeroErrores++;
                ErrorLexico error = new ErrorLexico("identificador demasiado largo",
                        ((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1);
                this.errores.add(error);

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
