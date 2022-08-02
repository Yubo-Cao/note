#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(void)
{
    int ch;
    int lparen_count = 0;
    while ((ch = getchar()) != EOF)
    {
        if (ch == '{')
        {
            lparen_count++;
        }
        else if (ch == '}')
        {
            if (lparen_count <= 0)
            {
                printf("Error: unbalanced braces\n");
                return EXIT_FAILURE;
            }
            lparen_count--;
        }
    }
    if (lparen_count != 0)
    {
        printf("Error: unbalanced braces\n");
        return EXIT_FAILURE;
    }
    return EXIT_SUCCESS;
}