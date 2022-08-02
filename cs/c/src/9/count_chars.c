#include <string.h>

int count_chars(char const *str, char const *chars){
    int count = 0;
    while(*str != '\0')
        if(strchr(chars, *str++) != NULL)
           count++;
    return count;
}
