#include <stdio.h>
#include <stdbool.h>

/*
multiply two matrices of sizes (x * y) and (y * z) (row, col)
store the result into r, which is a matrix of size (x * z)
*/
void matrix_multiply(int *m1, int *m2, int *r, int x, int y, int z)
{
    register int *m1p, *m2p;
    int row, col;

    for (row = 0; row < x; row++)
        for (col = 0; col < z; col++)
        {
            m1p = m1 + row * y;
            m2p = m2 + col;
            *r = 0;
            for (int i = 0; i < y; i++, m1p++, m2p += z)
                *r += *m1p * *m2p;
            r++;
        }
}

void print_mat(int *m, int x, int y)
{
    int *p = m;
    for (int i = 0; i < x; i++)
    {
        for (int j = 0; j < y; j++)
            printf("%d ", *p++);
        printf("\n");
    }
}

int main(int argc, char const *argv[])
{
    int m1[][2] = {
        {2, -6},
        {3, 5},
        {1, -1}};
    print_mat(&m1[0][0], 3, 2);
    int m2[][4] = {
        {4, -2, -4, -5},
        {-7, -3, 6, 7}};
    print_mat(&m2[0][0], 2, 4);
    int r[3][4];
    matrix_multiply(&m1[0][0], &m2[0][0], &r[0][0], 3, 2, 4);
    print_mat(&r[0][0], 3, 4);
    return 0;
}
