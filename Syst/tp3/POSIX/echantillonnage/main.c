#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
    int src, dest, n=5;

    if (argc != 3)
        printf("Nombre d'arguments incorrects.");
    else if ((src = open(argv[1], O_RDONLY)) == -1)
        printf("Ouverture du fichier source impossible.");
    else if ((dest = open(argv[2], O_WRONLY|O_CREAT)) == -1)
        printf("Creation du fichier destinataire impossible.");
    else {
        //TODO
    }

    close(src);
    close(dest);

    return 0;
}