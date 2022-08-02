#include <stdio.h>

void reverse_string(char *str)
{
    int len = 0;
    char *cp;
    for (cp = str; *cp != '\0'; cp++)
        len++;

    for (int i = 0; i < len / 2; i++)
    {
        char tmp = *(str + (len - 1 - i));
        *(str + (len - 1 - i)) = *(str + i);
        *(str + i) = tmp;
    }
}

int main(int argc, char const *argv[])
{
    // It must be stored in a char array, String can not be modified
    char str[] = "Hello World";
    printf("%s\n", str);
    reverse_string(str);
    printf("%s\n", str);
    return 0;
}
