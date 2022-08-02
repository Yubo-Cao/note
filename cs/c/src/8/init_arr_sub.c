#include <stdio.h>

int main(int argc, char const *argv[])
{
    int arr[10];
    for (int *ap = arr; ap < arr + 10; ap++)
        *ap = 0;
        
    printf("%d\n", arr[5]); 
    return 0;
}
