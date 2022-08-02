#include <iostream>
#include <cmath>

enum RainbowColor
{
    Red,
    Orange,
    Yellow,
    Green,
    Blue,
    Indigo,
    Violet
};

int main(int argc, char const *argv[])
{
    enum RainbowColor col = Red;
    std::cout << "col: " << col << std::endl;
    // C++ 奇奇怪怪的 enum 语法
    RainbowColor col2 = Violet;
    std::cout << "col2: " << col2 << std::endl;
    auto col3 = Orange;
    std::cout << "col3: " << col3 << std::endl;
    return 0;
}
