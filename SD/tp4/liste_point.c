#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "liste_point.h"

void init_liste_point(struct liste_point* L) {
    L->tete = (struct maillon_point*) 0;
    L->nbelem = 0;
}

void ajouter_en_tete_liste_point(struct liste_point* L, struct point p) {
    struct maillon_point* nouveau;

    nouveau = (struct maillon_point*) malloc(sizeof(struct maillon_point));
    assert(nouveau != (struct maillon_point*) 0);
    /* appeler ici un �ventuel constructeur pour nouveau->value */
    nouveau->value = p;
    nouveau->next = L->tete;

    L->tete = nouveau;
    L->nbelem += 1;
}

void clear_liste_point(struct liste_point* L) {
    struct maillon_point* courant;
    struct maillon_point* suivant;
    int i;

    courant = L->tete;
    for (i = 0; i < L->nbelem; i++) {
        suivant = courant->next;
        /* appeler ici un �ventuel destructeur pour nouveau->value */
        free(courant);
        courant = suivant;
    }
}

static void retourner_liste_point(struct liste_point* L) {
    struct maillon_point *precedent, *courant, *suivant;
    int i;

    if (L->nbelem >= 2) {
        courant = L->tete;
        suivant = courant->next;
        courant->next = (struct maillon_point*) 0;

        for (i = 1; i < L->nbelem; i++) {
            precedent = courant;
            courant = suivant;
            suivant = suivant->next;
            courant->next = precedent;
        }

        L->tete = courant;
    }
}

void set_liste_point(struct liste_point* dst, struct liste_point* src) {
    struct maillon_point* M;
    int i;

    if (dst != src) {
        clear_liste_point(dst);
        init_liste_point(dst);
        M = src->tete;

        for (i = 0; i < src->nbelem; i++) {
            ajouter_en_tete_liste_point(dst, M->value);
            M = M->next;
        }

        retourner_liste_point(dst);
    }
}

void extraire_tete_liste_point(struct liste_point* L) {
    struct maillon_point* tete;

    assert(L->nbelem != 0);

    tete = L->tete;

    L->tete = tete->next;
    L->nbelem -= 1;

    free(tete);
}

void imprimer_liste_point (struct liste_point* L) {
    struct maillon_point* M;
    int i;

    printf("[");
    M = L->tete;
    for (i = 0; i < L->nbelem; i++) {
        if (i != L->nbelem - 1)
            printf ("\n'%c' (%f, %f),", M->value.ident, M->value.x, M->value.y);
        else
            printf ("\n'%c' (%f, %f)\n", M->value.ident, M->value.x, M->value.y);
        M = M->next;
    }

    printf("]\n");
}
