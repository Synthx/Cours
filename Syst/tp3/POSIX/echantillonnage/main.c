#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
    int src, dest, n;
    char buf[10];

    if (argc != 3)
        printf("Nombre d'arguments incorrects.\n");
    else if ((src = open(argv[1], O_RDONLY)) == -1)
        printf("Ouverture du fichier source impossible.\n");
    else if ((dest = open(argv[2], O_WRONLY|O_CREAT, S_IRWXU)) == -1)
        printf("Creation du fichier destinataire impossible.\n");
    else {
        while ((n = read(src, buf, 10)) > 0) {
            if (n < 5)
                write(dest, buf, n);
            else
                write(dest, buf, 5);
        }
    }

    close(src);
    close(dest);

    return 0;
}