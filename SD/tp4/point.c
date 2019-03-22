#include <stdbool.h>
#include "point.h"

void init_point(struct point* p, double x, double y, char id) {
    p->x = x;
    p->y = y;
    p->ident = id;
}

int compare_points(const void* a, const void* b) {
    struct point* pa = (struct point*) a;
    struct point* pb = (struct point*) b;
    int scalaire = pa->x * pb->y - pa->y * pb->x;

    if (scalaire > 0)
        return 1;
    else if (scalaire < 0)
        return -1;
    else
        return 0;
}

bool tourne_a_gauche(struct point* A, struct point* B, struct point* C) {
    int scalaire = (B->x - A->x) * (C->y - A->y) - (B->y - A->y) * (C->x - A->x);

    return scalaire > 0 ? false : true ;
}