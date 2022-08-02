#include <stdio.h>

int count = 0;

int fib(int n)
{
    count++;
    if (n <= 2)
        return 1;
    return fib(n - 1) + fib(n - 2);
}

int main(int argc, char const *argv[])
{
    for (int i = 0; i < 50; i++)
    {
        int result = fib(i);
        printf("Calculate %d:%d use %d calculations.\n", i, result, count);
        count = 0;
    }
    return 0;
}
