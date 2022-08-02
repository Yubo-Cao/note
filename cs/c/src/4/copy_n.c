#include <stdio.h>

void copy_n(char dst[], char src[], int n)
{
    int src_len = sizeof(src) / sizeof(src[0]);
    int dst_len = sizeof(dst) / sizeof(dst[0]);

    int i = 0;
    // 如果 src 的长度小于 n, 就尽可能多的复制
    while (i < n && i < src_len && i < dst_len)
    {
        dst[i] = src[i];
        i++;
    }
    // 如果 src < n, 那么就在后面补上 NUL 字符
    while (i < dst_len && i < n)
    {
        dst[i] = '\0';
        i++;
    }
}

int main(int argc, char const *argv[])
{
    char dst[10] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
    char src[5] = {'1', '2', '3', '4', '5'};
    printf("dst: %s\n", dst);
    printf("src: %s\n", src);
    copy_n(dst, src, 5);
    printf("dst: %s\n", dst);
    printf("src: %s\n", src);
    return 0;
}
