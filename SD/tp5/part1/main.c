//main.c

#include "abr.h"
#include <stdio.h>
#include <stdlib.h>

int main() {
    struct abr* racine;
    int x;

    // Initialisation de l'ABR
    racine = NIL;

    // Lecture des valeurs à ranger dedans
    scanf("%d", &x);
    while (x != -1) {
        racine = ajouter_abr(x, racine);
        scanf("%d", &x);
    }

    // Affichage des informations de l'ABR
    printf("La hauteur de l'ABR est %d\n", hauteur_abr(racine));
    printf("Le nombre de noeuds de l'ABR est %d\n", nombre_noeuds_abr(racine));

    // Génération du fichier abr.dot
    generer_dot_abr(racine);

    // Vidage de la mémoire
    clear_abr(racine);

    // Génération de la prévisualisation de l'ABR
    system("dot -Tpdf abr.dot -Grankdir=LR -o abr.pdf");
    system("evince abr.pdf");

    return 0;
}