#include <stdio.h>

unsigned int reverse_bits(unsigned int value) {
    unsigned int result = 0;
    for (int i = 0; i < sizeof(int) * 8; i++) {
        if (value & (1 << i)) result |= (1 << (sizeof(int) * 8 - i - 1));
    }
    return result;
}

unsigned int reverse_bits2(unsigned int value) {
    unsigned int result = 0;
    for (int i = 0; i < sizeof(int) * 8; i++) {
        result <<= 1; // Act like as result is a deque.
        result |= (value & 1); // Add the last bit of value to the front of result.
        value >>= 1; // Remove the last bit of value.
    }
    return result;
}

int main(int argc, char const *argv[]) {
    printf("%u\n", reverse_bits2(25U));
    return 0;
}
