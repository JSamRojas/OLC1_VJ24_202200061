// ------------  Paquete e importaciones ------------
package Compiladores; 

import java_cup.runtime.*;
import Funciones.Errores;
import java.util.LinkedList;

%%	

%{ 
    String cadena = "";
    public LinkedList<Errores> listaErrores = new LinkedList<Errores>();
%} 

%init{
    yyline = 1;
    yycolumn = 1;
    listaErrores = new LinkedList<Errores>();
%init}

//-------> Directivas (No tocar)

%cup
%class Lexer
%public
%line
%column
%char
%full
%debug
%ignorecase

%state CADENA

// ------> Expresiones Regulares 

ENTERO = [0-9]+
DECIMAL = [0-9]+"."[0-9]+
ESPACIO = [ ]
ID_VAR = [a-zA-Z@][a-zA-Z0-9_]*
COMENTARIO = (\/\/.*|\/\*[\s\S]*?\*\/)
CARACTER = [\'][^\n\'][\']
BLANCOS = [\ \t\r\n\f]+

// ------------>  Simbolos <-------------------

DOSPUNTOS = ":"
PUNTOYCOMA = ";"
PUNTO = "."
COMA = ","
SUMA = "+"
MENOS = "-"
POTENCIA = "**"
POR = "*"
DIVISION = "/"
MOD = "%"
DOSIGUAL = "=="
IGUAL = "="
DIFERENCIA = "!="
NOT = "!"
MENORIGUAL = "<="
MENOR = "<"
MAYORIGUAL = ">="
MAYOR = ">"
OR = "||"
AND = "&&"
XOR = "^"
PARENTESIS_A = "("
PARENTESIS_C = ")"
LLAVE_A = "{"
LLAVE_C = "}"
CORCHETE_A = "["
CORCHETE_C = "]"
GUIONBAJO = "_"

// ------------>  Palabras Reservadas <-------------------

VAR = "var" 
CONST = "const"
INT = "int"
BOOLEANO = "bool"
CHAR = "char"
DOUBLE = "double"
STRING = "string"
IF = "if"
FOR = "for"
DO = "do"
WHILE = "while"
MATCH = "match"
ELSE = "else"
PRINTLN = "println"
TRUE = "true"
FALSE = "false"
BREAK = "break"
CONTINUE = "continue"
VOID = "void"
STARTWITH = "start_with"
LIST = "list"
NEW = "new"
APPEND = "append"
REMOVE = "remove"

%%

<YYINITIAL> {DOSPUNTOS}         { return new Symbol(sym.DOSPUNTOS, yycolumn, yyline, yytext()); }
<YYINITIAL> {PUNTOYCOMA}        { return new Symbol(sym.PUNTOYCOMA, yycolumn, yyline, yytext()); }
<YYINITIAL> {PUNTO}             { return new Symbol(sym.PUNTO, yycolumn, yyline, yytext()); }
<YYINITIAL> {COMA}              { return new Symbol(sym.COMA, yycolumn, yyline, yytext()); }
<YYINITIAL> {SUMA}              { return new Symbol(sym.SUMA, yycolumn, yyline, yytext()); }
<YYINITIAL> {MENOS}             { return new Symbol(sym.MENOS, yycolumn, yyline, yytext()); }
<YYINITIAL> {POTENCIA}          { return new Symbol(sym.POTENCIA, yycolumn, yyline, yytext()); }
<YYINITIAL> {POR}               { return new Symbol(sym.POR, yycolumn, yyline, yytext()); }
<YYINITIAL> {DIVISION}          { return new Symbol(sym.DIVISION, yycolumn, yyline, yytext()); }
<YYINITIAL> {MOD}               { return new Symbol(sym.MOD, yycolumn, yyline, yytext()); }
<YYINITIAL> {DOSIGUAL}          { return new Symbol(sym.DOSIGUAL, yycolumn, yyline, yytext()); }
<YYINITIAL> {IGUAL}             { return new Symbol(sym.IGUAL, yycolumn, yyline, yytext()); }
<YYINITIAL> {DIFERENCIA}        { return new Symbol(sym.DIFERENCIA, yycolumn, yyline, yytext());  }
<YYINITIAL> {NOT}               { return new Symbol(sym.NOT, yycolumn, yyline, yytext()); }
<YYINITIAL> {MENORIGUAL}        { return new Symbol(sym.MENORIGUAL, yycolumn, yyline, yytext()); }
<YYINITIAL> {MENOR}             { return new Symbol(sym.MENOR, yycolumn, yyline, yytext()); }
<YYINITIAL> {MAYORIGUAL}        { return new Symbol(sym.MAYORIGUAL, yycolumn, yyline, yytext()); }
<YYINITIAL> {MAYOR}             { return new Symbol(sym.MAYOR, yycolumn, yyline, yytext()); }
<YYINITIAL> {OR}                { return new Symbol(sym.OR, yycolumn, yyline, yytext()); }
<YYINITIAL> {AND}               { return new Symbol(sym.AND, yycolumn, yyline, yytext()); }
<YYINITIAL> {XOR}               { return new Symbol(sym.XOR, yycolumn, yyline, yytext()); }
<YYINITIAL> {PARENTESIS_A}      { return new Symbol(sym.PARENTESIS_A, yycolumn, yyline, yytext()); }
<YYINITIAL> {PARENTESIS_C}      { return new Symbol(sym.PARENTESIS_C, yycolumn, yyline, yytext()); }
<YYINITIAL> {LLAVE_A}           { return new Symbol(sym.LLAVE_A, yycolumn, yyline, yytext()); }
<YYINITIAL> {LLAVE_C}           { return new Symbol(sym.LLAVE_C, yycolumn, yyline, yytext()); }
<YYINITIAL> {CORCHETE_A}        { return new Symbol(sym.CORCHETE_A, yycolumn, yyline, yytext()); }
<YYINITIAL> {CORCHETE_C}        { return new Symbol(sym.CORCHETE_C, yycolumn, yyline, yytext()); }
<YYINITIAL> {GUIONBAJO}         { return new Symbol(sym.GUIONBAJO, yycolumn, yyline, yytext()); }

// ------------>  Instrucciones <-------------------

<YYINITIAL> {VAR}                   { return new Symbol(sym.VAR, yycolumn, yyline, yytext()); }
<YYINITIAL> {CONST}                 { return new Symbol(sym.CONST, yycolumn, yyline, yytext()); }
<YYINITIAL> {INT}                   { return new Symbol(sym.INT, yycolumn, yyline, yytext()); }
<YYINITIAL> {BOOLEANO}              { return new Symbol(sym.BOOLEANO, yycolumn, yyline, yytext()); }
<YYINITIAL> {CHAR}                  { return new Symbol(sym.CHAR, yycolumn, yyline, yytext()); }
<YYINITIAL> {DOUBLE}                { return new Symbol(sym.DOUBLE, yycolumn, yyline, yytext()); }
<YYINITIAL> {STRING}                { return new Symbol(sym.STRING, yycolumn, yyline, yytext()); }
<YYINITIAL> {IF}                    { return new Symbol(sym.IF, yycolumn, yyline, yytext()); }
<YYINITIAL> {FOR}                   { return new Symbol(sym.FOR, yycolumn, yyline, yytext()); }
<YYINITIAL> {DO}                    { return new Symbol(sym.DO, yycolumn, yyline, yytext()); } 
<YYINITIAL> {WHILE}                 { return new Symbol(sym.WHILE, yycolumn, yyline, yytext()); }  
<YYINITIAL> {MATCH}                 { return new Symbol(sym.MATCH, yycolumn, yyline, yytext()); }
<YYINITIAL> {ELSE}                  { return new Symbol(sym.ELSE, yycolumn, yyline, yytext()); }
<YYINITIAL> {PRINTLN}               { return new Symbol(sym.PRINTLN, yycolumn, yyline, yytext()); }
<YYINITIAL> {TRUE}                  { return new Symbol(sym.TRUE, yycolumn, yyline, yytext()); }
<YYINITIAL> {FALSE}                 { return new Symbol(sym.FALSE, yycolumn, yyline, yytext()); }
<YYINITIAL> {BREAK}                 { return new Symbol(sym.BREAK, yycolumn, yyline, yytext()); } 
<YYINITIAL> {CONTINUE}              { return new Symbol(sym.CONTINUE, yycolumn, yyline, yytext()); }
<YYINITIAL> {VOID}                  { return new Symbol(sym.VOID, yycolumn, yyline, yytext()); }
<YYINITIAL> {STARTWITH}             { return new Symbol(sym.STARTWITH, yycolumn, yyline, yytext()); }
<YYINITIAL> {LIST}                  { return new Symbol(sym.LIST, yycolumn, yyline, yytext()); }
<YYINITIAL> {NEW}                   { return new Symbol(sym.NEW, yycolumn, yyline, yytext()); }
<YYINITIAL> {APPEND}                { return new Symbol(sym.APPEND, yycolumn, yyline, yytext()); }
<YYINITIAL> {REMOVE}                { return new Symbol(sym.REMOVE, yycolumn, yyline, yytext()); }

// ------------>  Expresiones <-------------------

<YYINITIAL> {ESPACIO}           { }
<YYINITIAL> {DECIMAL}           { return new Symbol(sym.DECIMAL, yycolumn, yyline, yytext()); }
<YYINITIAL> {ENTERO}            { return new Symbol(sym.ENTERO, yycolumn, yyline, yytext()); }
<YYINITIAL> {ID_VAR}            { return new Symbol(sym.ID, yycolumn, yyline, yytext()); }
<YYINITIAL> {COMENTARIO}        { }
<YYINITIAL> {CARACTER}          { char carAux = yytext().charAt(1); return new Symbol(sym.CARACTER, yycolumn, yyline, carAux); }
<YYINITIAL> {BLANCOS}           { }

<YYINITIAL> [\"]        {cadena = ""; yybegin(CADENA);}

<CADENA> {
    [\"]    {String tmp= cadena;
            cadena="";
            yybegin(YYINITIAL);
            return new Symbol(sym.CADENA, yyline, yycolumn,tmp);}
    
    [^\"]   {cadena += yytext();}
    "\\\\"  {cadena += "\\";}
    "\\\""  {cadena += "\"";}
    "\\n"   {cadena += "\n";}
    "\\t"   {cadena += "\t";}
    "\\r"   {cadena += "\r";}
    "\\f"   {cadena += "\f";}
    "\\'"   {cadena += "'";}
}

//------> Errores Léxicos 
.           	{ listaErrores.add(new Errores("LEXICO","El caracter "+
                yytext()+" no pertenece al lenguaje", yyline, yycolumn));}
