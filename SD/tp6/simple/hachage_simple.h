/* hachage_simple.h */

#include "liste_double.h"
#include <stdbool.h>
#define N 10

struct alveole {
    struct liste_double L;
};

typedef int fonction_hachage(double);

struct table {
    struct alveole tab[N];
    fonction_hachage* hash;
};

extern void init_table(struct table*, fonction_hachage*);
extern void clear_table(struct table*);
extern void enregistrer_table(struct table*, double);
extern bool rechercher_table(struct table*, double);
extern void imprimer_table(struct table*);
