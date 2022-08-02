#include <stdio.h>
#include <string.h>

char *my_strcpy_end(char *dst, char *src)
{
   strcpy(dst, src);
   return dst + strlen(src) - 1; 
}