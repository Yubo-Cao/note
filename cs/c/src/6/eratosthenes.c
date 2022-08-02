#include <stdio.h>
#include <stdbool.h>
#define MAX_SIZE 1000

/**
 * @brief Array will only represents even numbers. The end of the array should contain
 * a NULL element.
 *
 * @param arr
 */
void eratosthenes(bool *arr)
{
    int idx = 3;
    bool *last = arr + MAX_SIZE;

    while (arr < last)
    {
        if (*arr != false)
        {
            bool *pt;
            for (pt = arr + idx; pt < last; pt += idx)
            {
                *pt = false;
            }
        }
        arr++;
        idx++;
    }
}

void print_array(bool *arr)
{
    bool *last = arr + MAX_SIZE;
    printf("1, ");

    int idx = 1;
    while (arr < last)
    {
        if (*arr++)
            printf("%d, ", idx * 2 + 1);
        idx++;
    }
}

int main(int argc, char const *argv[])
{
    bool arr[MAX_SIZE];
    bool *pt;
    bool *last = arr + MAX_SIZE;

    for (pt = arr; pt < last; pt++)
        *pt = true;

    eratosthenes(arr);
    print_array(arr);
    return 0;
}
