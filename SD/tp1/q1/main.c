#include<stdio.h>
#include<ctype.h>
#include<stdlib.h>

int main()
{
    char* p;
    /**
     * i: indice du caractère dans p
     * n: nombre de caractère alloué à p (de 4 caractère à la fois)
     */
    int c, i, n;
    
    // Initialisation de la chaine p
    n = 4;
    p = malloc(n * sizeof(char));
    i = 0;
    c = getchar();
    
    while(!isspace(c)) {
        // Si i dépasse 4 alors on réalloue la zone mémoire de p
        if (i == n) {
            n += 4;
            p = realloc(p, n * sizeof(char));
        }
        // AJout du caractère à la fin de p
        p[i] = c;
        // Incrémentation de l'indice
        i++;
        // Passage au caractère suivant
        c = getchar();
    }
    
    // Ajout du caractère de fin de chaine à p
    p[i] = '\0';
    // Affichage de la chaine de caractère p
    printf("%s\n", p);
    // Libération de la mémoire alloué à p
    free(p);
    
    return 0;
}
