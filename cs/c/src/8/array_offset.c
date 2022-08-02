#include <stdio.h>
#include <stdarg.h>

int array_offset(int *arrayinfo, ...)
{
    int dim = *arrayinfo;
    if (dim < 1 || dim > 10)
    {
        printf("Error! Invalid dimension %d\n", dim);
        return -1;
    }
    va_list vargs;
    va_start(vargs, arrayinfo);
    int args[dim];
    int lo, hi;
    for (int i = 0; i < dim; i++)
    {
        int arg = va_arg(vargs, int);
        lo = arrayinfo[i * 2 + 1], hi = arrayinfo[i * 2 + 2];
        if (arg < lo || arg > hi)
        {
            printf("Error! Index %d for dimension %d is invalid.\n", arg, i);
            return -1;
        }
        args[i] = arg;
    }
    va_end(vargs);

    int offset = 0;
    for (int i = 0; i < dim; i++)
    {
        lo = arrayinfo[i * 2 + 1], hi = arrayinfo[i * 2 + 2];
        if (i > 0)
            offset *= (hi - lo + 1);
        offset += (args[i] - lo);
    }
    return offset;
}

int main(int argc, char const *argv[])
{
    int arrayinfo[] = {3, 4, 6, 1, 5, -3, 3};
    printf("%d\n", array_offset(arrayinfo, 4, 1, -3));
    printf("%d\n", array_offset(arrayinfo, 4, 1, -2));
    printf("%d\n", array_offset(arrayinfo, 4, 1, 3));
    printf("%d\n", array_offset(arrayinfo, 4, 2, -3));
    printf("%d\n", array_offset(arrayinfo, 5, 1, -3));
    printf("%d\n", array_offset(arrayinfo, 6, 3, 1));
    return 0;
}
