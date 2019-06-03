/* functions.h */
#include "structure.h"

extern int* tour_geant(struct donnees, int);
extern int min_distance(float *, int *, int, int);
extern struct liste *split(int *, struct donnees);
extern void bellman(struct liste *, int, struct result *);