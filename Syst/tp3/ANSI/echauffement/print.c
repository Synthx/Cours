#include <stdio.h>
#define TAILLE_MAX 100

void affichage(FILE* fic, int n)
{
    char str[TAILLE_MAX];
    int i;

    for (i=0; i<n; i++) {
        fscanf(fic, "%s\t", str);
        printf("%s\n", str);
    }
}

int main()
{
    FILE* file;
    int n;

    printf("Nombre de noms à lire :\n");
    scanf("%d", &n);

    // Ouverture du fichier en mode lecture
    file = fopen("names.txt", "r");
    if (file != NULL) {
        // Appel à la fonction
        affichage(file, n);
        // Fermeture du fichier
        fclose(file);
    }

    return 0;
}