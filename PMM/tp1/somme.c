#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
	int n1, n2;

	n1 = atoi(argv[1]); /* la fonction atoi convertit une chaîne de caractères en entier */
	n2 = atoi(argv[2]);

	printf("PID : %d, PPID : %d, %d + %d = %d\n", getpid(), getppid(), n1, n2, n1+n2);

	return 0;
}
