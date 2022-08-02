#include <stdio.h>

int main(int argc, char const *argv[])
{
    int year;
    printf("Please input a year: ");
    scanf("%d", &year);
    // 被 4 整除且不被 100 整除，或被 400 整除
    if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
        printf("%d is a leap year.\n", year);
    else
        printf("%d is not a leap year.\n", year);
    return 0;
}
