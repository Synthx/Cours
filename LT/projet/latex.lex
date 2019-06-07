/* latex.lex */

%{
#include "yygrammar.h"
char err[20]; // chaine de caracteres pour les erreurs de syntaxe

%}

/* definition des macros : */
mot [a-zA-Z0-9 :,;.'()-]
separateur [\n\t]

%%

"\\textit{" {printf("textit "); return TEXTIT;} //reconnaît la commande textit

"\\textbf{" {printf("textbf "); return TEXTBF;} //reconnaît la commande textbf

"\\ldots" {printf("ldots "); return LDOTS;} //reconnaît la commande ldots

"\\" {printf("espace ");} //reconnaît la commande end

"\\title{"  {printf("title "); return TITLE;}   //reconnaît la commande title

"\\author{" {printf("author "); return AUTHOR;} //reconnaît la commande author

"\\date{" {printf("date "); return DATE;} //reconnaît la commande date

"\\begin{" {printf("begin "); return BEGINS;} //reconnaît la commande begin

"\\maketitle" {printf("maketitle "); return MAKETITLE;}    //reconnaît la commande maketitle

"\\section{" {printf("section "); return SECTION;} //reconnaît la commande section

"\\subsection{" {printf("subsection "); return SUBSECTION;} //reconnaît la commande subsection

"\\subsubsection{" {printf("subsubsection "); return SUBSUBSECTION;} //reconnaît la commande subsubsection

"\\item" {printf("item "); return ITEM;} //reconnaît la commande item

"\\end{" {printf("end "); return END;} //reconnaît la commande end

"\\enumerate{" {printf("enumerate "); return ENUMERATE;} //reconnaît la commande enumerate

{mot} {printf("mot "); return WORD;}

{separateur}+ ;

. { printf("Mauvais caractere : %c\n", yytext[0]); }

%%
