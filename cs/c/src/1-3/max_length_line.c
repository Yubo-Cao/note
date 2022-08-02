#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
// 定义输入行的最大长度。
#define MAXSIZE 1000

int main(void)
{
    char str[MAXSIZE];
    char max[MAXSIZE];
    int max_len = 0;
    int len = 0;
    // 我们并不会存储所有输入行，因为我们只关心最大的长度。
    while (fgets(str, MAXSIZE, stdin) != NULL)
    {
        if ((len = strlen(str)) > max_len)
        {
            strcpy(max, str);
            max_len = len;
        }
    }

    printf("%s\n", max);
    return EXIT_SUCCESS;
}