/* point.h */

#if ! defined (POINT_H)
#define POINT_H 1

#include <stdbool.h>

/*
 * IMPLANTATION
 *
 * Un �l�ment de type struct point repr�sente un point dans le plan.
 * Les champs x et y donnent les coordonn�es cart�siennes.
 * Le champ ident contient une lettre, pour identifier le point.
 */

struct point {
    double x;     /* abscisse */
    double y;     /* ordonn�e */
    char ident;   /* identificateur */
};

/*
 * TYPE ABSTRAIT
 */

/*
 * Constructeur
 */

extern void init_point(struct point*, double, double, char);

/*
 * Fonction de comparaison, pour trier un tableau de points par
 * angle croissant, au moyen de qsort.
 */

extern int compare_points(const void*, const void*);

/*
 * Retourne true si le chemin qui va de A vers C tourne � gauche en B.
 */

extern bool tourne_a_gauche(struct point* A, struct point* B, struct point* C);

#endif
