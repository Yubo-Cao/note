#include <stdio.h>
#include <string.h>

char *strdup(char const *str){
    char *new_str = malloc(strlen(str) + 1);
    if (new_str != NULL)
        strcpy(new_str, str);
    return new_str;
}