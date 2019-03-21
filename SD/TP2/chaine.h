// chaine.h
#include <wchar.h>

/**
 * 1. Déclaration du type chaine
 */

struct chaine
{
    wchar_t* p;
    int i, n;
};

/**
 * 2. Spécifications du type chaine
 * 
 * TODO
 */

/**
 * 3. Prototypages des fonctions 
 */

// Initialise une nouvelle chaine de caractère
extern void init_chaine(struct chaine*);

// Ajoute à la fin de la chaine donnée en paramètre un nouveau caractère
extern void ajout_wchar(struct chaine*, wchar_t);

// Affiche la chaine de caractère donnée en paramètre
extern void imprimer_chaine(struct chaine);

// Libère l'espace mémoire associé à la chaine donnée en paramètre
extern void destruct_chaine(struct chaine);
