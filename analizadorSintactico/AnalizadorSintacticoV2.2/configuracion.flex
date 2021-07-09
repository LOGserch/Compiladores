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
igual="=="
mayorQue=">"
menorQue="<"
mayorIgual=">="
menorIgual="<="
diferenteDe="!="
asignacion=":="
parAp="("
parC=")"
ENTERO=[0-9]+
ID=[a-zA-ZñÑ][0-9A-Za-zñÑ]*

%%
"true" {
       Token token=new Token(yytext(),Sym.TRUE,yyline+1,yycolumn+1);
       this.hayToken=true;
       return token;
}
"false" {
       Token token=new Token(yytext(),Sym.FALSE,yyline+1,yycolumn+1);
       this.hayToken=true;
       return token;
}
"not" {
       Token token=new Token(yytext(),Sym.NOT,yyline+1,yycolumn+1);
       this.hayToken=true;
       return token;
}
"or" {
       Token token=new Token(yytext(),Sym.OR,yyline+1,yycolumn+1);
       this.hayToken=true;
       return token;
}
"and" {
       Token token=new Token(yytext(),Sym.AND,yyline+1,yycolumn+1);
       this.hayToken=true;
       return token;
}
"if" {
       Token token=new Token(yytext(),Sym.CONDICION_IF,yyline+1,yycolumn+1);
       this.hayToken=true;
       return token;
}
"else" {
       Token token=new Token(yytext(),Sym.CONDICION_ELSE,yyline+1,yycolumn+1);
       this.hayToken=true;
       return token;
}
"end" {
       Token token=new Token(yytext(),Sym.TERMINAR,yyline+1,yycolumn+1);
       this.hayToken=true;
       return token;
}
{asignacion} {
       Token token=new Token(yytext(),Sym.ASIGNACION,yyline+1,yycolumn+1);
       this.hayToken=true;
       return token;
}
{igual} {
            Token token=new Token(yytext(),Sym.IGUAL,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{mayorQue} {
            Token token=new Token(yytext(),Sym.MAYOR,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{menorQue} {
            Token token=new Token(yytext(),Sym.MENOR,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{mayorIgual} {
            Token token=new Token(yytext(),Sym.MAYOR_QUE,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{menorIgual} {
            Token token=new Token(yytext(),Sym.MENOR_QUE,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{diferenteDe} {
            Token token=new Token(yytext(),Sym.DIFERENTE,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{parAp} {
            Token token=new Token(yytext(),Sym.PARA,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{parC} {
            Token token=new Token(yytext(),Sym.PARC,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{ENTERO} {
            Token token=new Token(yytext(),Sym.NUM_ENTERO,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
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
}
{Espacio} {
}
. {
    String error="Error Léxico: " + yytext() + " linea "+
    (yyline+1)+ " columna "  + (yycolumn+1);
    System.out.println(error);
}