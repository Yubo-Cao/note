#include <stdio.h>
#include <limits.h>

int main(int argc, char const *argv[])
{
    long var = LONG_MAX;
    short v2 = var; // 溢出，但不报错
    printf("%d", v2);
    return 0;
}
