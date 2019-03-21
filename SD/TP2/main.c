// main.c
#include <locale.h>
#include <assert.h>
#include <wctype.h>
#include "chaine.h"

int main()
{
    struct chaine p;
    wint_t c;
    
    assert(setlocale(LC_ALL, "C.UTF-8") != NULL);
    
    // Initialisation de la chaine p
    init_chaine(&p);
    c = getwchar();
    
    while(!iswspace(c)) {
        // Ajout du caractère à la fin de p
        ajout_wchar(&p, c);
        // Passage au caractère suivant
        c = getwchar();
    }
    
    // Ajout du caractère de fin de chaine à p
    ajout_wchar(&p, '\0');
    // Affichage de la chaine de caractère p
    imprimer_chaine(p);
    // Libération de la mémoire alloué à p
    destruct_chaine(p);
    
    return 0;
}
