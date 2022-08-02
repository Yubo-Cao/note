#include <limits.h>
#include <stdio.h>
#include <stdlib.h>

int main(void)
{
    printf("int max: %d int min: %d\n", INT_MAX, INT_MIN);
    printf("uint max: %u\n", UINT_MAX);

    printf("long max: %ld long min: %ld\n", LONG_MAX, LONG_MIN);
    printf("ulong max: %lu\n", ULONG_MAX);

    printf("long long max: %lld long long min: %lld\n", LLONG_MAX, LLONG_MIN);
    printf("ulong long max: %llu\n", ULLONG_MAX);

    printf("char max: %d char min: %d\n", CHAR_MAX, CHAR_MIN);
    printf("signed char max: %d signed char min: %d\n", SCHAR_MAX, SCHAR_MIN);
    printf("uchar max: %u\n", UCHAR_MAX);
    return EXIT_SUCCESS;
}
