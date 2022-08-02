#include <stdio.h>
#include <string.h>

// concat src to dst, make sure no overflow assuming sizeof(dst) == dst_len
void my_strncat(char *dst, char *src, int dst_len){
    strncpy(dst, src, dst_len - strlen(dst));
}