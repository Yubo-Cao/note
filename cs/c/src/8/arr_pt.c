#include <stdio.h>

int main(int argc, char const *argv[])
{
    int vector[10], *vp = vector;
    int matrix[3][4], (*mp)[4] = matrix;

    for(; mp < matrix + 3; ++mp)
        for(int *p = *mp; p < *mp + 4; ++p)
            *p = vp++ - vector,
            printf("%d\n", *p);
}
