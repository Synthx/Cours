//abr.c

#include "abr.h"
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

struct abr* new_feuille(int x) {
    struct abr* A;

    A = malloc(sizeof(struct abr));
    A->valeur = x;
    A->gauche = NIL;
    A->droit = NIL;

    return A;
}

struct abr* ajouter_abr(int x, struct abr* A) {
    // Si A est vide, on crée une feuille
    if (A == NIL)
        return new_feuille(x);
    // Sinon on ajoute X à droite ou à gauche récursivement
    else {
        if (A->valeur > x)
            A->gauche = ajouter_abr(x, A->gauche);
        else
            A->droit = ajouter_abr(x, A->droit);

        return A;
    }
}

int nombre_noeuds_abr(struct abr* A) {
    if (A != NIL)
        return 1 + nombre_noeuds_abr(A->gauche) + nombre_noeuds_abr(A->droit);
    else
        return 0;
}

int max(int a, int b) {
    return a > b ? a : b ;
}

int hauteur_abr(struct abr* A) {
    if (A == NIL)
        return -1;
    else
        return 1 + max(hauteur_abr(A->gauche), hauteur_abr(A->droit));
}

bool est_feuille(struct abr* A) {
    return A != NIL && A->gauche == NIL && A->droit == NIL;
}

void afficher_abr(struct abr* A) {
    if (A != NIL) {
        afficher_abr(A->gauche);
        printf("%d ", A->valeur);
        afficher_abr(A->droit);
    }
}

void affdot2(FILE* f, struct abr* A) {
    // ABR enfant à gauche
    if (A->gauche != NIL) {
        fprintf(f, "\t%d -> %d [label=\"gauche\"];\n", A->valeur, A->gauche->valeur);
        affdot2(f, A->gauche);
    }
    // ABR enfant à droite
    if (A->droit != NIL) {
        fprintf(f, "\t%d -> %d [label=\"droit\"];\n", A->valeur, A->droit->valeur);
        affdot2(f, A->droit);
    }
}

void generer_dot_abr(struct abr* A) {
    FILE* file;

    file = fopen("abr.dot", "w");
    fprintf(file, "digraph G {\n");

    if (A == NIL)
        fprintf(file, "\tNIL;\n");
    else if (est_feuille(A))
        fprintf(file, "\t%d;\n", A->valeur);
    else
        affdot2(file, A);

    fprintf(file, "}\n");
    fclose(file);
}

void clear_abr(struct abr* A) {
    if (A != NIL) {
        clear_abr(A->gauche);
        clear_abr(A->droit);
        free(A);
    }
}