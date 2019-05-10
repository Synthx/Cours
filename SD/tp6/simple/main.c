/* main.c */

#include <stdio.h>
#include "hachage_simple.h"

int hachage_basique(double d) {
    return (int)d % N;
}

int main () {
    struct table T;
    double x, recherche = 10;

    // Initialisation
    init_table(&T, &hachage_basique);
    
    scanf("%lf", &x);
    while (x != -1) {
        // Enregistrement dans le dictionnaire
        enregistrer_table(&T, x);
        scanf("%lf", &x);
    }

    // Impression
    printf("Impression du dictionnaire :\n");
    imprimer_table(&T);
    
    // Test si la valeur 10 est présente dans le dictionnaire
    if (rechercher_table(&T, recherche))
        printf("\nLa valeur %lf est présente dans le dictionnaire.\n", recherche);
    else
        printf("\nLa valeur %lf n'est pas présente dans le dictionnaire.\n", recherche);
    
    // Libération de la mémoire
    clear_table(&T);
    
    return 0;
}
