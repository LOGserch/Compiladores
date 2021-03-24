package aragon.modulo.lexico;

public class Simbolo {
    private int tipoSimbolo;
    private int numeroColumna;
    private int numeroLinea;
    private String simbolo;
    private String tipo;
    private boolean declarado;
    private boolean inicializado;

    public int getTipoSimbolo() {
        return tipoSimbolo;
    }

    public void setTipoSimbolo(int tipoSimbolo) {
        this.tipoSimbolo = tipoSimbolo;
    }

    public int getNumeroColumna() {
        return numeroColumna;
    }

    public void setNumeroColumna(int numeroColumna) {
        this.numeroColumna = numeroColumna;
    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isDeclarado() {
        return declarado;
    }

    public void setDeclarado(boolean declarado) {
        this.declarado = declarado;
    }

    public boolean isInicializado() {
        return inicializado;
    }

    public void setInicializado(boolean inicializado) {
        this.inicializado = inicializado;
    }

}
