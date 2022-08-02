#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

int main(void)
{
    int ch;
    char checksum = -1;
    while ((ch = getchar()) != EOF)
    {
        putchar(ch);
        checksum += ch;
    }
    printf("%d\n", checksum);
    return EXIT_SUCCESS;
}