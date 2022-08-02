#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
    printf("Please input a number: ");
    int n;
    scanf("%d", &n);
    while (n != 0)
    {
        printf("\n");
        n -= 1;
    }
    return 0;
}
