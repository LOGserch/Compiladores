package fes.aragon.codigo;
import java.io.*;
import java.util.Stack;

public class Inicio {

    private AnalizadorLexico analizador = null;
    private Token token = null;
    private boolean errorEnLinea = false;
    private final String[] noTerminales = {"S", "A", "B", "C"};
    private final String[] terminales = {"a", "b", "c", "d", "#"};
    private final String[][] tabla = {
            {"A B", "A B", "error", "error", "error"},
            {"a", "&", "error", "error", "error"},
            {"error", "b C d", "error", "error", "error"},
            {"error", "error", "c", "&", "error"}
    };
    private Stack<String> pila = new Stack<>();
    private String topePila = "#";
    private String simbolo = "";

    public static void main(String[] args) throws IOException {
        try {
            Inicio app = new Inicio();

            String filePath = new File("fuente.txt").getAbsolutePath();
            FileReader fileReader = new FileReader(filePath);
            BufferedReader fileBuffer = new BufferedReader(fileReader);
            app.analizador = new AnalizadorLexico(fileBuffer);
            app.pila.add(app.topePila);
            app.pila.add(app.noTerminales[0]);
            app.siguienteToken();

            while (app.token.getLexema() != Sym.EOF) {

                try {
                    if (app.errorEnLinea) {
                        throw new IOException("Error en el compilador en la linea " + app.token.getLinea());
                    } else {
                        if (app.token.getLexema() == Sym.ESPACIO || app.token.getLexema() == Sym.SALTOLINEA) {
                            app.siguienteToken();
                        } else if (app.token.getLexema() == Sym.GATITO) {
                            app.pila.add(app.noTerminales[0]);
                            app.siguienteToken();
                        } else {
                            while(!app.pila.lastElement().equals(app.topePila)) {
                                if (app.simbolo.equals("error")) {
                                    app.errorEnLinea = true;
                                    break;
                                } else {
                                    if (app.pila.lastElement().equals(app.token.getToken())) {
                                        app.consumir();
                                    } else if (!app.pila.lastElement().equals(app.token.getToken())) {
                                        app.siNoEsTerminal(app.pila.lastElement(), app.token.getToken());
                                    } else if (app.simbolo.equals("error")) {
                                        app.errorEnLinea = true;
                                    }
                                }
                            }
                            System.out.println("Todo correcto en la linea: " + app.token.getLinea());
                            app.siguienteToken();
                        }
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void siNoEsTerminal(String noTerminal, String terminal) throws IOException {
        if (terminal.equals(" ")) {
            this.siguienteToken();
        } else {
            String regla = this.tabla[getFila(noTerminal)][getColumna(terminal)];

            if (regla.equals("A B")) {
                this.pila.pop();
                this.agregarElementos(regla);
            } else if (regla.equals("b C d")) {
                this.pila.pop();
                this.agregarElementos(regla);
            } else if (regla.equals("error")) {
                this.simbolo = regla;
            } else if(regla.equals("&")) {
            } else if (regla.equals("a") || regla.equals("c")) {
                this.pila.pop();
                this.agregarElementos(regla);
            }
        }
    }

    private void siguienteToken() {
        try {
            this.token = this.analizador.yylex();

            if (this.token == null) {
                this.token = new Token("EOF", Sym.EOF, 0, 0);
                throw new IOException("Fin");
            }
        } catch (IOException ex) {
            System.out.println("Fin del Archivo");
        }
    }

    public int getFila(String noTerminal) throws IOException {
        int i;

        for (i = 0; i < this.noTerminales.length; i++) {
            if (this.noTerminales[i].equals(noTerminal)) {
                break;
            }
        }

        return i;
    }

    public int getColumna (String terminal) throws IOException {
        int i;

        for (i = 0; i < this.terminales.length; i++) {
            if (this.terminales[i].equals(terminal)) {
                break;
            }
        }

        return i;
    }

    public void agregarElementos(String elemento) throws IOException{
        if (elemento.equals("A B")) {
            this.pila.add("B");
            this.pila.add("A");
        } else if (elemento.equals("b C d")) {
            this.pila.add("d");
            this.pila.add("C");
            this.pila.add("b");
        } else if (elemento.equals("&")) {
        } else {
            this.pila.add(elemento);
        }
    }

    public void consumir() {
        this.pila.pop();
        this.siguienteToken();
    }
}