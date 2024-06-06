// ------------  Paquete e importaciones ------------
package Compiladores; 

import java_cup.runtime.*;
import Funciones.Errores;


%%	
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

%{ 
%} 

// ------> Expresiones Regulares 

entero = [0-9]+
decimal = [0-9]+"."[0-9]+
espacio = [ ]
id_var = [a-zA-Z@][a-zA-Z0-9_]*|[\"][^\n\"]*[\"]
comentario = (\/\/.*|\/\*[\s\S]*?\*\/)

%%
// ------------  Reglas Lexicas -------------------

// ------------>  Simbolos <-------------------

":"     { return new Symbol(sym.DOSPUNTOS, yycolumn, yyline, yytext()); }
"="     { return new Symbol(sym.IGUAL, yycolumn, yyline, yytext()); }
";"     { return new Symbol(sym.PUNTOYCOMA, yycolumn, yyline, yytext()); }
"+"     { return new Symbol(sym.SUMA, yycolumn, yyline, yytext()); }
"-"     { return new Symbol(sym.MENOS, yycolumn, yyline, yytext()); }
"*"     { return new Symbol(sym.POR, yycolumn, yyline, yytext()); }
"/"     { return new Symbol(sym.DIVISION, yycolumn, yyline, yytext()); }
"**"    { return new Symbol(sym.POTENCIA, yycolumn, yyline, yytext()); }
"%"     { return new Symbol(sym.MOD, yycolumn, yyline, yytext()); }
"=="    { return new Symbol(sym.DOSIGUAL, yycolumn, yyline, yytext()); }
"!="    { return new Symbol(sym.DIFERENCIA, yycolumn, yyline, yytext());  }
"<"     { return new Symbol(sym.MENOR, yycolumn, yyline, yytext()); }
"<="    { return new Symbol(sym.MENORIGUAL, yycolumn, yyline, yytext()); }
">"     { return new Symbol(sym.MAYOR, yycolumn, yyline, yytext()); }
">="    { return new Symbol(sym.MAYORIGUAL, yycolumn, yyline, yytext()); }
"||"    { return new Symbol(sym.OR, yycolumn, yyline, yytext()); }
"&&"    { return new Symbol(sym.AND, yycolumn, yyline, yytext()); }
"^"     { return new Symbol(sym.XOR, yycolumn, yyline, yytext()); }
"!"     { return new Symbol(sym.NOT, yycolumn, yyline, yytext()); }
"("     { return new Symbol(sym.PARENTESIS_A, yycolumn, yyline, yytext()); }
")"     { return new Symbol(sym.PARENTESIS_C, yycolumn, yyline, yytext()); }
"{"     { return new Symbol(sym.LLAVE_A, yycolumn, yyline, yytext()); }
"}"     { return new Symbol(sym.LLAVE_C, yycolumn, yyline, yytext()); }
"["     { return new Symbol(sym.CORCHETE_A, yycolumn, yyline, yytext()); }
"]"     { return new Symbol(sym.CORCHETE_C, yycolumn, yyline, yytext()); }

// ------------>  Instrucciones <-------------------

"var"         { return new Symbol(sym.VAR, yycolumn, yyline, yytext()); }
"const"       { return new Symbol(sym.CONST, yycolumn, yyline, yytext()); }
"int"         { return new Symbol(sym.INT, yycolumn, yyline, yytext()); }
"bool"        { return new Symbol(sym.BOOLEANO, yycolumn, yyline, yytext()); }
"char"        { return new Symbol(sym.CHAR, yycolumn, yyline, yytext()); }
"string"      { return new Symbol(sym.STRING, yycolumn, yyline, yytext()); }
"if"          { return new Symbol(sym.IF, yycolumn, yyline, yytext()); }
"println"     { return new Symbol(sym.PRINTLN, yycolumn, yyline, yytext()); }

// ------------>  Expresiones <-------------------

{espacio}       {}
{decimal}       { return new Symbol(sym.DECIMAL, yycolumn, yyline, yytext()); }
{entero}        { return new Symbol(sym.ENTERO, yycolumn, yyline, yytext()); }
{id_var}        { return new Symbol(sym.ID, yycolumn, yyline, yytext()); }
{comentario}    {}


//------> Ingorados 
[ \t\r\n\f]     {/* Espacios en blanco se ignoran */}

//------> Errores Léxicos 
.           	{ System.out.println("Error Lexico: " + yytext() + " | Fila:" + yyline + " | Columna: " + yycolumn); }
