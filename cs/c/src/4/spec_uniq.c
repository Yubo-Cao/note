#include <stdio.h>
#include <string.h>

#define MAX_LEN 128

int main(int argc, char const *argv[])
{
    char line[MAX_LEN];
    char prev_line[MAX_LEN];

    while (fgets(line, MAX_LEN, stdin))
    {
        if (strcmp(line, prev_line) == 0)
        {
            printf("%s", line);
            // Consume anyline hereafter that are same as prev_line
            while (fgets(line, MAX_LEN, stdin) && strcmp(line, prev_line) == 0)
                ;
        }
        strcpy(prev_line, line);
    }

    /* code */
    return 0;
}
