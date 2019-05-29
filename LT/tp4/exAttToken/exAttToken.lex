/* exAttToken.lex */
%{
#include "yystype.h"
#include "yygrammar.h"

char err[20]; /* Chaine de caracteres pour les erreurs de syntaxe */
%}

/* Definition de macros */
chiffre [0-9]
separateur [ \t]

%%

[+-]{chiffre}+  {
                    yylval.valEnt = atoi(yytext);
                    return ENTIER;
                }
[+-]{chiffre}*"."{chiffre}+ {
                                yylval.valReel = atof(yytext);
                                return REEL;
                            }
","                 return VIRG;
";"                 return PVIRG;
{separateur}+       ;
\n                  yypos++;
.                   { sprintf(err,"Mauvais caractere %c",yytext[0]);
                      yyerror(err);    /* Generation d'une erreur de syntaxe */
                    }

%%
