// main.c

#include "abr.h"

int main ()
{   struct abr* A;

    A = NIL;
    A = ajout_abr (17, A);
    A = ajout_abr (109, A);
    A = ajout_abr (58, A);
    A = ajout_abr (5, A);
    return 0;
}

