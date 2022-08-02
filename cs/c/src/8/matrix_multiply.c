#include <stdio.h>
#include <stdarg.h>

void matrix_multiply(int *m1, int *m2, int *r, int x, int y, int z)
{
    int *p1, *p2;
    for (int i = 0; i < x; i++)
    {
        int j;
        for (j = 0; j < z; j++)
        {
            p1 = m1 + y * i, p2 = m2 + j;
            int res = 0;
            for (int k = 0; k < y; k++)
            {
                res += *p2 * *p1;
                p2 += z;
                p1++;
            }
            *r++ = res;
        }
    }
}

void print_vec(int *vec, int len)
{
    printf("[");
    for (int i = 0; i < len; i++)
    {
        printf("%d, ", *vec++);
    }
    printf("]");
}

void print_mat(int *mat, int h, int c)
{
    printf("[");
    for (int i = 0; i < h; i++)
    {
        if (i != 0)
            printf(" ");
        print_vec(mat, c);
        mat += c;
        if (i < h - 1)
            printf(",\n");
    }
    printf("]\n");
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
}
