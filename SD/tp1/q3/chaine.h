// chaine.h
#include "liste_char.h"

/**
 * 1. Déclaration du type chaine
 */

struct chaine
{
    struct liste_char L;
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

// Libère l'espace mémoire associé à la chaine donnée en paramètre
extern void clear_chaine(struct chaine*);

// Ajoute à la fin de la chaine donnée en paramètre un nouveau caractère
extern void ajout_char(struct chaine*, char);

// Affiche la chaine de caractère donnée en paramètre
extern void imprimer_chaine(struct chaine*);
