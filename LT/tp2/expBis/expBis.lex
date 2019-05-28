/* expBis.lex */
%{
#include "yygrammar.h"

char err[20];
%}

/* Definition de macros */
separateur [ \t]
chiffre [0-9]

%%

"+" return PLUS;
"-" return MOINS;
"*" return MULT;
"/" return DIV;
"(" return PAROUV;
")" return PARFER;

("+"|"-"){chiffre}+ return ENTIER_SIGNE;
("+"|"-"){chiffre}*"."{chiffre}+ return REEL_SIGNE;

{separateur}+;
\n              yypos++;
.               { sprintf(err,"Mauvais caractere %c",yytext[0]);
                  yyerror(err);
                }

%%