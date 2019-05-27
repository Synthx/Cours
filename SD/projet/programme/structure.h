/* structure.h */

struct donnees {
    int n, Q, *q;
    float **distances;
};

struct resultat {
    int cout_total;
};

struct maillon {
    int value;
    float cost;
    struct maillon *next;
};

#define NIL (struct maillon *)0

struct liste {
    struct maillon *tete;
    int nb_elem;
};

extern void init_donnees_tab(struct donnees *, int);
extern void free_donnees_tab(struct donnees);
extern void init_tab_liste_succ(struct liste *, int);
extern void free_tab_liste_succ(struct liste *, int);
extern void init_liste_succ(struct liste *);
extern void ajout_liste_succ(struct liste *, int, float);