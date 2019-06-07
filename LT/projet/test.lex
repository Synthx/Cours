/* latex.lex */

%{
#include "yygrammar.h"
char err[20]; // chaine de caracteres pour les erreurs de syntaxe

%}

/* definition des macros : */
mot [a-zA-Z0-9éèà\n :,;.'()-]
separateur [\n\t]

%%

"\\textit{" {printf("textit\n");} //reconnaît la commande textit

"\\textbf{" {printf("textbf\n");} //reconnaît la commande textbf

"\\ldots" {printf("ldots\n");} //reconnaît la commande ldots

"\\" {printf("espace\n");} //reconnaît la commande end

"\\title{"  {printf("title\n");}   //reconnaît la commande title

"\\author{" {printf("author\n");} //reconnaît la commande author

"\\date{" {printf("date\n");} //reconnaît la commande date

"\\begin{" {printf("begin\n");} //reconnaît la commande begin

"\\maketitle" {printf("maketitle\n");}    //reconnaît la commande maketitle

"\\section{" {printf("section\n");} //reconnaît la commande section

"\\subsection{" {printf("subsection\n");} //reconnaît la commande subsection

"\\subsubsection{" {printf("subsubsection\n");} //reconnaît la commande subsubsection

"\\item" {printf("item\n");} //reconnaît la commande item

"\\end{" {printf("end\n");} //reconnaît la commande end

"\\enumerate{" {printf("enumerate\n");} //reconnaît la commande enumerate

{mot}+ {printf("mot\n");}

"}" {printf("fin cmd\n");}

{separateur}+ ;

. { printf("Mauvais caractere : %c\n", yytext[0]); }

%%
