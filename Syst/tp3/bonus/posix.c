#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#define MAX 1024

int main(int argc, char *argv[]) {
    int dest, n;
    char buf[MAX];

    if (argc != 2)
        printf("Nombre d'arguments incorrects.\n");
    else if ((dest = open(argv[1], O_WRONLY|O_CREAT, S_IRWXU)) == -1)
        printf("Creation du fichier destinataire impossible.\n");
    else {
        // Lecture sur l'entrée standard
        while ((n = read(0, buf, MAX)) > 0) {
            // Ecriture dans le fichier de sortie
            write(1, buf, n);
            // Ecriture dans la sortie standard
            write(dest, buf, n);
        }
    }

    close(dest);

    return 0;
}