#include <assert.h>
#include <stdbool.h>
#include <stdlib.h>
#include <math.h>
#include <stdio.h>
#include "liste_point.h"

#define N 10
#define MAX_COORDINATES 100
#define SCENARIO 78738

int main() {
    struct maillon_point *current;
    struct point T[N], cour, prec;
    struct liste_point L;
    FILE* f;
    int i;

    srand48(SCENARIO);

    /* 
    * On crée N points. Le point A est en (0,0).
    * On les enregistre dans "points.dat"
    */
    init_point(&T[0], 0, 0, 'A');

    for (i = 1; i < N; i++) {
        double x = drand48 () * MAX_COORDINATES;
        double y = drand48 () * MAX_COORDINATES;
        init_point(&T[i], x, y, 'A' + i);
    }

    f = fopen("points.dat", "w");
    assert(f != NULL);

    for (i = 0; i < N; i++)
        fprintf(f, "%f %f %c\n", T[i].x, T[i].y, T[i].ident);

    fclose (f);

    /* 
    * On trie T [1 .. N-1] par angle croissant.
    * On s'assure qu'il n'y a pas deux points alignés.
    */
    qsort(T+1, N-1, sizeof(struct point), &compare_points);
    for (i = 1; i < N-1; i++)
        assert(compare_points(&T[i], &T[i+1]) != 0);

    // Affichage de la liste des points triés par angle croissant
    for (i = 0; i < N; i++)
        printf("%c ", T[i].ident);
    printf("\n");

    /*
    * Boucle principal de l'algorithme de Graham
    */
    // Initialisation de la pile vide
    init_liste_point(&L);
    // Empilement de T[0] et T[1]
    ajouter_en_tete_liste_point(&L, T[0]);
    ajouter_en_tete_liste_point(&L, T[1]);

    i = 2;
    while (i < N) {
        // Récupération de cour et prec
        cour = L.tete->value;
        prec = L.tete->next->value;
        // Calcul si on tourne à gauche
        if (tourne_a_gauche(&prec, &cour, &T[i])) {
            // Empilement de T[i]
            ajouter_en_tete_liste_point(&L, T[i]);
            // Incrementation de i
            i++;
        }
        // Sinon
        else {
            // On dépile un point
            extraire_tete_liste_point(&L);
        }
    }

    // Affichage de la pile en fin de programme principal
    imprimer_liste_point(&L);

    f = fopen("enveloppe.dat", "w");
    assert(f != NULL);

    // On ajoute 2 fois l'origine pour relier tous les points entre eux
    fprintf(f, "%f %f \n", T[0].x, T[0].y);
    // Pour tous les points de la pile, on ajoute ses coordonnées
    current = L.tete;
    for (i = 0; i < L.nbelem; i++) {
        fprintf(f, "%f %f\n", current->value.x, current->value.y);
        current = current->next;
    }

    fclose (f);
    // Vidage de la mémoire
    clear_liste_point(&L);

    return 0;
}
