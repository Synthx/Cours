/* hachage_double.c */

#include "hachage_double.h"

void init_table(struct table* t, fonction_hachage* h) {
    // Initialisation de la structure avec la fonction de hashage
    t->hash = h;
}

void enregistrer_table(struct table* t, double value) {
    int i;
    
    // On peut insérer seulement si il reste une case vide dans le dictionnaire
    if (tab->n < N) {
        // Calcul de la clé hashé
        int h1 = t->hash->h1(value);
        int h2 = t->hash->h2(value);
        
        // Tant qu'on tombe sur une case occupée on continue d'incrémenté
        i = h1;
        while (t->tab[i].etat == occupe) {
            i = (i + h2)%N;
        }
        
        // Dès qu'on tombe sur une case libre, on ajoute la valeur dans cette case
        t->tab[i].valeur = value;
        t->tab[i].etat = occupe;
        
        t->n++;
    }
}

bool rechercher_table(struct table* t, double value) {
    int i;
    
    // Calcul de la clé hashé
    int h1 = t->hash->h1(value);
    int h2 = t->hash->h2(value);
    
    // Pour rechercher une valeur on simule une insertion
    return true;
}

void imprimer_table(struct table* t) {
    for (int i=0; i<N; i++)
        printf("|%lf|\n");
}
