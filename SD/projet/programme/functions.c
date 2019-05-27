/* functions.c */
#include <stdlib.h>
#include "functions.h"

int *tour_geant(struct donnees G, int d) {
    int *T, i, index, current, mark[G.n];

    // Initialisation du tableau des sommets marqués à 0
    for (i=0; i < G.n; i++)
        mark[i] = 0;

    // Création de la matrice T
    T = malloc(G.n * sizeof(int));

    // Premier sommet exploré le sommet de départ
    T[0] = d;
    i = 0;

    // TODO: n ou n - 1 ?
    // Tant qu'un sommet n'est pas exploré
    while (i < G.n) {
        current = T[i];
        // Récupération du successeur le plus proche
        index = min_distance(G.distances[current], mark, G.n);
        // Ajout dans T
        i++;
        T[i] = index;
        // On le marque à Vrai dans mark
        mark[index] = 1;
    }

    return T;
}

int min_distance(float *distances, int *mark, int n) {
    int i, res;

    res = -1;

    for (i=0; i < n; i++) {
        if (res == -1 || (distances[i] != -1 && distances[i] < res))
            res = i;
    }

    return res;
}

struct liste * split(int *T, int Q, int n, float **dist, int *q) {
    struct liste *succ;
    int load, dep;
    float cost;

    // Allocation dynamique du tableau de liste chainée
    succ = malloc(n * sizeof(struct liste));
    // Indice du dépot dans la matrice dist
    dep = 0;
    
    for(int i=0; i < n; i++) {
        int j = i;
        load = 0;
        
        while(j > n || load >= Q) {
            
            load += q[T[j]];
            
            init_liste_succ(struct liste* head[i]);
            
            if(i==j){
                // T[j] est le 1er client qui sera livré par le véhicule courant
                cost = dist[dep][T[i]] + dist[T[i]][dep]; //NB : distance aller = distance retour
            } else {
                // T[j] est ajouté en fin de livraison du véhicule courant
                cost += -dist[T[j-1]][dep] + dist[T[j-1]][T[j]] + dist[T[j]][dep];
            }
            
            if (load<=Q){
                //La livraison ne peut pas être assurée par le véhicule courant car limite de capacité
                // cout non stocké car déjà dans la matrice dist
                
                ajout_maillon(head[i-1], j);
            }
        
        j += 1;
        
        }
    }
}
    
struct resultat bellman(struct graphe H, int r, int n){
    
    int pere[n+1] = { 0 };
    int pot[n+1] = { -1 };
    int i;
    struct maillon* M;
    struct resultat res;
    
    pot[r] = 0;
    pere[r] = r;
    
    M = H->head[r]->tete;
    
    for(i=0; i < H->head[r]->nbelem ; i++){

        pot[M->value] = dist[0][M->value];
        pere[M->value] = r;
        M = M->next;
            
    }
    
    /* boucle sur les couches (couche 0 déjà réalisée) */
    for(i=1; i<n+1; i++){
            /* l'étape pour  parcourir tous les sommets de la couche est facultative car nous avons un seul et unique sommet par couche */
            
        for(j=0; j < H->head[i]->nbelem ; j++){
            if(pot[i] + dist[i][M->value] < pot[M->value]){
                pot[M->value] = pot[i] + dist[i][M->value];
                pere[M->value] = i;
                M = M->next;
                
            }
        }
        
    }
    res->pot = pot;
    res->pere = pere;
    res->cout = pot[n];
    
    return res;
}
