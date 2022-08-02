#include <stdio.h>
#include <stdbool.h>

bool identitiy_matrix(int *mat, int sz)
{
    int row, col;
    for (row = 0; row < sz; row++)
        for (col = 0; col < sz; col++)
            if (*mat++ != (row == col))
                return false;
    return true;
}