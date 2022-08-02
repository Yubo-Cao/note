#include <stdio.h>

union Fi
{
    float f;
    int i;
};

int main(int argc, char const *argv[])
{
    union Fi fi;
    fi.f = 3.14;

    printf("%d\n", fi.i); // 把 pi 的浮点表示形式转换为整数表示形式
    printf("%f\n", fi.f); // 打印 pi

    return 0;
}
