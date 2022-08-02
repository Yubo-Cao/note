#include <stdio.h>

int main(int argc, char const *argv[])
{
    float f;
    f = xyz();
    printf("%f\n", f);
    return 0;
}


float xyz()
{
    return 3.14;
}