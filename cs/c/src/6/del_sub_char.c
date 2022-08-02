#include <stdio.h>
#include <string.h>

int del_substr(char *str, char const *substr)
{
    char *cp, *op;
    for(cp = str; *cp != '\0'; cp++){
    }
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
