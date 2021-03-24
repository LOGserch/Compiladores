package aragon.modulo.lexico;

public class Token {
    private int tipoToken;
    private int numeroColumna;
    private int numeroLinea;
    private int PosicionSimbolo;
    private String token;
    private String nombreVariable;

    public Token(int tipoToken, int numeroColumna, int numeroLinea, int posicionSimbolo, String token, String nombreVariable) {
        this.tipoToken = tipoToken;
        this.numeroColumna = numeroColumna;
        this.numeroLinea = numeroLinea;
        PosicionSimbolo = posicionSimbolo;
        this.token = token;
        this.nombreVariable = nombreVariable;
    }

    public void setTipoToken(int tipoToken) {
        this.tipoToken = tipoToken;
    }

    public void setNumeroColumna(int numeroColumna) {
        this.numeroColumna = numeroColumna;
    }

    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public void setPosicionSimbolo(int posicionSimbolo) {
        PosicionSimbolo = posicionSimbolo;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setNombreVariable(String nombreVariable) {
        this.nombreVariable = nombreVariable;
    }

    public int getTipoToken() {
        return tipoToken;
    }

    public int getNumeroColumna() {
        return numeroColumna;
    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    public int getPosicionSimbolo() {
        return PosicionSimbolo;
    }

    public String getToken() {
        return token;
    }

    public String getNombreVariable() {
        return nombreVariable;
    }
}
