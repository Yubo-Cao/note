#include <float.h>
#include <stdio.h>

long double my_sqrt(long double x)
{
    long double result = 1;
    long double last_result;
    do
    {
        last_result = result;
        result = (result + x / result) / 2;
    } while (result != last_result);
    return result;
}

int main(int argc, char const *argv[])
{
    long double x;
    printf("Enter a number: ");
    scanf("%Lf", &x);
    printf("The square root of %Lf is %Lf\n", x, my_sqrt(x));
    return 0;
}
