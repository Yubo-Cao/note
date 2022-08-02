#include <stdio.h>

int count_one_bits(unsigned val) {
    int ones;
    for (ones = 0; val; val >>= 1) {
        ones += val & 1;
    }
    return ones;
}


int main(int argc, char const *argv[])
{
    printf("%d\n", count_one_bits(8));
    return 0;
}
