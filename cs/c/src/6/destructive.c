#include <assert.h>
#include <stdbool.h>
#include <stdio.h>

bool find_char(char **arr, int val) {
    assert(arr != NULL);

    /*
    对于每个字符串
    */
    while (*arr != NULL) {
        /*
        观察字符串中每个字符
        */
        while (**arr != '\0') {
            if (*(*arr)++ == val) return true;
        }
        arr++;
    }
    return false;
}

int main(int argc, char const *argv[])
{
    char *arr[2] = {"No Hello", NULL};
    printf("%d\n", find_char(arr, 'x'));
    return 0;
}
