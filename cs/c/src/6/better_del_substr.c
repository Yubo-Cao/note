#include <stdio.h>
#include <stdbool.h>
#include <string.h>

char *match(char *str, char *want)
{
    while (*want != '\0')
        if (*str++ != *want++)
            return NULL;
    return str;
}

int del_substr(char *str, char const *substr)
{
    char *next;
    while (*str != '\0')
    {
        next = match(str, substr);
        if (next != NULL)
            break;
        str++;
    }
    if (*str == '\0')
        return false;
    while (*str++ = *next++)
        ;
    return true;
}

void test(char *str, char *substr)
{
    char cp_str[129];
    strcpy(cp_str, str);
    char *cp_substr[129];
    strcpy(cp_substr, substr);

    printf("%d\n", del_substr(cp_str, cp_substr));
    printf("%s\n", cp_str);
}

int main(int argc, char const *argv[])
{
    test("Hello World", "H");
    test("ABCDEF", "FGH");
    test("ABCDEF", "ABC");
    test("ABCDEF", "XABC");
    test("ABCDEF", "");
    return 0;
}