#include <stdbool.h>
#include <stdio.h>

bool identity_matrix(int matrix[][10])
{
    int i;
    for (i = 0; i < 10; ++i)
        if (matrix[i][i] != 1)
            return false;
    return true;
}

bool dyn_identity_matrix(int *matrix, int sz)
{
    for (int i = 0; i < sz; i++)
    {
        if (*matrix != 1)
            return false;
        matrix += (sz + 1);
    }
    return true;
}

int main(int argc, char const *argv[])
{
    int matrix[10][10];
    for (int i = 0; i < 10; i++)
    {
        matrix[i][i] = 1;
    }
    printf("%d\n", identity_matrix(matrix));
    printf("%d\n", dyn_identity_matrix(&matrix[0][0], 10));

    return 0;
}
