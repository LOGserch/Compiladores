package fes.aragon.codigo;

public class Token {
    private int linea;
    private int lexema;
    private int columna;
    private String token;
    
    public Token(String token, int lexema, int linea, int columna) {
        this.linea = linea;
        this.token = token;
        this.lexema = lexema;
        this.columna = columna;
    }
    
    public String getToken() {
        return this.token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public int getLexema() {
        return this.lexema;
    }
    
    public void setLexema(int lexema) {
        this.lexema = lexema;
    }

    public int getLinea() {
        return this.linea;
    }
    
    public void setLinea(int linea) {
        this.linea = linea;
    }
    
    public int getColumna() {
        return this.columna;
    }
    
    public void setColumna(int columna) {
        this.columna = columna;
    }
}