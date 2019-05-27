/* structure.c */
#include "structure.h"
#include <stdlib.h>
#include <assert.h>

void init_donnees_tab(struct donnees *D, int n) {
    int size, i;

    size = n + 1;
    D->q = malloc(n * sizeof(int));
    D->distances = malloc(size * sizeof(float *));
    for (i=0; i < size; i++)
        D->distances[i] = malloc(size * sizeof(float));
}

void free_donnees_tab(struct donnees D) {
    int i;

    free(D.q);
    for (i=0; i < (D.n + 1); i++)
        free(D.distances[i]);
    free(D.distances);
}

void init_tab_liste_succ(struct liste *tab, int size) {
    int i;

    for (i=0; i < size; i++)
        init_liste_succ(&tab[i]);
}

void free_tab_liste_succ(struct liste *tab, int size) {
    struct maillon *next, *current;
    int i;

    for (i=0; i < size; i++) {
        current = tab[i].tete;

        while (current != NIL) {
            next = current->next;
            free(current);
            current = next;
        }
    }

    free(tab);
}

void init_liste_succ(struct liste *L) {
    L->tete = NIL;
    L->nb_elem = 0;
}

void ajout_liste_succ(struct liste *L, int succ, float cost) {   
    struct maillon* N;

    N = malloc(sizeof(struct maillon));
    assert(N != NIL);

    N->value = succ;
    N->cost = cost;
    N->next = L->tete;

    L->tete = N;
    L->nb_elem++;
}