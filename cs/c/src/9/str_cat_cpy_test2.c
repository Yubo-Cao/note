#include <stdio.h>
#include <string.h>

int main(int argc, char const *argv[])
{
    char buf[100];
    printf("%s\n", strcat(buf, "Hello World"));
    printf("%s\n", buf);
    printf("%s\n", strcat(buf, "Hello C"));
    printf("%s\n", buf);
    return 0;
}
