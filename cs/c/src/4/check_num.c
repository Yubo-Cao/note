#include <stdint.h>
#include <stdio.h>

void read_int(char *name, int *result)
{
    printf("Please input '%s': ", name);
    scanf("%d", result);
}

int main(int argc, char const *argv[])
{
    int x, y, a, b;
    read_int("x", &x);
    read_int("y", &y);
    read_int("a", &a);
    read_int("b", &b);
    if (x < y || a >= b)
        printf("WRONG\n");
    else
        printf("RIGHT\n");
    return 0;
}
