// 接口
#include "addr_list.h"
#include <stdio.h>

// 结构体更好，不过我们没有 struct
static char name[MAX_ADDRSSES][NAME_LENGTH];
static char address[MAX_ADDRSSES][ADDR_LENGTH];
static char phone[MAX_ADDRSSES][PHONE_LENGTH];

static int find_entry(char const *name_to_find)
{
    int entry;
    for (entry = 0; entry < MAX_ADDRSSES; entry += 1)
        if (strcmp(name_to_find, name[entry]) == 0)
            return entry;
    return -1;
}


char const *lookup_address(char const *name_to_find)
{
    int entry = find_entry(name_to_find);
    if (entry == -1)
        return NULL;
    return address[entry];
}

char const *lookup_phone(char const *name_to_find)
{
    int entry = find_entry(name_to_find);
    if (entry == -1)
        return NULL;
    return phone[entry];
}

