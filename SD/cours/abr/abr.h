// abr.h

struct abr {
    struct abr* gauche;
    double valeur;
    struct abr* droit;
};

#define NIL (struct abr*)0

#include <stdbool.h>

extern bool recherche_abr (double, struct abr*);

extern struct abr* ajout_abr (double, struct abr*);
