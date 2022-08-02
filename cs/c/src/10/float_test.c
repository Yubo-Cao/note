#include <stdio.h>

struct Float
{
    char sign : 1;
    char exp : 7;
    int mantissa : 24;
};

int main(int argc, char const *argv[])
{
    float f = 3.14;
    struct Float *fi = (struct Float *)&f;

    printf("%d\n", fi->mantissa);
    printf("%d\n", fi->sign);
    printf("%d\n", fi->exp);

    printf("%f\n", f);
    return 0;
}
