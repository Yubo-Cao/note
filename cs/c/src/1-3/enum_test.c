#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
    enum Jar_Type
    {
        CUP,
        PINT,
        QUART,
        HALF_GALLON,
        GALLON
    };
    enum Jar_Type jar_type = CUP;
    printf("%d\n", jar_type); // 0
    enum
    {
        CUP_SIZE = 8,
        PINT_SIZE = 16,
        QUART_SIZE = 32,
        HALF_GALLON_SIZE = 64,
        GALLON_SIZE = 128
    } gallon_sz = GALLON_SIZE;
    printf("%d\n", gallon_sz); // 128
    printf("%d\n", CUP_SIZE + 1); // 9
    return EXIT_SUCCESS;
}
