#include <float.h>
#include <stdio.h>

int main(int argc, char const *argv[])
{
    double num = DBL_MAX - 1.0;
    float num2 = num;
    printf("%g\n", num2); // inf
    printf("%f\n", num2); // inf
    return 0;
}
