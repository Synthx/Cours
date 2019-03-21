// liste_char.c
#include <stdio.h>
#include <stdlib.h>
#include "liste_char.h"

void init_liste_char(struct liste_char* L) {
    L->tete = (struct maillon*)0;
    L->nbelem = 0;
}

void clear_liste_char(struct liste_char* L) {
    struct maillon* current;
    struct maillon* next;
    int i;
    
    current = L->tete;
    
    for (i = 0; i < L->nbelem; i++) {
        next = current->next;
        free(current);
        current = next;
    }
}

void ajouter_fin_liste_char(struct liste_char* L, char c) {
    struct maillon* M;
    struct maillon* N;
    
    M = (struct maillon*)malloc(sizeof(struct maillon));
    M->value = c;
    M->next = NIL;
    if (L->nbelem == 0){
        L->tete=M;
    }else{
        N=L->tete;
        while (N->next != NIL){
            N=N->next;
        }
        N->next=M;
    }
    
    L->nbelem++;
}

void imprimer_liste_char(struct liste_char* L) {
    struct maillon* maillon;
    int i;
    
    maillon = L->tete;
    
    for (i = 0; i < L->nbelem; i++) {
        printf("%c", maillon->value);
        maillon = maillon->next;
    }
    
    printf("\n");
}
