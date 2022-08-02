#include <stdio.h>
#include <string.h>

int main(int argc, char const *argv[])
{
    char buf[100];
    printf("%zu\n", sizeof(buf) / sizeof(char));
    strcat(buf, "Hello World");
    printf("%s\n", buf);
    strcat(buf, " Hello C");
    printf("%s\n", buf);
    char dst[100];
    strcpy(dst, buf);
    printf("%s\n", dst);
    return 0;
}
