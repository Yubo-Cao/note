#include <stdio.h>

int my_strlen(char *str, int len)
{
    int l = 0;
    while(*str++ != '\0' && l < len)
        l++;
    return l;
}

int main(int argc, char const *argv[])
{
    /* code */
    return 0;
}
