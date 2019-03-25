// abr.c

#include <stdlib.h>
#include "abr.h"

// Permuter les deux lignes ci-dessous pour choisir la version
// récursive ou la version itérative.

#define VERSION_RECURSIVE 
#undef VERSION_RECURSIVE

#if defined (VERSION_RECURSIVE)
bool recherche_abr (double cle, struct abr* A0)
{
    if (A0 == NIL)
        return false;
    else if (A0->valeur == cle)
        return true;
    else if (A0->valeur > cle)
        return recherche_abr (cle, A0->gauche);
    else
        return recherche_abr (cle, A0->droit);
}
#else
bool recherche_abr (double cle, struct abr* A0)
{
    bool found;
    struct abr* A;

    found = false;
    A = A0;
    while (A != NIL && !found)
    {   if (A->valeur == cle)
            found = true;
        else if (A->valeur > cle)
            A = A->gauche;
        else
            A = A->droit;
    }
    return found;
}
#endif

static struct abr* new_feuille (double cle)
{   struct abr* F;

    F = malloc (sizeof (struct abr));
    F->valeur = cle;
    F->gauche = F->droit = NIL;
    return F;
}

#if defined (VERSION_RECURSIVE)
struct abr* ajout_abr (double cle, struct abr* A0)
{
    if (A0 == NIL)
        return new_feuille (cle);
    else 
    {   if (A0->valeur > cle)
            A0->gauche = ajout_abr (cle, A0->gauche);
        else
            A0->droit = ajout_abr (cle, A0->droit);
        return A0;
    }
}
#else
struct abr* ajout_abr (double cle, struct abr* A0)
{
    if (A0 == NIL)
        return new_feuille (cle);
    else
    {   struct abr* prec = NIL;
        struct abr* cour = A0;
        while (cour != NIL)
        {   prec = cour;
            if (cour->valeur > cle)
                cour = cour->gauche;
            else
                cour = cour->droit;
        }
        if (prec->valeur > cle)
            prec->gauche = new_feuille (cle);
        else
            prec->droit = new_feuille (cle);
        return A0;
    }
}
#endif
