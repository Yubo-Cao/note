#include <iostream>

int main(int argc, char const *argv[])
{
    int large_num = 50000;
    short small_num = 50000;

    using namespace std;
    cout << "Large number: " << large_num << endl;
    cout << "Small number: " << small_num << endl; // 恭喜，溢出了！

    // short no_overflow { 50000 }; // 编译不过，避免溢出
    // cout << "No overflow: " << no_overflow << endl;

    float some_float{large_num}; // warning, narrowing conversion. 可能溢出
    cout << "Float: " << some_float << endl;

    float better_float{50000}; // 无警告，肯定不溢出
    cout << "Better float: " << better_float << endl;

    // 上述列表初始化语法避免了数据在执行阶段缩窄转化可能导致的 bug. 这种 bug 难以发现。

    return 0;
}
