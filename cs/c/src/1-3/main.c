#include <stdio.h>
#include <stdlib.h>

void test_func(int);
int increment(int);
int negate(int);

int main(void)
{
    test_func(10);
    test_func(0);
    test_func(-10);
    return EXIT_SUCCESS;
}

void test_func(int i){
    printf("%d\n", increment(i));
    printf("%d\n", negate(i));
}