#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[]) {
    int i = 10;
    i = i-- - --i * (i = -3) * i++ + ++i;
    printf("%d\n", i);
    return 0;
}
