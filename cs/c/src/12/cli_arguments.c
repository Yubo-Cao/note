#include <stdio.h>

// argv 是指向字符的指针的指针，也就是所有的参数
// argv 中存储参数的顺序不能依赖，这个顺序取决于编译器的实现
int main(int argc, char const *argv[])
{
    printf("Obtain %d arguments:\n", argc);
    // 程序名本身也会被传入
    for (int i = 0; i < argc; i++)
        printf("%s\n", argv[i]);

    return 0;
}
