//abr.h

#include <wchar.h>
#define MAXLEN 80

typedef wchar_t wstring[MAXLEN];

struct couple {
    wstring clef;
    wstring satellite;
};

struct abr {
    struct abr* gauche;
    struct couple* valeur;
    struct abr* droit;
};

#define NIL (struct abr*)0

extern struct abr* ajouter_abr(wstring, wstring, struct abr*);

extern int nombre_noeuds_abr(struct abr*);

extern int hauteur_abr(struct abr*);

extern void afficher_abr(struct abr*);

extern void generer_dot_abr(struct abr*);

extern void clear_abr(struct abr*);

extern wchar_t* rechercher_abr(struct abr*, wstring); 