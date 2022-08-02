#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
    int *pi;
    pi = malloc(25 * sizeof(int));
    if (pi == NULL)
    {
        printf("Out of Memory\n");
        exit(EXIT_FAILURE);
    }

    int *pt;
    for (pt = pi; pt < pi + 25; pt++)
        *pt = pt - pi + 1;

    int i;
    for (i = 0; i < 25; i++)
        printf("%d\n", pi[i]);
    return 0;
}
