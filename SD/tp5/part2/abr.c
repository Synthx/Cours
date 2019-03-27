//abr.c

#include "abr.h"
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

struct couple* new_couple(wstring clef, wstring satellite) {
    struct couple* c;

    c = malloc(sizeof(struct couple));
    wcscpy(c->clef, clef);
    wcscpy(c->satellite, satellite);

    return c;
}

struct abr* new_feuille(wstring clef, wstring satellite) {
    struct abr* A;

    A = malloc(sizeof(struct abr));
    A->valeur = new_couple(clef, satellite);
    A->gauche = NIL;
    A->droit = NIL;

    return A;
}

struct abr* ajouter_abr(wstring clef, wstring satellite, struct abr* A) {
    // Si A est vide, on crée une feuille
    if (A == NIL)
        return new_feuille(clef, satellite);
    // Sinon on ajoute X à droite ou à gauche récursivement
    else {
        if (wcscmp(A->valeur->clef, clef) < 0)
            A->gauche = ajouter_abr(clef, satellite, A->gauche);
        else if (wcscmp(A->valeur->clef, clef) > 0)
            A->droit = ajouter_abr(clef, satellite, A->droit);

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
        wprintf(L"(%ls:%ls) ", A->valeur->clef, A->valeur->satellite);
        afficher_abr(A->droit);
    }
}

void affdot2(FILE* f, struct abr* A) {
    // ABR enfant à gauche
    if (A->gauche != NIL) {
        fwprintf(f, L"\t\"%ls\" -> \"%ls\" [label=\"gauche\"];\n", A->valeur->clef, A->gauche->valeur->clef);
        affdot2(f, A->gauche);
    }
    // ABR enfant à droite
    if (A->droit != NIL) {
        fwprintf(f, L"\t\"%ls\" -> \"%ls\" [label=\"droit\"];\n", A->valeur->clef, A->droit->valeur->clef);
        affdot2(f, A->droit);
    }
}

void generer_dot_abr(struct abr* A) {
    FILE* file;

    file = fopen("abr.dot", "w");
    fwprintf(file, L"digraph G {\n");

    if (A == NIL)
        fwprintf(file, L"\tNIL;\n");
    else if (est_feuille(A))
        fwprintf(file, L"\t%ls:%ls;\n", A->valeur->clef, A->valeur->satellite);
    else
        affdot2(file, A);

    fwprintf(file, L"}\n");
    fclose(file);
}

void clear_abr(struct abr* A) {
    if (A != NIL) {
        clear_abr(A->gauche);
        clear_abr(A->droit);
        free(A->valeur);
        free(A);
    }
}

wchar_t* rechercher_abr(struct abr* A, wstring clef) {
    if (A == NIL)
        return (wchar_t*)0;
    else if (wcscmp(A->valeur->clef, clef) == 0)
        return A->valeur->satellite;
    else if (wcscmp(A->valeur->clef, clef) < 0)
        return rechercher_abr(A->gauche, clef);
    else if (wcscmp(A->valeur->clef, clef) > 0)
        return rechercher_abr(A->droit, clef);
}