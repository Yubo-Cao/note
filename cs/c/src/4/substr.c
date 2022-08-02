#include <stdio.h>
#include <string.h>
#define MIN(X, Y) (((X) < (Y)) ? (X) : (Y))

int substring(char dst[], char src[], int start, int len)
{
    int i;
    int dst_len = strlen(dst), src_len = strlen(src);
    if (start < 0 || start >= src_len || len < 0)
        return 0;
    // 具体复制多少，看len是否超出src的长度
    len = MIN(src_len - start, len);
    for (int i = 0; i < len; i++)
    {
        dst[i] = src[start + i];
    }
    return len;
}