#include <stdio.h>
#include <stdlib.h>

// 通过逐个字符处理的方式来解决字符串溢出的问题。
int main(void)
{
    int ch;
    int line = 0;
    int at_beginning = 1;

    while ((ch = getchar()) != EOF)
    {
        if (at_beginning)
        {
            at_beginning = 0;
            line++;
            printf("%d ", line);
        }
        putchar(ch);
        if (ch == '\n')
        {
            at_beginning = 1;
        }
    }

    return EXIT_SUCCESS;
}