/* exp.lex */
%{
#include "yygrammar.h"

char err[20]; /* Chaine de caracteres pour les erreurs de syntaxe */
%}

/* Definition de macros */
separateur      [ \t]
chiffre         [0-9]

%%

"+"             return PLUS;     /* Indique au parser que + est reconnu */
"-"             return MOINS;    /* Indique au parser que - est reconnu */
"*"             return MULT;     /* Indique au parser que * est reconnu */
"/"             return DIV;      /* Indique au parser que / est reconnu */
"("             return PAROUV;   /* Indique au parser que ( est reconnu */
")"             return PARFER;   /* Indique au parser que ) est reconnu */
{chiffre}+      return ENTIER;   /* Indique au parser qu'un entier est reconnu */
{separateur}+   ;                /* Elimination des espaces */
\n              yypos++;         /* Compte le nombre de lignes du fichier source */
.               { sprintf(err,"Mauvais caractere %c",yytext[0]);
                  yyerror(err);  /* Generation d'une erreur de syntaxe */
                }

%%