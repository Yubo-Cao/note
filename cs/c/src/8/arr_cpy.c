#include <stdio.h>
#define SIZE 50
int x[SIZE];
int y[SIZE];
int i;
int *p1, *p2;

void arr_cpy_idx()
{
    for (i = 0; i < SIZE; i++)
        x[i] = y[i];
}

int main(int argc, char const *argv[])
{
    /* code */
    return 0;
}
