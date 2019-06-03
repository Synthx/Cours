// relation.h

#include <stdbool.h>
#include "grandeur.h"

/*
 * Le type struct relation permet de représenter une relation
 * de la forme A * B = C où A, B et C sont les identificateurs
 * de trois grandeurs.
 */

struct relation {
    char A [GDIM];
    char B [GDIM];
    char C [GDIM];
};

extern void init_relation (struct relation*);

extern void clear_relation (struct relation*);

extern bool read_relation (struct relation*);

extern void print_relation (struct relation*);

