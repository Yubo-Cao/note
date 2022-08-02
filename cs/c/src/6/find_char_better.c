#include <stdio.h>

char *
find_char(char const *str, char const *chars)
{
    char *cp;

    if (str == NULL || chars == NULL)
        return NULL;

    for (; *str != '\0'; str++)
    {
        for (cp = chars; *cp != '\0'; cp++)
            if (*cp == *str)
                return str;
    }
    return NULL;
}

int main(int argc, char const *argv[])
{
    printf("%s\n", find_char("Hello World", "H"));
    printf("%s\n", find_char("ABCDEF", "XYZ"));
    printf("%s\n", find_char("ABCDEF", "JURY"));
    printf("%s\n", find_char("ABCDEF", "QQQ"));
    printf("%s\n", find_char("ABCDEF", "XRCQEF"));
    return 0;
}
