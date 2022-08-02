#include <stdio.h>

void print_hex(int i)
{
    if (i < 0)
    {
        putchar('-');
        i = -i;
        print_hex(i);
    }

    if (i == 0)
        return;
    
    print_hex(i / 16);

    int remainder = i % 16;
    if (remainder < 10)
        putchar(remainder + '0');
    else
        putchar(remainder + 'A' - 10);
}

int main(int argc, char const *argv[])
{
    print_hex(0x12abc);
    putchar('\n');  
    return 0;
}
