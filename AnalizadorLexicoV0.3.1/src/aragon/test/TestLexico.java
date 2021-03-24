package aragon.test;

import aragon.herramientas.ErrorLexico;
import aragon.modulo.lexico.Lexico;
import aragon.modulo.lexico.Token;

public class TestLexico {
    public static void main(String[] args) {
        try {
            Lexico lexico = new Lexico();
            Token t = null;
            do{
                t=lexico.siguienteToken();
            }while (t!=null);
            System.out.println(lexico.getTokenReconocidos());


        }catch (ErrorLexico ex){
            ex.printStackTrace();

        }
    }
}
