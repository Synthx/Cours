// main.c
#include <stdio.h>
#include <ctype.h>
#include "chaine.h"

int main()
{
    struct chaine p;
    int c;
    
    // Initialisation de la chaine p
    init_chaine(&p);
    c = getchar();
    
    while(!isspace(c)) {
        // Ajout du caractère à la fin de p
        ajout_char(&p, c);
        // Passage au caractère suivant
        c = getchar();
    }
    
    // Ajout du caractère de fin de chaine à p
    ajout_char(&p, '\0');
    // Affichage de la chaine de caractère p
    imprimer_chaine(p);
    // Libération de la mémoire alloué à p
    destruct_chaine(p);
    
    return 0;
}
