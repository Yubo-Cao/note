#include <stdio.h>
#include <string.h>

char *my_strcat(char *dst, char *src, int size)
{
    strncat(dst, src, size);
    dst[size - 1] = '\0';
    return dst;
}