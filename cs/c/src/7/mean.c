#include <stdarg.h>
#include <stdio.h>

float average(int N, ...)
{
    va_list var_args;
    int count;
    float sum = 0;

    va_start(var_args, N);
    for (count = 0; count < N; count++)
    {
        sum += va_arg(var_args, int);
    }
    va_end(var_args);
    return sum / N;
}

int main(int argc, char const *argv[])
{
    printf("%f\n", average(3, 1, 2, 3));
    return 0;
}
