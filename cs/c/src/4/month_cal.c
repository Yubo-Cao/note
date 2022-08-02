#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

int get_days_since_start(int month);
int parse_int(const char *str);

int main(int argc, char const *argv[])
{
    int month, result, days;
    if (!(argc == 3 && 1 <= (month = parse_int(argv[1])) <= 12 && 1 <= (days = parse_int(argv[2])) <= 31))
    {
        printf("Please input month: ");
        scanf("%d", &month);
        printf("Please input days: ");
        scanf("%d", &days);
    }
    printf("%d\n", get_days_since_start(month - 1) + days);
    return EXIT_SUCCESS;
}

int parse_int(const char *str)
{
    char *pt;

    errno = 0;
    long try = strtol(str, &pt, 10);

    if (errno != 0 || *pt != '\0' || try < INT_MIN || try > INT_MAX)
        printf("Invalid input %ld\n", try);
    else
        return (int)try;
}

int get_days_since_start(int month)
{
    int days = 0;
    switch (month)
    {
    case 12:
        days += 31;
    case 11:
        days += 30;
    case 10:
        days += 31;
    case 9:
        days += 30;
    case 8:
        days += 31;
    case 7:
        days += 31;
    case 6:
        days += 30;
    case 5:
        days += 31;
    case 4:
        days += 30;
    case 3:
        days += 31;
    case 2:
        days += 28;
    case 1:
        days += 31;
    }
    return days;
}