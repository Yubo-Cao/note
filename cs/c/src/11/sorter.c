#include <stdlib.h>
#include <stdio.h>

int compare_integers(void const *a, void const *b)
{
    register int const *pa = a;
    register int const *pb = b;
    return *pa > *pb ? 1 : *pa < *pb ? -1
                                     : 0;
}

int main()
{
    int *arr;
    int n_values;
    int i;

    printf("Enter the number of values: ");
    if (scanf("%d", &n_values) != 1 || n_values <= 0)
    {
        printf("Invalid number of values\n");
        return EXIT_FAILURE;
    }

    arr = malloc(n_values * sizeof(int));
    if (arr == NULL)
    {
        printf("Out of Memory\n");
        return EXIT_FAILURE;
    }

    printf("Enter the values: ");
    for (i = 0; i < n_values; i++)
    {
        if (scanf("%d", &arr[i]) != 1)
        {
            printf("Invalid value\n");
            return EXIT_FAILURE;
        }
    }

    qsort(arr, n_values, sizeof(int), compare_integers);

    for (i = 0; i < n_values; i++)
        printf("%d\n", arr[i]);
    free(arr);
    return EXIT_SUCCESS;
}
