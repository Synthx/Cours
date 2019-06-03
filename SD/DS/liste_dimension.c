#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>
#include <stdbool.h>
#include "liste_dimension.h"

/*
 * Constructeur
 */

void init_liste_dimension (struct liste_dimension* L)
{
    L->nbelem = 0;
    L->tete = NIL_DIM;
    L->queue = NIL_DIM;
}

/*
 * Destructeur
 */

void clear_liste_dimension (struct liste_dimension* L)
{
    struct maillon_dimension* M;
    struct maillon_dimension* N;

    M = L->tete;
    while (M != NIL_DIM)
    {   N = M->suivant;
        free (M);
        M = N;
    }
}

/*
 * Ajoute dim (donnée) en queue de L (donnée/résultat)
 */

static void ajout_en_queue_liste_dimension 
        (struct liste_dimension* L, struct dimension* dim)
{   struct maillon_dimension* M;
    struct maillon_dimension* N;

    M = (struct maillon_dimension*)malloc (sizeof (struct maillon_dimension));
    M->valeur = *dim;
    M->suivant = NIL_DIM;
    if (L->nbelem == 0)
    {
        L->tete = M;
        L->queue = M;
    } else
    {
        N = L->queue;
        N->suivant = M;
        L->queue = M;
    }
    L->nbelem += 1;
}

/*
 * Affecte src (donnée) à dst (résultat)
 */

void set_liste_dimension 
    (struct liste_dimension* dst, struct liste_dimension* src)
{   struct maillon_dimension* M;

    if (dst != src)
    {   clear_liste_dimension (dst);
        init_liste_dimension (dst);

        for (M = src->tete; M != NIL_DIM; M = M->suivant)
            ajout_en_queue_liste_dimension (dst, &M->valeur);
    }
}

/*
 * Affecte le produit de A et de B (données) à P (résultat).
 */

void set_liste_dimension_produit
    (struct liste_dimension* P, 
     struct liste_dimension* A, 
     struct liste_dimension* B)
{   struct maillon_dimension *M, *N;
    bool found;

    assert (P != A && P != B);

    clear_liste_dimension (P);
    init_liste_dimension (P);

    M = A->tete;
    while (M != NIL_DIM)
    {   N = B->tete;
        found = false;
        while (N != NIL_DIM && !found)
        {   if (strcmp (M->valeur.ident, N->valeur.ident) == 0)
                found = true;
            else
                N = N->suivant;
        }
        if (!found)
            ajout_en_queue_liste_dimension (P, &M->valeur);
        else
        {   struct dimension dim = M->valeur;
            dim.exposant += N->valeur.exposant;
            if (dim.exposant != 0)
                ajout_en_queue_liste_dimension (P, &dim);
        }
        M = M->suivant;
    }
    N = B->tete;
    while (N != NIL_DIM)
    {   M = A->tete;
        found = false;
        while (M != NIL_DIM && !found)
        {   if (strcmp (M->valeur.ident, N->valeur.ident) == 0)
                found = true;
            else
                M = M->suivant;
        }
        if (!found)
            ajout_en_queue_liste_dimension (P, &N->valeur);
        N = N->suivant;
    }
}

/*
 * Lit une dimension sur l'entrée standard et l'affecte à L (résultat)
 */

void read_liste_dimension (struct liste_dimension* L)
{   char buffer [256];
    struct dimension dim;
    int i, j;

    clear_liste_dimension (L);
    init_liste_dimension (L);

    scanf ("%s", buffer);
    if (strcmp (buffer, "1") != 0)
    {   i = 0;
        dim.exposant = 1;
        while (buffer[i] != '\0')
        {   j = 0;
            while (buffer[i] != '*' && buffer[i] != '/' && 
                                buffer[i] != '^' && buffer[i] != '\0')
                dim.ident[j++] = buffer[i++];
            dim.ident[j] = '\0';
            if (buffer[i] == '^')
            {   int e = 0;
                i += 1;
                while (buffer[i] >= '0' && buffer[i] <= '9')
                {   e = e * 10 + buffer[i] - '0';
                    i += 1;
                }
                dim.exposant *= e;
            }
            ajout_en_queue_liste_dimension (L, &dim);
            if (buffer[i] == '*')
                dim.exposant = 1;
            else
                dim.exposant = -1;
            if (buffer[i] != '\0')
                i += 1;
        }
    }
}

/*
 * Affiche L (donnée) sur la sortie standard
 */

void print_liste_dimension (struct liste_dimension* L)
{   struct maillon_dimension* M;
    int nbpos;

    if (L->nbelem == 0)
    {   printf ("sans dimension");
        return;
    }

    nbpos = 0;
    for (M = L->tete; M != NIL_DIM; M = M->suivant)
    {   if (M->valeur.exposant > 0)
        {   if (nbpos > 0)
                printf ("*");
            if (M->valeur.exposant == 1)
                printf ("%s", M->valeur.ident);
            else
                printf ("%s^%d", M->valeur.ident, M->valeur.exposant);
            nbpos += 1;
        }
    }
    if (nbpos == 0)
        printf ("1");
    for (M = L->tete; M != NIL_DIM; M = M->suivant)
    {   if (M->valeur.exposant < 0)
        {   printf ("/");
            if (M->valeur.exposant == -1)
                printf ("%s", M->valeur.ident);
            else
                printf ("%s^%d", M->valeur.ident, - M->valeur.exposant);
        }
    }
}

