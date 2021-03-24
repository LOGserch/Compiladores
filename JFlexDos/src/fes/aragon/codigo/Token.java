package fes.aragon.codigo;

public class Token {
    private String token;
    private int linea;
    private int lexema;
    private int columna;

    public Token(String token, int lexema, int linea, int columna) {
        this.token = token;
        this.linea = linea;
        this.lexema = lexema;
        this.columna = columna;
    }

    public String getToken() {
        return token;
    }

    public void setToken(int string) {
        this.token=token;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getLexema() {
        return lexema;
    }

    public void setLexema(int lexema) {
        this.lexema = lexema;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}
