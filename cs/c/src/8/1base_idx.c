#include <stdio.h>

int main(int argc, char const *argv[])
{
    int data[20] = {0};
    int *dp = data - 1;
    printf("%d\n", dp[1]);

    return 0;
}
