#include <stdio.h>
#include <string.h>

void dollars(char *dst, char const *src)
{
    int len = strlen(src);
    int tmp;
    if (len <= 2)
    {
        strncpy(dst, "$0.00", 5 - len);
        strncpy(dst + 5 - len, src, len);
        return;
    }

    *dst++ = '$';
    len -= 2;
    strncpy(dst, src, (tmp = len % 3));
    dst += tmp, src += tmp;

    for (int i = 0; i < len / 3; i++)
    {
        if (dst[-1] != '$')
            *dst++ = ',';
        strncpy(dst, src, 3);
        dst += 3;
        src += 3;
    }

    *dst++ = '.';
    strncpy(dst, src, 2);

    dst += 2;
    *dst = '\0';
}

int main(int argc, char const *argv[])
{
    char dst[20];
    dollars(dst, "123456789");
    printf("%s\n", dst);
    return 0;
}
