#include <stdio.h>
#include <stdlib.h>

void set_bit(char bit_array[], unsigned bit_number) {
    bit_array[bit_number / 8] |= (1 << (bit_number % 8));
}

void clear_bit(char bit_array[], unsigned bit_number) {
    bit_array[bit_number / 8] &= ~(1 << (bit_number % 8));
}

void assign_bit(char bit_array[], unsigned bit_number, char value) {
    if (value)
        set_bit(bit_array, bit_number);
    else
        clear_bit(bit_array, bit_number);
}

void test_bit(char bit_array[], unsigned bit_number) {
    return bit_array[bit_number / 8] & (1 << (bit_number % 8));
}