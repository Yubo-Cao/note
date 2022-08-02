#include <stdio.h>
#include <string.h>
#include <stdbool.h>

char *my_strnchr(char const *str, int ch, int which)
{
    while (true)
    {
        if (*str == '\0')
            return NULL;
        if (*str == ch)
            which--;
        if (which == 0)
            return str;
    }
}
