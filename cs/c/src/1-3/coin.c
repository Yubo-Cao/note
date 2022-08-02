#include <stdio.h>

int main(int argc, char const *argv[])
{
    enum Coin
    {
        PENNY = 25,
        NICKEL = 1
    };

    enum Coin price = PENNY;
    printf("%d", price);
    return 0;
}
