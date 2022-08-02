#include <stdio.h>
#include <stdlib.h>

int store_bit_field(int ori, int val, unsigned start, unsigned end) {
    int mask = ~0 << (end) & ~(~0 << start + 1);
    return ori & ~mask | (val << end) & mask;
}


int main(int argc, char const *argv[]) {
    printf("%x\n", store_bit_field(0x0, 0x1, 4U, 4U)); // 10
    printf("%x\n", store_bit_field(0xffff, 0x123, 15U, 4U)); // 123f
    printf("%x\n", store_bit_field(0xffff, 0x123, 13U, 9U)); // c7ff
}
