/* main.c */

#include <locale.h>
#include <wctype.h>
#include <assert.h>
#include <stdio.h>
#include "hachage_simple.h"

int hachage_basique(wstring clef) {
    wint_t result;
    int i;
    
    result = 0;
    for(i = 0; clef [i] != L'\0'; i++)
        result = (result + (wint_t)clef [i]) % (wint_t)N;
    
    return (int)result;
}

int main() {
    struct table T;
    wstring clef, satellite;
    wint_t c;
    FILE* f;
    
    // Ouverture du fichier
    assert(setlocale (LC_ALL, "C.UTF-8") != NULL);
    f = fopen("Esperanto-Francais.utf8", "r");
    assert(f != (FILE*)0);
    
    // Initialisation du dictionnaire
    init_table(&T, &hachage_basique);
    
    c = fgetwc(f);
    while (c != WEOF) {
        int i = 0;
    
        // Clef
        while (c != L':') {
            clef[i++] = c;
            c = fgetwc (f);
        }
        clef[i] = L'\0';
        
        // satellite
        c = fgetwc (f);
        i = 0;
        while (c != L'\n') {
            satellite [i++] = c;
            c = fgetwc (f);
        }
        satellite[i] = L'\0';
        
        // Enregistrement dans le dictionnaire
        enregistrer_table (&T, clef, satellite);
        c = fgetwc (f);
    }
    
    // Fermeture du fichier
    fclose(f);
    
    // Impression du dictionnaire
    imprimer_table(&T);
    
    // Libération de la mémoire
    clear_table(&T);
    
    return 0;
}
