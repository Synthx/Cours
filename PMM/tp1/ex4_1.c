#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main() {
	char *commands[] = {"who", "ps", "ls"};
	int i;

	for (i=0; i <=2; i++) {
		switch (fork()) {
			case -1:
				fprintf(stderr, "echec fork %s\n", commands[i]);
				exit(1);
			case 0:
				if (execlp(commands[i], commands[i], NULL) == -1) {
					fprintf(stderr, "echec execlp %s\n", commands[i]);
					exit(1);
				}
				exit(0);
			default:
				if (wait(NULL) == -1) {
					fprintf(stderr, "echec wait pour %s", commands[i]);
					exit(1);
				}
		}
	}

	return 0;
}
