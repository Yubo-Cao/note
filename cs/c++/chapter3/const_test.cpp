#include <iostream>

constexpr double get_pi() { return 3.141592654; }
constexpr double get_2_pi() {return get_pi() * 2; }

/* constexpr 中只能 invoke 其他 constexpr，杜绝了无法编译阶段优化的情况。
constexpr double printer_pi() {
    std::cout << "Enter the value of pi: ";
    return get_pi();
} */

int main(int argc, char const *argv[])
{
    // 字面量，也被称为常量。   "Hello World" 这样的东西就是字面常量。
    const char *literal = "Hello World";
    std::cout << literal << std::endl;
    // 通过使用 0, 0b, 0x 来实现 octal, binary, hexadecimal 字面量。
    int nums[] = {15, 0b1111, 0xf, 017};
    for (int *ptr = nums; ptr < &nums[4]; ++ptr)
        std::cout << *ptr << " ";
    std::cout << std::endl;

    // 定义常量
    const double pi = 3.141592654;
    const auto e = 2.718281828;
    std::cout << "Pi: " << pi << std::endl;
    std::cout << "e: " << e << std::endl;
    // pi = 3.141592654; // error, pi 是 const 常量，不能修改。

    // 定义常量表达式 （他们不是函数，都在编译期间替换了）
    std::cout << "2 * pi: " << get_2_pi() << std::endl;
    // 鉴于常量表达式中可以包含大部分函数的内容，他们 **不一定在编译阶段优化**，如果无法在编译器计算。

    return 0;
}
