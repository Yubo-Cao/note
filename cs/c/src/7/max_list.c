#include <stdarg.h>
#include <stdio.h>

/**
 * @brief Find the max element in an arbitrary list. Require at least one
 * item in the list.
 * 
 * @param first first item in the list.
 * @param ... subsquent items in the list. The last item must be negative.
 * @return int max item.
 */
int max_list(int first, ...)
{
    va_list args;
    va_start(args, 0);
    int idx = 0, max = first;
    while (1)
    {
        int tmp = va_arg(args, int);
        if (tmp <= 0)
            break;
        if (tmp > max)
            max = tmp;
        idx++;
    }
    va_end(args);
    return max;
}

int main(int argc, char const *argv[])
{
    printf("%d\n", max_list(1, 2, 3, 4, 10, -1));
    return 0;
}
