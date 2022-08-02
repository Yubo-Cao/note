#include <stdio.h>

char *my_strrchr(char const *str, int ch){
    char *pt = str + (strlen(str) - 1);
    while(pt >= str){
        if(*pt == ch)
            return pt;
        pt--;
    }
    return NULL;
}