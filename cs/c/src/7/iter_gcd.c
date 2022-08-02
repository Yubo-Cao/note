#include <stdio.h>

int gcd(int m, int n)
{
    if (m <= 0 || n <= 0)
        return;
    int r;
    do
    {
        r = m % n;
        m = n;
        n = r;
    } while (r > 0);
    return m;
}

