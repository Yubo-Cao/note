#include <iostream>

int main(int argc, char const *argv[])
{
    auto coin_flip = false; // 隐式类型推断， bool
    // 在使用隐式类型推断时，必须初始化变量，否则编译器会报错。
    
    using std::cout;
    using std::endl;
    cout << "Coin flip: " << coin_flip << endl;

    return 0;
}
