/* structure.h */

struct donnees {
    int n, Q, *q;
    float **distances;
};

struct maillon {
    int value, start;
    float cost;
    struct maillon *next;
};

#define NIL (struct maillon *)0
#define NIL_POINT (struct maillon_point *)0

struct liste {
    struct maillon *tete;
    int nb_elem;
};

struct maillon_point {
    int start, finish;
    struct maillon_point *next;
};

struct result {
    float cost;
    struct maillon_point *tete;
};

extern void init_donnees_tab(struct donnees *, int);
extern void free_donnees_tab(struct donnees);
extern void init_tab_liste_succ(struct liste *, int);
extern void free_tab_liste_succ(struct liste *, int);
extern void init_liste_succ(struct liste *);
extern void ajout_liste_succ(struct liste *, int, int, float);
extern void init_result_tab(struct result *);
extern void ajout_result_tab(struct result *, int, int);
extern void free_result_tab(struct result *);