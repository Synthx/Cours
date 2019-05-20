/* structure.c */
#include "structure.h"
#include <stdlib.h>

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