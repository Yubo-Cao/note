#include <stdarg.h>

int max_list(int first, ...)
{
    va_list args;
    int max = 0;
    if (first >= 0)
    {
        int this;
        max = first;
        va_start(args, first);
        while ((this = va_arg(args, int)) >= 0)
            if (this > max)
                max = this;
        va_end(args);
    }
    return max;
}
