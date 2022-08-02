#include <iostream>

int main()
{
    bool alwaysOnTop = true;
    // Are you still integer?
    std::cout << alwaysOnTop << " " << (alwaysOnTop == 1) << std::endl;
    std::cout << (alwaysOnTop == 1) << " " << (alwaysOnTop == true) << std::endl;
    // 1, I suspect it is char.
    std::cout << sizeof(alwaysOnTop) << std::endl;
    return 0;
}