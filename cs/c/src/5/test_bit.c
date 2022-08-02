#include <stdio.h>

int main(int argc, char const* argv[]) {
    int a = 8;
    printf("%d %d %d\n", a, a << 2, a >> 2);
    a = -8;
    printf("%d %d %d\n", a, a << 2, a >> 2);

    return 0;
}