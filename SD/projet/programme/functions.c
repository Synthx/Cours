/* functions.c */
#include "functions.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

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
    while (i < G.n -1) {
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
        if (current != i && !mark[i] && (dist == -1 || (distances[i] != -1 && distances[i] < dist))) {
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
    
    for (i=1; i <= D.n; i++) {
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
            if (load <= D.Q)
                ajout_liste_succ(&succ[i-1], i, j, cost);
        
            j += 1;
        }
    }

    return succ;
}

void bellman(struct liste* H, int n, struct result *res) {
    int i, j, pere[n+1], start[n], finish[n];
    struct maillon *M;
    float pot[n+1];

    // Initialisation
    for (i=0; i <= n; i++) {
        pere[i] = 0;
        pot[i] = -1;
    }

    pot[0] = 0;
    pere[0] = -1;

    // Propagation aux successeurs de la racine
    M = H[0].tete;
    while (M != NIL) {
        i = M->value;
        pot[i] = M->cost;
        pere[i] = 0;
        finish[i] = i;
        start[i] = M->start;

        M = M->next;
    }

    // Boucle principale
    for (i=1; i <=n; i++) {
        // i stocke l'indice du sommet qu'on propage
        M = H[i].tete;
        // Tant qu'on trouve un successeur
        while (M != NIL) {
            // j stocker l'indice du successeur
            j = M->value;
            // Si potentiel à -1 ou inférieur à celui stocké
            if (pot[j] == -1 || pot[j] > pot[i] + M->cost) {
                pot[j] = pot[i] + M->cost;
                pere[j] = i;
                finish[j] = j;
                start[j] = M->start;
            }
            // Passage au successeur suivant
            M = M->next;
        }
    }

    init_result_tab(res);
    // Sauvegarde du cout total
    res->cost = pot[n];
    // Sauvegarde du trajet
    i = n;
    while (pere[i] != -1) {
        ajout_result_tab(res, start[i], finish[i]);
        i = pere[i];
    }
}