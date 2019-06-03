// main.c

#include "relation.h"

int main ()
{   struct grandeur G;
    struct relation R;

    init_grandeur (&G);
    while (read_grandeur (&G))
        print_grandeur (&G);
    clear_grandeur (&G);

    init_relation (&R);
    while (read_relation (&R))
        print_relation (&R);
    clear_relation (&R);

    return 0;
}
