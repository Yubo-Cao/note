#include <stdio.h>

int gcd(int M, int N)
{
    int mod = M % N;
    if (mod == 0)
        return N;
    else if (mod > 0)
        return gcd(N, M % N);
}

int main(int argc, char const *argv[])
{
    printf("%d\n", gcd(12, 18));
    printf("%d\n", gcd(18, 12));
    return 0;
}
