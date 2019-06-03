// grandeur.h

#if ! defined (GRANDEUR_H)
#define GRANDEUR_H 1

#include <stdbool.h>
#include "liste_dimension.h"

#define GDIM 32

/*
 * Le type struct grandeur permet de représenter une grandeur
 * munie d'une dimension.
 *
 * Exemple : la grandeur niv_min de dimension megawatt/generateur peut
 * être codée par { ident = "niv_min", 
 *                  dim = [ { "megawatt", 1 }, { "generateur", -1 } ]
 *                }
 */
 
struct grandeur {
    char ident [GDIM];
    struct liste_dimension dim;
};

extern void init_grandeur (struct grandeur* G);

extern void clear_grandeur (struct grandeur* G);

extern void set_grandeur (struct grandeur* dst, struct grandeur* src);

extern void set_grandeur_ident_produit 
    (struct grandeur* G, char* ident, 
     struct liste_dimension*, struct liste_dimension*);

extern bool read_grandeur (struct grandeur* G);

extern void print_grandeur (struct grandeur* G);

#endif

