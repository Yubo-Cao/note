
#include <stdio.h>

/**
 * @brief This function converts an ASCII string to an integer.
 *
 * @param str The string to convert.
 * @return int Converted result.
 */
int ascii_to_integer(char *str)
{
    int result = 0;
    char ch;
    while ((ch = *str++) != '\0')
    {
        if ('0' <= ch && ch <= '9')
            result = result * 10 + (ch - '0');
        else
            return 0;
    }
    return result;
}

int main(int argc, char const *argv[])
{
    char str[] = "12345";
    printf("%d\n", ascii_to_integer(str));
    return 0;
}
