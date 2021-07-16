package fes.aragon.codigo;
%%
%public
%class AnalizadorLexico
%line
%column
%char
%full
%type Token
%{
    private boolean hayToken=false;
    public boolean getHayToken(){
        return this.hayToken;
    }
%}
%init{
    /* código que se ejecuta en el constructor de la clase*/
%init}
%eof{
    /* código que se ejecuta al terminar el archivo*/
    this.hayToken=false;
%eof}
Espacio=" "
PuntoComa=";"
saltoLinea=\n|\r
ID=[a-zA-ZñÑ]+(_[a-zA-Z]+)?

%%
{ID} {
            Token token=new Token(yytext(),Sym.IDENTIFICADOR,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{PuntoComa} {
            Token token=new Token(yytext(),Sym.PUNTOCOMA,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{saltoLinea} {
          Token token=new Token(yytext(),Sym.SALTOLINEA,yyline+1,yycolumn+1);
          this.hayToken=true;
          return token;
}
{Espacio} {
          Token token=new Token(yytext(),Sym.ESPACIO,yyline+1,yycolumn+1);
          this.hayToken=true;
          return token;
}
. {
    String error="Error Léxico: " + yytext() + " linea "+
    (yyline+1)+ " columna "  + (yycolumn+1);
    System.out.println(error);
}