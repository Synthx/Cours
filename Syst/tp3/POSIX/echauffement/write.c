#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

int main()
{
    char buf[] = "remi";
    int file;

    file = open("mon_nom.txt", O_WRONLY|O_CREAT);
    write(file, buf, sizeof(buf));
    close(file);

    return 0;
}