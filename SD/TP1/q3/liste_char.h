// liste_char.h
/**
 * 1. Déclaration des types maillon et liste_char
 */

struct maillon
{
    char value;
    struct maillon* next;
};

#define NIL (struct maillon*)0

struct liste_char
{
    struct maillon* tete;
    int nbelem;
};

/**
 * 2. Spécifications des types
 * 
 * TODO
 */

/**
 * 3. Prototypages des fonctions
 */

extern void init_liste_char(struct liste_char*);

extern void clear_liste_char(struct liste_char*);

extern void ajouter_fin_liste_char(struct liste_char*, char);

extern void imprimer_liste_char(struct liste_char*);
