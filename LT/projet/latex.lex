/* latex.lex */

%{
#include "yygrammar.h"
char err[20]; // chaine de caracteres pour les erreurs de syntaxe

%}

/* definition des macros : */
mot [a-zA-Z0-9éèà\n :,;.'()-]
separateur [\n\t]

%%

"\\textit{" {printf("textit\n"); return TEXTIT;} //reconnaît la commande textit

"\\textbf{" {printf("textbf\n"); return TEXTBF;} //reconnaît la commande textbf

"\\ldots" {printf("ldots\n"); return LDOTS;} //reconnaît la commande ldots

"\\" {printf("espace\n"); return BACKSLASH;} //reconnaît la commande end

"\\title{"  {printf("title\n"); return TITLE;}   //reconnaît la commande title

"\\author{" {printf("author\n"); return AUTHOR;} //reconnaît la commande author

"\\date{" {printf("date\n"); return DATE;} //reconnaît la commande date

"\\begin{" {printf("begin\n"); return BEGINS;} //reconnaît la commande begin

"\\maketitle" {printf("maketitle\n"); return MAKETITLE;}    //reconnaît la commande maketitle

"\\section{" {printf("section\n"); return SECTION;} //reconnaît la commande section

"\\subsection{" {printf("subsection\n"); return SUBSECTION;} //reconnaît la commande subsection

"\\subsubsection{" {printf("subsubsection\n"); return SUBSUBSECTION;} //reconnaît la commande subsubsection

"\\item" {printf("item\n"); return ITEM;} //reconnaît la commande item

"\\end{" {printf("end\n"); return END;} //reconnaît la commande end

"\\enumerate{" {printf("enumerate\n"); return ENUMERATE;} //reconnaît la commande enumerate

{mot}+ {printf("mot\n"); return WORD;} //reconnaît un mot

"}" {printf("fin cmd\n"); return ACC;}

{separateur}+ ;

. { printf("Mauvais caractere : %c\n", yytext[0]); }

%%
