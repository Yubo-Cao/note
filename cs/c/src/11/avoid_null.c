#include <stdio.h>

#include "alloc.h"

int main(int argc, char const *argv[])
{
    int *pi;
    pi = alloc(25 * sizeof(int));
    for (int *p = pi; p < pi + 25; p++)
        *p = p - pi + 1;
    for(int i = 0; i < 25; i++)
        printf("%d\n", pi[i]);
    return 0;
}
