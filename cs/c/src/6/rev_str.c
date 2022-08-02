#include <stdio.h>

/**
 * @brief Better and cleaner implementation of reverse string
 * using pointers. This prevent us from using `len`
 * 
 * @param str 
 */
void reverse_string(char *str)
{
    char *last_char;
    for (last_char = str; *last_char != '\0'; last_char++)
        ;
    last_char--;

    while (str < last_char)
    {
        char tmp = *str;
        *str++ = *last_char;
        *last_char-- = tmp;
    }
}

