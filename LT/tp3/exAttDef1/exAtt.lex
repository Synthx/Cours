/* exAtt.lex */
%{
#include "yygrammar.h"

char err[20]; /* Chaine de caracteres pour les erreurs de syntaxe */
%}

/* Definition de macros */
chiffre         [0-9]
separateur      [ \t]

%%

[+-]?{chiffre}+     return ENTIER;     /* Indique au parser qu un entier positif ou negatif est reconnu */
","                 return VIRG;       /* Indique au parser qu une virgule est reconnu */
";"                 return PVIRG;      /* Indique au parser qu un point-virgule est reconnu */
{separateur}+       ;                  /* Elimination des espaces */
\n                  yypos++;           /* Compte le nombre de lignes du fichier source */
.                   { sprintf(err,"Mauvais caractere %c",yytext[0]);
                      yyerror(err);    /* Generation d'une erreur de syntaxe */
                    }

%%
