#include <stdio.h>

void binary_to_ascii(unsigned int val)
{
    unsigned int quotient = val / 10;
    if (quotient != 0)
        binary_to_ascii(quotient);
    printf("%d", val % 10);
}

int main(int argc, char const *argv[])
{
    binary_to_ascii(0x12abc);
    return 0;
}

// C 使用推论的方式声明类型
// int *p(int, int)
// 上面的操作就是——p 是某物，被call (int, int) 之后的结果解引用
// 得到 int. 那么，p 为返回 int 指针的函数