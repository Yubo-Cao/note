#include <stdio.h>
#include <stdlib.h>

#include "alloc.h"
#undef malloc

void *alloc(size_t size)
{
    void *new_mem;
    new_mem = malloc(size);
    if (new_mem == NULL)
    {
        printf("Out of Memory\n");
        exit(EXIT_FAILURE);
    }
    return new_mem;
}
