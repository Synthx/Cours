#include <stdio.h>
#define TAILLE_MAX 100

void saisie(FILE* fic, int n) {
    char name[TAILLE_MAX];
    int i;

    for (i=0; i<n; i++) {
        scanf("%s", name);
        fprintf(fic, "%s\t", name);
    }
}

int main() {
    FILE* file;
    int n;

    printf("Saisir le nombre de noms à rentrer :\n");
    scanf("%d", &n);

    // Ouverture du fichier en mode écriture
    file = fopen("names.txt", "w");
    if (file != NULL) {
        // Appel de la fonction
        saisie(file, n);
        // Fermeture du fichier
        fclose(file);
    }

    return 0;
}