#include <stdio.h>

void ajouter_eleve(FILE* fic) {
    float moyenne;
    char nom[100];

    printf("Saisir les informations de l'eleves :\n");
    scanf("%s %f", nom, &moyenne);

    fprintf(fic, "%s %f\n", nom, moyenne);
}

void lire_eleves(FILE* fic) {
    float moyenne;
    char nom[100];

    while (fscanf(fic, "%s %f\n", nom, &moyenne) != EOF) {
        printf("%s a une moyenne de %f\n", nom, moyenne);
    }
}

float calcul_moyenne(FILE* fic) {
    char nom[100];
    float total=0, moyenne;
    int n=0;

    while (fscanf(fic, "%s %f\n", nom, &moyenne) != EOF) {
        total += moyenne;
        n++;
    }

    return n != 0 ? total / n : 0 ;
}

int eleve_au_dessus_moyenne(FILE* fic, float moyenne) {
    char nom[100];
    float moy;
    int compteur=0;

    while (fscanf(fic, "%s %f\n", nom, &moy) != EOF) {
        if (moy > moyenne)
            compteur++;
    }

    return compteur;
}

int occur_char(FILE* fic, char c) {
    int compteur=0, current;

    current = getc(fic);

    while (current != EOF) {
        if (current == c)
            compteur++;
        current = getc(fic);
    }

    return compteur;
}

int main() {
    double moyenne;
    FILE* file;

    // file = fopen("eleves.txt", "a");
    // ajouter_eleve(file);
    // fclose(file);

    // Ouverture du fichier en mode lecture
    file = fopen("eleves.txt", "r");

    // Affichage de la base de donnee
    lire_eleves(file);

    // Reset de la position du reader au debut du fichier
    fseek(file, 0, SEEK_SET);

    // Calcul de la moyenne de la classe
    moyenne = calcul_moyenne(file);
    printf("La moyenne de la classe est de %f\n", moyenne);

    fseek(file, 0, SEEK_SET);

    // Calcul du nombre d'occurences d'un caractere donne dans le fichier
    char c = 'o';
    printf("Le nombre de caractère '%c' dans le fichier est de %d\n", c, occur_char(file, c));

    fseek(file, 0, SEEK_SET);

    // Calcul du nombre d'élève ayant une moyenne > à la moyenne de la classe
    printf("Nombre d'eleve dont la note est superieure a la moyenne de la classe est de %d\n", eleve_au_dessus_moyenne(file, moyenne));
    
    // Fermeture du fichier
    fclose(file);

    return 0;
}