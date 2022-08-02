#include <stdio.h>

int main(int argc, char const *argv[])
{
    printf("Follow ANSI C %d\n", __STDC__);
    printf("Compile Date is %s\n", __DATE__);
    printf("Compile time now is %s\n", __TIME__);
    printf("This is defined at line %d\n", __LINE__);
    printf("This file is %s\n", __FILE__);
    return 0;
}
