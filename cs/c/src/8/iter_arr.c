#include <stdio.h>

int main(int argc, char const *argv[])
{
    int matrix[3][4] = {
        {1, 2, 3, 4},
        {5, 6, 7, 8},
        {9, 10, 11, 12}};

    int *mp;
    mp = *matrix;

    printf("First value is %d\n", *mp);
    printf("Second value is %d\n", *++mp);
    printf("Third value is %d\n", *++mp);
    printf("Fourth value is %d\n", *++mp);
    printf("Welcome to next line, the value is %d\n", *++mp); // 5
    printf("Back to previous line, the value is %d\n", *--mp); // 4

    printf("%d\n", matrix[0][0]);
    printf("%d\n", matrix[2][3]); // 12
    //But matrix[3][2] won't work.

    return 0;
}
