
package ppdz;
import java_cup.runtime.Symbol;


%%

%{
		// ukljucivanje informacije o poziciji tokena
		private Symbol new_symbol(int type) {
				return new Symbol(type, yyline+1, yycolumn);
		}
		// ukljucivanje informacije o poziciji tokena
		private Symbol new_symbol(int type, Object value) {
				return new Symbol(type, yyline+1, yycolumn, value);
		}
%}

%cup

%xstate COMMENT

%eofval{ 
return new_symbol(sym.EOF);
%eofval}

%line
%column

%%
" " {}
"\b" {}
"\t" {}
"\n" {}
"\r\n" {}
"\f" {}
"new"   {return new_symbol(sym.NEW);}
"class" {return new_symbol(sym.CLASS);}
"print" {return new_symbol(sym.PRINT);}
"read"  {return new_symbol(sym.READ);}
"return" {return new_symbol(sym.RETURN);}
"void" {return new_symbol(sym.VOID);}
"++" {return new_symbol(sym.PLUSPLUS);}
"--" {return new_symbol(sym.MINUSMINUS);}
"+" {return new_symbol(sym.PLUS);}
"-" {return new_symbol(sym.MINUS);}
"*" {return new_symbol(sym.TIMES);} 
"/" {return new_symbol(sym.DIV);}
"=" {return new_symbol(sym.EQUAL);}
";" {return new_symbol(sym.SEMI);}
"," {return new_symbol(sym.COMMA);}
"(" {return new_symbol(sym.LPAREN);}
")" {return new_symbol(sym.RPAREN);}
"{" {return new_symbol(sym.LBRACE);}
"}" {return new_symbol(sym.RBRACE);}
"[" {return new_symbol(sym.LBRACKET);}
"]" {return new_symbol(sym.RBRACKET);}
"?" {return new_symbol(sym.QUEST);}
"const" {return new_symbol(sym.CONST);}
"case" {return new_symbol(sym.CASE);}
"continue" {return new_symbol(sym.CONTINUE);}
"break" {return new_symbol(sym.BREAK);}
"!=" {return new_symbol(sym.DIFFERENT);}
"==" {return new_symbol(sym.SAME);}
">=" {return new_symbol(sym.GREATEREQUAL);}
"<=" {return new_symbol(sym.LESSEQUAL);}
"<" {return new_symbol(sym.LESS);}
">" {return new_symbol(sym.GREATER);}
"||" {return new_symbol(sym.OR);}
"&&" {return new_symbol(sym.AND);}
"," {return new_symbol(sym.COMMA);}
"<" {return new_symbol(sym.LESS);}
"if" {return new_symbol(sym.IF);}
"else" {return new_symbol(sym.ELSE);}
"true" {return new_symbol(sym.TRUE);}
"false" {return new_symbol(sym.FALSE);}
"%" {return new_symbol(sym.MOD);}
"switch" {return new_symbol(sym.SWITCH);}
":" {return new_symbol(sym.COL);}
"." {return new_symbol(sym.POINT);}
"while" {return new_symbol(sym.WHILE);}
"extends" {return new_symbol(sym.EXTENDS);}
"false" {return new_symbol(sym.FALSE);}
"." {return new_symbol(sym.POINT);}
"do" {return new_symbol(sym.DO);}
"program" {return new_symbol(sym.PROG);}


"//" {yybegin(COMMENT);}
<COMMENT>"\n" {yybegin(YYINITIAL);}
<COMMENT>. {yybegin(COMMENT);}
<COMMENT>"\r\n" {yybegin(YYINITIAL);}
[0-9]+ {return new_symbol(sym.NUMBER, new Integer (yytext()));}
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* {return new_symbol (sym.IDENT, yytext());}
"'"[\040-\176]"'" {return new_symbol (sym.LETTER, new String (yytext()));}
. {System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1) + "i koloni " + (yycolumn+1));}
