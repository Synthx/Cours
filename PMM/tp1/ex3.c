#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
	int cpid_a, cpid_b, i, status, terminated_cpid;
	char *n1, *n2;

	// lecture des entiers
	n1 = argv[1];
	n2 = argv[2];

	// creation du premier processus
	cpid_a = fork();

	switch (cpid_a) {
		case -1:
			perror("echec fork somme");
			exit(1);
		case 0:
			// appel du programme somme
			if (execlp("./somme", "somme", n1, n2, NULL) == -1) {
				perror("echec execlp somme");
				exit(1);
			}
		default:
			// creation du second processus
			cpid_b = fork();
			switch (cpid_b) {
				case -1:
					perror("echec fork produit");
					exit(1);
				case 0:
					// appel du programme produit
					if (execlp("./produit", "produit", n1, n2, NULL) == -1) {
						perror("echec execlp produit");
						exit(1);
					}
			}

	}

	for (i = 0; i <= 1; i++) {
		terminated_cpid = wait(&status);

		if (terminated_cpid == -1) {
			fprintf(stderr, "echec wait pour %d", i);
			exit(1);
		}

		if (terminated_cpid == cpid_a) {
			printf("programme somme terminé\n");
		} else {
			printf("programme produit terminé\n");
		}
	}

	return 0;
}
