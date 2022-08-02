#include <stdio.h>
#include <stdbool.h>

#define SIZE 8

int count;

int min(int a, int b)
{
    return (a < b) ? a : b;
}

bool is_valid(bool chess[][SIZE], int row, int col)
{
    int i, rd = SIZE - row - 1, cd = SIZE - col -1;

    for (i = 0; i < SIZE; i++)
        if (chess[i][col])
            return false;

    for (i = 1; i <= min(row, col); i++)
        if (chess[row - i][col - i])
            return false;

    for (i = 1; i <= min(rd, cd); i++)
        if (chess[row + i][col + i])
            return false;

    for (i = 1; i <= min(cd, row); i++)
        if (chess[row - i][col + i])
            return false;

    for (i = 1; i <= min(rd, col); i++)
        if (chess[row + i][col - i])
            return false;

    return true;
}

void queen(int row)
{
    static bool chess[SIZE][SIZE] = {false};

    if (row >= SIZE)
    {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (chess[i][j])
                    if (i < SIZE - 1)
                        printf("(%d, %d), ", i, j);
                    else
                        printf("(%d, %d)", i, j);
        printf("\n");
        count++;
        return;
    }

    for (int col = 0; col < SIZE; col++)
        if (is_valid(chess, row, col))
        {
            chess[row][col] = true;
            queen(row + 1);
            chess[row][col] = false;
        }
}

int main(int argc, char const *argv[])
{
    queen(0);
    printf("%d\n", count);
    return 0;
}
