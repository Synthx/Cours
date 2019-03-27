// main-eofr-abr.c

#include <locale.h>
#include <wctype.h>
#include <assert.h>
#include <stdlib.h>
#include <stdio.h>
#include "abr.h"

int main () {
    struct abr* racine;
    wstring clef, satellite;
    wchar_t* p;
    wint_t c;
    FILE* f;
    int i;

    // Chargement du dictionnaire
    assert(setlocale(LC_ALL, "fr_FR.UTF-8") != NULL);
    f = fopen("Esperanto-Francais-shuffle.utf8", "r");
    assert(f != NULL);

    // Initialisation de l'ABR
    racine = NIL;

    // Lecture du fichier jusqu'à sa fin
    c = fgetwc(f);
    while (c != WEOF) {
        // Récupération de la clef
        i = 0;
        while (c != L':') {
            clef[i] = c;
            i += 1;
            c = fgetwc(f);
        }
        clef[i] = L'\0';

        // Récupération du satellite
        c = fgetwc(f);
        i = 0;
        while (c != L'\n') {
            satellite [i] = c;
            i += 1;
            c = fgetwc(f);
        }
        satellite [i] = L'\0';

        // Ajout dans l'ABR du couple (clef, satellite)
        racine = ajouter_abr(clef, satellite, racine);

        // Lecture du caractère suivant
        c = fgetwc(f);
    }

    // Fermeture du dictionnaire
    fclose(f);

    // Affichage des informations de l'ABR
    wprintf(L"La hauteur de l'ABR est %d\n", hauteur_abr(racine));
    wprintf(L"Le nombre de noeuds de l'ABR est %d\n", nombre_noeuds_abr(racine));

    // // Affichage dans la console de l'ABR
    // afficher_abr(racine);
    // printf("\n");

    // Génération du fichier abr.dot
    generer_dot_abr(racine);

    // Génération du fichier abr.pdf
    wprintf(L"Generation du fichier pdf...\n");
    system("dot -Tpdf abr.dot -Grankdir=LR -o abr.pdf");

    // Recherche d'une clef dans l'ABR
    p = rechercher_abr(racine, L"neebla");
    wprintf(L"Traduction de \"neebla\": %ls\n", p);

    // Vidage de la mémoire
    clear_abr(racine);

    return 0;
}

