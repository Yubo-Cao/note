#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char const *argv[])
{
    char *str = "Hello, World!";
    printf("%s\n", str);
    char str2[100];
    strcpy(str2, str);
    printf("%s\n", str2);
    return EXIT_SUCCESS;
}
