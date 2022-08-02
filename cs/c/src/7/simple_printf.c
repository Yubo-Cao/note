#include <stdbool.h>
#include <stdarg.h>
#include <stdio.h>
#define NUL '\0'

void print_integer(int i)
{
    int q = i / 10;
    if (q != 0)
        print_integer(q);
    putchar(i % 10 + '0');
}

void print_double(double f)
{
    int i = (int)f;
    print_integer(i);
    putchar('.');
    f -= i;
    while (f != 0)
    {
        f *= 10;
        putchar('0' + (int)f);
        f -= (int)f;
    }
}

void print_string(char *s)
{
    while (*s != NUL)
        putchar(*s++);
}

void my_printf(const char *format, ...)
{
    char ch;
    enum State
    {
        STATE_PERCENT,
        STATE_NORMAL
    };
    enum State state = STATE_NORMAL;
    va_list args;
    va_start(args, 0);
    int idx = 0;
    while ((ch = *format++) != NUL)
    {
        if (ch == '%')
        {
            state = STATE_PERCENT;
            continue;
        }
        if (state == STATE_PERCENT)
        {
            switch (ch)
            {
            case 'd':
                print_integer(va_arg(args, int));
                break;
            case 'f':
                print_double(va_arg(args, double)); // default argument promotion
                break;
            case 's':
                print_string((char *)va_arg(args, int));
                break;
            case 'c':
                putchar(va_arg(args, int));
                break;
            default:
                printf("\nInvalid format specifier: %%%c\n", ch);
                return;
            }
            state = STATE_NORMAL;
        }
        else
        {
            putchar(ch);
        }
    }
    va_end(args);
}

int main(int argc, char const *argv[])
{
    my_printf("Test%c %d %s %f\n", 'c', 1, "World", 1.02);
    return 0;
}
