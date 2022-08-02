#include <stdio.h>

int main(int argc, char const *argv[])
{
    int array[10];
    for (int i = 0; i < 10; i++)
        array[i] = 0;
    printf("%d\n", array[5]);
    return 0;
}
