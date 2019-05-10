/* liste_wstring.h */

#include <assert.h>
#include <stdlib.h>
#include <wchar.h>
#define MAXLEN 80

typedef wchar_t wstring[MAXLEN];

struct maillon_wstring {
    wstring clef;
    wstring satellite;
    struct maillon_wstring* next;
};

struct liste_wstring {
    struct maillon_wstring* tete;
    int nbelem;
};

extern void init_liste_wstring(struct liste_wstring*);
extern void clear_liste_wstring(struct liste_wstring*);
extern void ajouter_en_tete_liste_wstring(struct liste_wstring*, wstring, wstring);
extern wchar_t* rechercher_liste_wstring(struct liste_wstring*, wstring);
extern void imprimer_liste_wstring(struct liste_wstring*);
