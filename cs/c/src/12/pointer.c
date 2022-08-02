#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
    int i = 5;
    int *pi = &i;
    int **ppi = &pi;
    
    printf("%d\n", **ppi);
    printf("%d\n", *pi);
    printf("%d\n", i);
    return 0;
}
