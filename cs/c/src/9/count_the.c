#include <stdio.h>
#include <string.h>

#define MAX_SIZE 100

int main(void)
{
    char buf[MAX_SIZE + 1];

    int cp, count = 0;
    char *ptr = buf, *token;
    while ((cp = getchar()) != EOF && count < MAX_SIZE)
        *ptr++ = cp;

    *ptr = '\0';

    for (token = strtok(buf, " "); token != NULL; token = strtok(NULL, " "))
        if (strcmp(token, "the") == 0)
            count++;

    printf("%d\n", count);
}