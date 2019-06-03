#include <stdio.h>
#include <string.h>
#include "grandeur.h"

/*
 * Constructeur
 */

void init_grandeur (struct grandeur* G)
{
    G->ident [0] = '\0';
    init_liste_dimension (&G->dim);
}

/*
 * Destructeur
 */

void clear_grandeur (struct grandeur* G)
{
    clear_liste_dimension (&G->dim);
}

/*
 * Affecte src (donnée) à dst (résultat)
 */

void set_grandeur (struct grandeur* dst, struct grandeur* src)
{
    if (dst != src)
    {   strcpy (dst->ident, src->ident);
        set_liste_dimension (&dst->dim, &src->dim);
    }
}

/*
 * Affecte à G la grandeur nommée ident avec, pour dimension,
 * le produit des dimensions A et B.
 */
void set_grandeur_ident_produit 
    (struct grandeur* G, char* ident, 
     struct liste_dimension* A, struct liste_dimension* B)
{
    strcpy (G->ident, ident);
    set_liste_dimension_produit (&G->dim, A, B);
}

/*
 * Lit une grandeur sur l'entrée standard et l'affecte à G
 */

bool read_grandeur (struct grandeur* G)
{   char buffer [GDIM];
    scanf ("%s", buffer);
    if (strcmp (buffer, "quit") == 0)
        return false;
    strcpy (G->ident, buffer);
    read_liste_dimension (&G->dim);
    return true;
}

/*
 * Affiche G sur la sortie standard
 */

void print_grandeur (struct grandeur* G)
{
    printf ("%s # ", G->ident);
    print_liste_dimension (&G->dim);
    printf ("\n");
}


