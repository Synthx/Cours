#include <stdio.h>
#include <string.h>
#include "relation.h"

/*
 * Constructeur
 */

void init_relation (struct relation* R)
{
}

/*
 * Destructeur
 */

void clear_relation (struct relation* R)
{
}

/*
 * Lit une relation A * B = C sur l'entrée standard et l'affecte à R
 */

bool read_relation (struct relation* R)
{   char buffer [GDIM];

    scanf ("%s", buffer);
    if (strcmp (buffer, "quit") == 0)
        return false;
    strcpy (R->A, buffer);
    scanf ("%s %s %s %s", buffer, R->B, buffer, R->C);
    return true;
}

void print_relation (struct relation* R)
{
    printf ("%s * %s = %s\n", R->A, R->B, R->C);
}
