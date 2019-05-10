/* hachage_simple.c */

#include "hachage_simple.h"

void init_table(struct table* t, fonction_hachage* h) {
    // Initialisation de la structure avec la fonction de hashage
    t->hash = h;
    // On initialise chaque alvéole à une liste vide
    for (int i=0; i<N; i++)
        init_liste_wstring(&t->tab[i].L);
}

void clear_table(struct table* t) {
    // Libération de la mémoire pour chaque alvéole
    for (int i=0; i<N; i++)
        clear_liste_wstring(&t->tab[i].L);
}

void enregistrer_table(struct table* t, wstring clef, wstring satellite) {
    // Calcul de la clé hashé
    int key = t->hash(clef);
    // Ajout dans l'alvéole correspondante
    ajouter_en_tete_liste_wstring(&t->tab[key].L, clef, satellite);
}

wchar_t* rechercher_table(struct table* t, wstring clef) {
    // Calcul de la clé hashé
    int key = t->hash(clef);
    // Recherche dans la liste sur la valeur est présente
    return rechercher_liste_wstring(&t->tab[key].L, clef);
}

void imprimer_table(struct table* t) {
    for (int i=0; i<N; i++)
        imprimer_liste_wstring(&t->tab[i].L);
}
