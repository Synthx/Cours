/* latex.lex */

%{
#include "yygrammar.h"

char err[20]; // chaine de caracteres pour les erreurs de syntaxe
%}

/* definition des macros : */
mot [a-zA-Z0-9éèà :,;.'()\[\]\-\/]

%%

"}" return ACC;

"document" return DOCUMENT;

"itemize" return ITEMIZE;

"enumerate" return ENUMERATE;

"\\textit{" return TEXTIT; //reconnaît la commande textit

"\\textbf{" return TEXTBF; //reconnaît la commande textbf

"\\ldots" return LDOTS; //reconnaît la commande ldots

"\\" return BACKSLASH; //reconnaît la commande end

"\\title{" return TITLE;  //reconnaît la commande title

"\\author{" return AUTHOR; //reconnaît la commande author

"\\date{" return DATE; //reconnaît la commande date

"\\begin{" return BEGINS; //reconnaît la commande begin

"\\maketitle" return MAKETITLE; //reconnaît la commande maketitle

"\\section{" return SECTION; //reconnaît la commande section

"\\subsection{" return SUBSECTION; //reconnaît la commande subsection

"\\subsubsection{" return SUBSUBSECTION; //reconnaît la commande subsubsection

"\\item" return ITEM; //reconnaît la commande item

"\\end{" return END; //reconnaît la commande end

{mot}+ return WORD; //reconnait un mot

\n yypos++; 

[\t]+ ;
   
. { printf("Mauvais caractere : %c\n", yytext[0]); }

%%
