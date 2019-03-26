//main.c

#include "abr.h"
#include <stdio.h>
#include <stdlib.h>

int main() {
    struct abr* racine;
    int x;

    racine = NIL;

    scanf("%d", &x);
    while (x != -1) {
        racine = ajouter_abr(x, racine);
        scanf("%d", &x);
    }

    afficher_dot_abr(racine);
    printf("\n");

    printf("La hauteur de l'ABR est %d\n", hauteur_abr(racine));
    printf("Le nombre de noeuds de l'ABR est %d\n", nombre_noeuds_abr(racine));

    clear_abr(racine);

    system("dot -Tpdf abr.dot -Grankdir=LR -o abr.pdf");

    return 0;
}