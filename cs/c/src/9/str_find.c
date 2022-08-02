#include <stdio.h>
#include <string.h>

char *strrstr(char const *s1, char const *s2)
{
    register char *last;
    register char *cur;

    if (*s2 == '\0')
        return NULL;

    cur = strstr(s1, s2);
    while (cur != NULL)
    {
        last = cur;
        cur = strstr(last + 1, s2);
    }
    return last;
}

int main(int argc, char const *argv[])
{
    char *str = "Hello World llo Yubo";
    printf("%s\n", strrstr(str, "llo"));
    printf("%s\n", strstr(str, "llo"));
    
    return 0;
}
