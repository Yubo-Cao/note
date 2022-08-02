#include <stdio.h>
#include <stdbool.h>
#include <string.h>

bool is_palindrom(char const *const str)
{
    char const *p = str;
    char const *q = str + strlen(str) - 1;
    while (p < q)
        if (*p++ != *q--)
            return false;
    return true;
}

int main(int argc, char const *argv[])
{
    char *str = "racecar";
    printf("%s is %s\n", str, is_palindrom(str) ? "a palindrom" : "not a palindrom");
    return 0;
}
