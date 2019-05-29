/* listeBis.lex */
%{
#include "yystype.h"
#include "yygrammar.h"

char err[20]; /* Chaine de caracteres pour les erreurs de syntaxe */
%}

/* Definition de macros */
lettre [a-zA-Z]
separateur [ \t]

%%

{lettre}+       {
                    yylval.valChaine = malloc((strlen(yytext) + 1) * sizeof(char));
                    strcpy(yylval.valChaine, yytext);
                    return ID;
                }
"("             return PAROUV;
")"             return PARFER;
{separateur}+   ;
\n              yypos++;
.               {
                    sprintf(err, "Mauvais caractere %c",yytext[0]);
                    yyerror(err);
                }

%%