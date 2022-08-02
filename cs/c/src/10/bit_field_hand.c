#include <stdio.h>

int cpy_bits(int src, int dst, int offset, int len)
{
    int mask = (~0 << offset) & (~(~0 << (offset + len)));
    // only appropriate position are 1
    int src_mask = src & mask;
    int dst_mask = dst & ~mask;
    return dst_mask | src_mask;
}

int main(int argc, char const *argv[])
{
    int x = 0x123456, aaa, bbb, ccc, ddd;
    cpy_bits(x, aaa, 12, 4);
    cpy_bits(x, bbb, 4, 8);
    cpy_bits(x, ccc, 1, 3);
    cpy_bits(x, ddd, 0, 1);
    return 0;
}
