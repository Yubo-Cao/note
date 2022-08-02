#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct
{
    int cost;
    int supplier;
} PartInfo;

typedef struct
{
    int part_count;
    struct
    {
        char partno[10];
        short count;
    } * part;
} SubassyInfo;

typedef struct
{
    char partno[10];
    int count;
    enum
    {
        PART,
        SUBASSY
    } type;
    union
    {
        PartInfo *part;
        SubassyInfo *subassy;
    } info;
} Inventory;