#include <stdio.h>

void binary_to_ascii(unsigned val)
{
    unsigned quotient = val / 10;
    if (quotient != 0)
        binary_to_ascii(quotient);
    putchar(val % 10 + '0');
}

int main(int argc, char const *argv[])
{
    unsigned val = 0;
    scanf("%u", &val);
    binary_to_ascii(val);
    return 0;
}


