#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main() {
	int status;

	// lit un nom de fichier au clavier
	char fich[100];
	printf("Entrez un nom de fichier:\n");
	scanf("%s", fich);

	// creation d'un processus enfant
	int type = fork();

	// es-t-on dans le parent ou l'enfant ?
	switch (type) {
		case -1:
			perror("echec fork");
			exit(1);
		case 0:
			if (execlp("wc", "wc", "-l", fich, NULL) == -1) {
				perror("echec execlp");
				exit(1);
			}
			exit(0);
		default:
			if (wait(&status) == -1) {
				perror("echec wait");
				exit(1);
			}
	}

	return 0;
}
