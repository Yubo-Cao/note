#include <stdio.h>
#include <stdlib.h>

void print_star(int n)
{
    if (n < 0 || n > 100)
        return;
    n += 5;
    n /= 10;
    // print number of star according to
    // n. bar charter!
    // far faster.
    printf("%s\n", "**********" + 10 - n);
}

int main(int argc, char const *argv[])
{
    int n;
    if (scanf("%d", &n) != 1)
        return EXIT_FAILURE;
    print_star(n);
    return 0;
}
