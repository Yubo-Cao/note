#include <stdio.h>

int main(int argc, char const *argv[])
{
    int matrix[2][3] = {1, 2, 3, 4, 5, 6};
    int *mp = *matrix;
    for (; mp < matrix[1] + 3; ++mp)
        printf("%d\n", *mp); // 1 2 3 4 5 6
    int mat2[3][2] = {{1, 2}, {3, 4}, {5, 6}};
    
    return 0;
}
