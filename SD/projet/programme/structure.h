/* structure.h */

struct donnees {
    int n, Q, *q;
    float** distances;
};

struct resultat {
    int cout_total;
};

extern void init_donnees_tab(struct donnees *, int);
extern void free_donnees_tab(struct donnees);