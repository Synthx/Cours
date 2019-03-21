#include <stdio.h>
#include <unistd.h>

int main() {	
	int forkid = fork();

	printf("pid: %d, ppid: %d\n", getpid(), getppid());

	return 0;
}
