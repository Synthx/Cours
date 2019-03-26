#include <stdio.h>

int main(int argc, char *argv[]) {
    FILE *src, *dest;
    char c;

    if (argc != 3)
        printf("Nombre d'arguments invalides.\n");
    // Ouverture du fichier source en lecture
    else if ((src = fopen(argv[1], "r")) == NULL)
        printf("Impossible d'ouvrir le fichier source.\n");
    // Ouverture du fichier destinataire en ecriture
    else if ((dest = fopen(argv[2], "w")) == NULL)
        printf("Impossible d'ouvrier le fichier destinataire.\n");
    else {
        // Copie caractere par caractere
        while ((c = getc(src)) != EOF)
            fputc(c, dest);

        // Fermeture des fichiers precedemment ouverts
        fclose(src);
        fclose(dest);
    }

    return 0;
}