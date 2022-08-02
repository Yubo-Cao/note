#include <stdio.h>
#include <stdlib.h>

#define TRUE 1
#define FALSE 0

int find_char(char **arr, char val) {
    /*
    接受一个 NULL 终止的 array 和一个 char 值，
    返回一个 bool 值，表示是否在 array 中找到了 val。
     */
    char *str;
    while ((str = *arr++) != NULL) {
        char ch;
        while ((ch = *str++) != '\0' && ch != val)
            ;
        if (ch == val) return TRUE;
    }
    return FALSE;
}

int main() {
    char *arr[2] = {"No Hello", NULL};
    printf("%d\n", find_char(arr, 'x'));
}