#include <stdio.h>

// argv 的为不是一个 NULL 指针
// 可以用来确定实际传递参数的数量。
int main(int argc, char const *argv[])
{
    // 打印所有的参数
    while(*++argv != NULL)
        printf("%s ", *argv);
    printf("\n");
    return 0;
}
