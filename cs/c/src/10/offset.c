#include <stdio.h>
#include <stddef.h>

struct Align
{
    char a;
    int b;
    char c;
};

struct ImprovedAlign
{
    int b;
    char a;
    char c;
};

int main(int argc, char const *argv[])
{
    printf("%lu\n", offsetof(struct Align, a)); // 0
    printf("%lu\n", offsetof(struct Align, b)); // 4
    printf("%lu\n", offsetof(struct Align, c)); // 8
    // 这个结构体使用了 12 个字节，实际只利用了 6 个字节。

    printf("%lu\n", offsetof(struct ImprovedAlign, a)); // 0
    printf("%lu\n", offsetof(struct ImprovedAlign, b)); // 4
    printf("%lu\n", offsetof(struct ImprovedAlign, c)); // 8
    // 这个结构体使用了 8 个字节，利用了 6 个字节，比原来的结构体更紧凑。
    return 0;
}
