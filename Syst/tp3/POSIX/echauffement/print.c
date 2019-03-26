#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#define MAX 100

int main() {
    char buf[MAX];
    int file;

    file = open("mon_nom.txt", O_RDONLY);
    read(file, buf, MAX);
    close(file);

    printf("%s\n", buf);

    return 0;
}