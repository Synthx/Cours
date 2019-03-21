// chaine.c
#include <stdio.h>
#include <stdlib.h>
#include "chaine.h"

// Initialise une nouvelle chaine de caractère
void init_chaine(struct chaine* ch) {
    init_liste_char(&(ch->L));
}

// Libère l'espace mémoire associé à la chaine donnée en paramètre
void clear_chaine(struct chaine* ch) {
    clear_liste_char(&(ch->L));
}

// Ajoute à la fin de la chaine donnée en paramètre un nouveau caractère
void ajout_char(struct chaine* ch, char c) {
    ajouter_fin_liste_char(&(ch->L), c);
}

// Affiche la chaine de caractère donnée en paramètre
void imprimer_chaine(struct chaine* ch) {
    imprimer_liste_char(&(ch->L));
}
