#include <stdio.h>
#include <stdlib.h>
#include <float.h>

int main(int argc, char const *argv[])
{
    printf("float max: %f\n", FLT_MAX); // 3.40282347e+38
    printf("float min: %f\n", FLT_MIN); // 0
    printf("double max: %lf\n", DBL_MAX); // 1.7976931348623157e+308
    printf("double min: %lf\n", DBL_MIN); // 0
    printf("long double max: %Lf\n", LDBL_MAX); // 1.18973149535723176502e+4932L
    printf("long double min: %Lf\n", LDBL_MIN); // 0
    return EXIT_SUCCESS;
}
