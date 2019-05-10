/* liste_wstring.c */

#include "liste_wstring.h"

void init_liste_wstring(struct liste_wstring* L) {
    L->tete = (struct maillon_wstring*)0;
    L->nbelem = 0;
}

void ajouter_en_tete_liste_wstring(struct liste_wstring* L, wstring key, wstring data) {
    struct maillon_wstring* nouveau;

    nouveau = (struct maillon_wstring*)malloc (sizeof (struct maillon_wstring));
    assert (nouveau != (struct maillon_wstring*)0);
    
    wcscpy(nouveau->clef, key);
    wcscpy(nouveau->satellite, data);
    nouveau->next = L->tete;
    
    L->tete = nouveau;
    L->nbelem += 1;
}

void clear_liste_wstring(struct liste_wstring* L) {
    struct maillon_wstring* courant;
    struct maillon_wstring* suivant;
    int i;

    courant = L->tete;
    for (i = 0; i < L->nbelem; i++){
        suivant = courant->next;
        free(courant);
        courant = suivant;
    }
}

/*
 * Sous-fonction de set_liste_double.
 * Si L = [m1, m2, ..., mn] avant l'appel alors
 *    L = [mn, ..., m2, m1] après l'appel.
 * Aucune allocation dynamique n'est effectuée. 
 * Seuls les pointeurs sont modifiés
 */

static void retourner_liste_wstring(struct liste_wstring* L)
{   struct maillon_wstring *precedent, *courant, *suivant;
    int i;

    if (L->nbelem >= 2)
    {   courant = L->tete;
        suivant = courant->next;
        courant->next = (struct maillon_wstring*)0;
        for (i = 1; i < L->nbelem; i++)
        {   precedent = courant;
            courant = suivant;
            suivant = suivant->next;
            courant->next = precedent;
        }
        L->tete = courant;
    }
}

void set_liste_wstring(struct liste_wstring* dst, struct liste_wstring* src)
{   struct maillon_wstring* M;
    int i;

    if (dst != src) {
        clear_liste_wstring(dst);
        init_liste_wstring(dst);
        
        M = src->tete;
        for (i = 0; i < src->nbelem; i++) {
            ajouter_en_tete_liste_wstring(dst, M->clef, M->satellite);
            M = M->next;
        }
        
        retourner_liste_wstring(dst);
    }
}

wchar_t* rechercher_liste_wstring(struct liste_wstring* L, wstring key) {
    struct maillon_wstring* M;
    
    M = L->tete;
    for (int i=0; i<L->nbelem; i++) {
        if (wcscmp(M->clef, key))
            return M->satellite;
        
        M = M->next;
    }
    
    return (wchar_t*)0;
}

void imprimer_liste_wstring(struct liste_wstring* L) {
    struct maillon_wstring* M;

    wprintf(L"[");
    
    M = L->tete;
    for (int i=0; i<L->nbelem; i++) {
        if (i == 0)
            wprintf(L"%ls", M->satellite);
        else
            wprintf(L", %ls", M->satellite);
        
        M = M->next;
    }
    
    wprintf(L"]\n");
}
