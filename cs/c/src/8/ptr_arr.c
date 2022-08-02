#include <stdio.h>

int main(int argc, char const *argv[])
{
    char *kws[5] = {
        "return",
        "if",
        "else",
        "fn",
        "for",
    };

    for (int i = 0; i < 5; ++i)
        printf("%s\n", kws[i]);

    return 0;
}
