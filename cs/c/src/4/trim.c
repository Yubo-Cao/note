#include <stdio.h>
#include <string.h>

void trim(char str[])
{
    // i 用来过滤 str 中的空格，j 指向 trim 后字符串的位置
    int i = 0, j = 0;
    while (str[i] == ' ')
    {
        i++;
    }
    int len = sizeof(str) / sizeof(str[0]);
    while (str[j] != '\0')
    {
        // remove continued spaces
        while (str[i] == ' ' && (str[i + 1] == ' ' || str[i + 1] == '\0'))
        {
            i++;
        }
        str[j] = str[i];
        i++;
        j++;
    }
    str[j] = '\0';
}

int main(int argc, char const *argv[])
{
    char str[] = "   trim     trim  ";
    trim(str);
    printf("%s\n", str);
    return 0;
}
