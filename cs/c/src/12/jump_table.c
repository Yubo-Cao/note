#include <stdio.h>
#include <stdlib.h>

typedef double OpType;

OpType calc_add(OpType a, OpType b)
{
    return a + b;
}

OpType calc_sub(OpType a, OpType b)
{
    return a - b;
}

OpType calc_mul(OpType a, OpType b)
{
    return a * b;
}

OpType calc_div(OpType a, OpType b)
{
    return a / b;
}

OpType (*JUMP_TABLE[])(OpType, OpType) = {
    calc_mul, // 0
    calc_add, // 1
    NULL,
    calc_sub, // 3
    NULL,
    calc_div, // 5
};

OpType calc(OpType a, OpType b, char op)
{
    // 进行边界检查是必要的。
    // 对于数组越界的情况，如果系统没有检查出错误，那么一些随即
    // 指令将被执行；特别的，错误报告的地址将是错误的，极大的增加了调试难度
    // 如果程序还没有失败，那么就基本完蛋了——随即运行一些函数，随即运行一些指令，并且破坏了线索。如果
    // 换一转移表有问题，应当在函数调用1的前后打印。
    // 人们往往不会怀疑这里！
    if (op < '*' || op > '/')
        return 0;
    if (JUMP_TABLE[op] == NULL)
        return 0;

    return JUMP_TABLE[op - '*'](a, b);
}

int main(int argc, char const *argv[])
{
    OpType a, b, res;
    char op;
    // read double, char, double
    scanf("%lf %c %lf", &a, &op, &b);
    printf(" = %lf\n", calc(a, b, op));
    return 0;
}
