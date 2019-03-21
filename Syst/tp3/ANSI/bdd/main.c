#include <stdio.h>

void ajouter_eleve(FILE* fic)
{
    float moyenne;
    char nom[100];

    printf("Saisir les informations de l'eleves :\n");
    scanf("%s %f", nom, &moyenne);

    fprintf(fic, "%s %f\n", nom, moyenne);
}

void lire_eleves(FILE* fic)
{
    float moyenne;
    char nom[100];

    while (fscanf(fic, "%s %f\n", nom, &moyenne) != EOF) {
        printf("%s a une moyenne de %f\n", nom, moyenne);
    }
}

float calcul_moyenne(FILE* fic)
{
    char nom[100];
    float total=0, moyenne;
    int n=0;

    while (fscanf(fic, "%s %f\n", nom, &moyenne) != EOF) {
        total += moyenne;
        n++;
    }

    return n != 0 ? total / n : 0 ;
}

int occur_char(FILE* fic, char c)
{
    int compteur=0, current;

    current = getc(fic);

    while (current != EOF) {
        if (current == c)
            compteur++;
        current = getc(fic);
    }

    return compteur;
}

int main()
{
    FILE* file;

    // file = fopen("eleves.txt", "a");
    // ajouter_eleve(file);
    // fclose(file);

    // file = fopen("eleves.txt", "r");
    // lire_eleves(file);
    // fclose(file);

    // file = fopen("eleves.txt", "r");
    // printf("\nLa moyenne de la classe est de %f\n", calcul_moyenne(file));
    // fclose(file);

    file = fopen("eleves.txt", "r");
    char c = 'o';
    printf("Le nombre de caractère '%c' dans le fichier est de %d\n", c, occur_char(file, c));
    fclose(file);

    return 0;
}