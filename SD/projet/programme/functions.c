/* functions.c */
#include <stdlib.h>
#include "functions.h"

int *tour_geant(struct donnees G, int d) {
    int *T, i, index, current, mark[G.n];

    // Initialisation du tableau des sommets marqués à 0
    for (i=0; i < G.n; i++)
        mark[i] = 0;

    // Création de la matrice T
    T = malloc(G.n * sizeof(int));

    // Premier sommet exploré le sommet de départ
    T[0] = d;
    i = 0;

    // TODO: n ou n - 1 ?
    // Tant qu'un sommet n'est pas exploré
    while (i < G.n) {
        current = T[i];
        // Récupération du successeur le plus proche
        index = min_distance(G.distances[current], mark, G.n);
        // Ajout dans T
        i++;
        T[i] = index;
        // On le marque à Vrai dans mark
        mark[index] = 1;
    }

    return T;
}

int min_distance(float *distances, int *mark, int n) {
    int i, res;

    res = -1;

    for (i=0; i < n; i++) {
        if (res == -1 || (distances[i] != -1 && distances[i] < res))
            res = i;
    }

    return res;
}