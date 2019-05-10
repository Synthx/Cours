/* hachage_double.h */

#include <stdbool.h>
#define N 11

struct valeur_hachage {
    int h1;
    int h2;
};

typedef struct valeur_hachage fonction_hachage(int);

enum etat_alveole { libre, occupe };

struct alveole {
    enum etat_alveole etat;
    double valeur;
};

struct table {
    struct alveole tab[N];
    fonction_hachage* h;
    int n;
};

extern void init_table(struct table*, fonction_hachage*);
extern void enregistrer_table(struct table*, double);
extern bool rechercher_table(struct table*, double);
extern void imprimer_table(struct table*);
