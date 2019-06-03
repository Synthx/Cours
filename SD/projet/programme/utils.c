/* utils.c */
#include "utils.h"

void init_data(struct donnees *D, int *d) {
    char filename[MAX];
    FILE *file;

    // Fichier de données
    printf("Fichier avec lequel lancer le programme:\n");
    scanf("%s", filename);

    // Ouverture du fichier contenant les données
    file = fopen(filename, "r");

    if (file != NULL) {
        // Lecture du fichier de données
        read_data(file, D);

        // Client de départ pour effectuer l'algorithme
        printf("\nClient de départ:\n");
        scanf("%d", d);

        // Fermeture du fichier contenant les données
        fclose(file);
    } else {
        printf("\nImpossible d'ouvrir le fichier '%s'\n", filename);
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

void display_res(struct result res, int *T) {
    struct maillon_point *current;
    int i;

    printf("\nTrajet: [\n");

    current = res.tete;
    while (current != NIL_POINT) {
        printf("(");
        for (i=current->depart - 1; i <= current->finish - 1; i++) {
            if (i == current->finish - 1)
                printf("%d", T[i]);
            else
                printf("%d ", T[i]);
        }
        printf(")\n");

        current = current->next;
    }

    printf("]\n");
    printf("Cout total: %f\n", res.cost);
}