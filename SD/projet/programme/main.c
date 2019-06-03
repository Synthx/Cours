/* main.c */
#include "utils.h"

int main() {
    struct donnees D; /* Structure contenant les données du problème */
    struct liste *H;
    struct result res;
    int *T, d; /* Résultat du Tour Géant */
    
    // Initialisation de D avec les données du fichier
    init_data(&D, &d);
    
    // Création du tour géant
    T = tour_geant(D, d);

    // Création du sous graphe grâce à la procédure SPLIT
    H = split(T, D);

    // Application du PCC
    bellman(H, D.n, &res);

    // Affichage du résultat
    display_res(res, T);

    // Libération de la mémoire
    free_donnees_tab(D);
    free_tab_liste_succ(H, D.n + 1);
    free_result_tab(&res);
    free(T);
    
    return 0;
}