// chaine.c
#include <stdio.h>
#include <stdlib.h>
#include "chaine.h"

// Initialise une nouvelle chaine de caractère
void init_chaine(struct chaine* ch) {
    ch->i = 0;
    ch->n = 4;
    ch->p = malloc(ch->n * sizeof(wchar_t));
}

// Ajoute à la fin de la chaine donnée en paramètre un nouveau caractère
void ajout_wchar(struct chaine* ch, wchar_t c) {
    if (ch->i == ch->n) {
        ch->n += 4;
        ch->p = realloc(ch->p, ch->n * sizeof(wchar_t));
    }
    
    ch->p[ch->i] = c;
    ch->i++;
}

// Affiche la chaine de caractère donnée en paramètre
void imprimer_chaine(struct chaine ch) {
    wprintf(L"%ls\n", ch.p);
}

// Libère l'espace mémoire associé à la chaine donnée en paramètre
void destruct_chaine(struct chaine ch) {
    free(ch.p);
}
