#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
    typedef char *char_pt;
    char_pt str = "Hello, World!";
    printf("%s\n", str);
    return EXIT_SUCCESS;
}
