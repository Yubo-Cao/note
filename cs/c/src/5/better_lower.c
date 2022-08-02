#include <stdio.h>
#include <ctype.h>

int main(int argc, char const *argv[])
{
    int ch;
    while ((ch = getchar()) != EOF) putchar(tolower(ch));
    return 0;
}
