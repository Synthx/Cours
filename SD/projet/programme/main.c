/* main.c */
#include <stdio.h>
#include <stdlib.h>
#include "functions.h"

void init_data(struct donnees *, int *);
void read_data(FILE *, struct donnees *);
void display_T(int *, int);
void display_H(struct liste *, int);

int main() {
    struct donnees D; /* Structure contenant les données du problème */
    struct liste *H;
    int *T, d; /* Résultat du Tour Géant */
    
    // Initialisation de D avec les données du fichier
    init_data(&D, &d);
    
    // Création du tour géant
    T = tour_geant(D, d);
    display_T(T, D.n);

    // Création du sous graphe grâce à la procédure SPLIT
    H = split(T, D);
    display_H(H, D.n);

    // Création du résultat
    printf("Cout total : %f\n", bellman(H, d-1, D.n));

    // Libération de la mémoire
    free_donnees_tab(D);
    free_tab_liste_succ(H, D.n + 1);
    free(T);
    
    return 0;
}

void init_data(struct donnees *D, int *d) {
    char *filename;
    FILE *file;

    // Fichier de données
    printf("Fichier avec lequel lancer le programme :\n");
    //scanf("%s", filename);

    filename = "exemple.dat";

    // Ouverture du fichier contenant les données
    file = fopen(filename, "r");

    if (file != NULL) {
        // Lecture du fichier de données
        read_data(file, D);

        // Client de départ pour effectuer l'algorithme
        printf("Client de depart :\n");
        scanf("%d", d);

        // Fermeture du fichier contenant les données
        fclose(file);
    } else {
        printf("Impossible d'ouvrir le fichier '%s'\n", filename);
        // Arret du programme
        exit(1);
    }
}

void read_data(FILE *file, struct donnees *D) {
    int i, j, size;

    // Nombre de clients
    fscanf(file, "%d", &D->n);

    init_donnees_tab(D, D->n);

    // Capacité maximale des camions
    fscanf(file, "%d", &D->Q);

    // Besoin de chaque clients
    for (i=0; i < D->n; i++)
        fscanf(file, "%d", &D->q[i]);

    // Distance entre chaque clients
    size = D->n + 1;
    for (i=0; i < size; i++) {
        for (j=0; j < size; j++) {
            fscanf(file, "%f", &D->distances[i][j]);

            if (D->distances[i][j] == 0)
                D->distances[i][j] = -1;
        }
    }
}

void display_H(struct liste *H, int n) {
    printf("Résultat du SPLIT :\n");

    for (int i=0; i <= n; i++) {
        printf("Sommet %d: ", i);

        struct liste L = H[i];
        struct maillon *current = L.tete;

        while (current != NIL) {
            printf("(%d: %f) ", current->value, current->cost);
            current = current->next;
        }

        printf("\n");
    }
}

void display_T(int *T, int n) {
    printf("Résultat du grand tour :\n");
    for (int i=0; i < n; i++)
        printf("%d ", T[i]);

    printf("\n");
}