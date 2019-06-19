/* auxil.c */
#include <stdio.h>
#include <stdlib.h>

/* Programme principal */
int main(void) {
    yyparse() ; /* Appel de l'analyseur syntaxique */
    return 0;
}

/* Fonction d'affichage des erreurs */
int yyerror(char *msg) {
    extern long yypos; /* Numero de la ligne courante du fichier d entree */

    printf("Ligne %li: %s\n", yypos, msg);
    exit(1);
}

/* Retourne 1 si pas d autre fichier a analyser */
int yywrap() {
    return 1;
}
