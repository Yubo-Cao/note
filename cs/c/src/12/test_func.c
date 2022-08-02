#include <stdio.h>

int f(int a, int b)
{
    return a + b;
}

int main(int argc, char const *argv[])
{
    // pointer to a function that accepts two ints and return an int
    int (*pf)(int, int) = &f;
    printf("%d\n", f(1, 2));
    // compilter always convert function name 
    // to pointer to function. hence, `*` is optional
    printf("%d\n", pf(1, 2));
    printf("%d\n", (*pf)(1, 2));
    return 0;
}
