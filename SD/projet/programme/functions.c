/* functions.c */
#include "functions.h"
#include <stdlib.h>
#include <stdio.h>

int *tour_geant(struct donnees G, int d) {
    int *T, i, index, current, mark[G.n];

    // Initialisation du tableau des sommets marqués à 0
    for (i=0; i < G.n; i++)
        mark[i] = 0;

    // Création du tableau T
    T = malloc(G.n * sizeof(int));

    // Le premier sommet exploré est le sommet de départ
    T[0] = d;
    i = 0;

    // Tant qu'il reste un sommet non exploré
    while (i < G.n) {
        current = T[i];
        // On le marque à Vrai dans mark
        mark[current] = 1;
        // Récupération du successeur le plus proche
        index = min_distance(G.distances[current], mark, G.n, current);
        // Ajout dans T
        i++;
        T[i] = index;
    }

    return T;
}

int min_distance(float *distances, int *mark, int n, int current) {
    int i, res;
    float dist;

    res = -1;
    dist = -1;

    for (i=1; i < (n+1); i++) {
        if (current != i && mark[i] != 1 && (dist == -1 || (distances[i] != -1 && distances[i] < dist))) {
            res = i;
            dist = distances[i];
        }
    }

    return res;
}

struct liste * split(int *T, struct donnees D) {
    struct liste *succ;
    int load, i, j, dep;
    float cost;
    
    // Indice du dépot dans la matrice dist
    dep = 0;

    // Allocation dynamique du tableau de liste chainée
    succ = malloc((D.n + 1) * sizeof(struct liste));
    // Initialisation de la liste chainée des successeurs du sommet i
    init_tab_liste_succ(succ, D.n + 1);
    
    for (int i=1; i <= D.n; i++) {
        j = i;
        load = 0;
        
        while (j <= D.n && load < D.Q) {
            load += D.q[T[j-1]-1];
            
            if (i == j)
                // T[j] est le 1er client qui sera livré par le véhicule courant
                cost = D.distances[dep][T[i-1]] + D.distances[T[i-1]][dep];
            else
                // T[j] est ajouté en fin de livraison du véhicule courant
                cost = cost - D.distances[T[j-2]][dep] + D.distances[T[j-2]][T[j-1]] + D.distances[T[j-1]][dep];
            
            //La livraison ne peut pas être assurée par le véhicule courant car limite de capacité
            // cout non stocké car déjà dans la matrice dist
            if (load <= D.Q) {
                ajout_liste_succ(&succ[i-1], j, cost);
            }
        
            j += 1;
        }
    }

    return succ;
}