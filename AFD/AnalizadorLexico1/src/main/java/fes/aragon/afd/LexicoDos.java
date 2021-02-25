package fes.aragon.afd;
import fes.aragon.herramientas.ErrorLexico;
import fes.aragon.herramientas.Herramientas;
import fes.aragon.herramientas.RangoDeValores;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author sergi
 */
public class LexicoDos{
    public static void main(String[] args) {
        int estado = 0;
        int entrada = 0;
        Herramientas hr = new Herramientas();
        ArrayList<String> lineas = null;
        char simbolo = ' ';
        int linea = 0;
        int[][] tabla = {{2, 1,0, 0},
                        {1, 1,1, 0},
                        {2, 2,2, 1}};

        try {
            lineas = hr.lectura();
            for (int i = 0; i < lineas.size(); i++) {
                try {
                    hr.setPalabra(lineas.get(linea));
                    simbolo = hr.siguienteCaracter();

                    while (simbolo != 32) {
                        if (RangoDeValores.letra(simbolo)) {
                            entrada = 0;

                        } else if (RangoDeValores.enterosPositivos(simbolo)) {
                            entrada = 1;

                        }else if (RangoDeValores.caracteresEspecificos(simbolo)) {
                            entrada = 2;
                        }
                        else if (RangoDeValores.finCadena(simbolo)) {
                            entrada = 3;
                        }
                        else {
                            throw new ErrorLexico("Caracter no valido, linea" + (linea + 1));

                        }
                        estado = tabla[estado][entrada];
                        simbolo = hr.siguienteCaracter();
                    }
                    if (entrada == 3 && estado == 1) {
                        System.out.println("variable valida, linea" + (linea + 1));
                    } else if (entrada == 3 && estado == 0) {
                        System.out.println("variable no valida, linea" + (linea + 1));

                    }

                }catch(ErrorLexico ex) {
                    System.out.println("variable no valida, linea" + (linea + 1));
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                estado = 0;
                entrada = 0;
                //simbolo = ' ';
                linea += 1;
            }
        }catch (IOException ex) {
            ex.printStackTrace();//imprime el error en consola
            JOptionPane.showMessageDialog(null, "error en el archivo");
        }

    }

}
