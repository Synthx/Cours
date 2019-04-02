#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#define CONST 10

/**
 * Seul globVar, malloc et main apparaît lors de l'appel à rtaniel>$ readelf -s ./a.out
 * 
 */

int globVar = 10;

int main() {
    int locVar = 20;
    int* myTab = malloc(10 * sizeof(int));

    printf("PID : %d\n\n", getpid());
    //printf("Adresse CONST : %p\n", CONST);
    printf("Adresse globVar : %p\n", &globVar);
    printf("Adresse locVar : %p\n", &locVar);
    printf("Adresse myTab : %p\n\n", myTab);
    printf("Adresse de la fonction main : %p\n", &main);
    printf("Adresse de la fonction malloc : %p\n", &malloc);

    scanf("%d", &locVar);

    return 0;
}