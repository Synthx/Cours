// liste_dimension.h

#if ! defined (DIMENSION_H)
#define DIMENSION_H 

#define NDIM 32

/*
 * Le type struct dimension permet de représenter une dimension élémentaire. 
 * Le champ ident contient l'identificateur de la dimension
 * Le champ exposant est positif ou négatif mais non nul.
 * Exemple: heure^2 est codé par { ident = "heure", exposant = 2 }
 */

struct dimension {
    char ident [NDIM];
    int exposant;
};

struct maillon_dimension {
    struct dimension valeur;
    struct maillon_dimension* suivant;
};

#define NIL_DIM (struct maillon_dimension*)0

/*
 * Le type struct liste_dimension permet de représenter n'importe 
 * quelle dimension. La dimension est définie comme le produit des
 * valeurs des maillons.
 *
 * Exemple: megawatt/heure est codé par [ {"megawatt", 1}, {"heure", -1} ]
 *                               ou par [ {"heure", -1}, {"megawatt", 1} ]
 */

struct liste_dimension {
    struct maillon_dimension* tete;
    struct maillon_dimension* queue;
    int nbelem;
};

extern void init_liste_dimension (struct liste_dimension*);

extern void clear_liste_dimension (struct liste_dimension*);

extern void set_liste_dimension 
    (struct liste_dimension* dst, struct liste_dimension* src);

extern void set_liste_dimension_produit 
    (struct liste_dimension* dst,
     struct liste_dimension*, struct liste_dimension*);

extern void read_liste_dimension (struct liste_dimension*);

extern void print_liste_dimension (struct liste_dimension*);

#endif
