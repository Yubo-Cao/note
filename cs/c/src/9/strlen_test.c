#include <stddef.h>
#include <string.h>

size_t my_len(char const *str)
{
    size_t len = 0;
    while (*str++ != '\0')
        len++;
    return len;
}


int main(int argc, char const *argv[])
{
    char *str = "Hello World";
    printf("%zu\n", my_len(str));
    printf("%zu\n", strlen(str));
    return 0;
}



