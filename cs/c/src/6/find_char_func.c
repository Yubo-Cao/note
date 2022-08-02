#include <stdio.h>

char *find_char(char const *source, char const *chars)
{
    char *target = source;
    while (*target != '\0')
    {
        char *cur = chars;
        while (*cur != '\0')
        {
            if (*cur == *target)
                return target;
            cur++;
        }
        target++;
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
