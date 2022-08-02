#include <stdlib.h>
#include <stdio.h>
#include "inventory.h"

/**
 * @brief Create a subassy reocrd object
 * This is ACID compliant, i.e., it fails, or success. It never leave memory leaks
 * @param n_parts
 * @return Inventory*
 */
Inventory *create_subassy_reocrd(int n_parts)
{

    Inventory *new_rec;
    new_rec = malloc(sizeof(Inventory));
    if (new_rec == NULL)
        return NULL;
    new_rec->info.subassy = malloc(sizeof(SubassyInfo));
    if (new_rec->info.subassy == NULL)
    {
        free(new_rec);
        return NULL;
    }
    new_rec->info.subassy->part = malloc(n_parts * sizeof(PartInfo));

    if (new_rec->info.subassy->part == NULL)
    {
        free(new_rec->info.subassy);
        free(new_rec);
        return NULL;
    }
    new_rec->type = SUBASSY;
    new_rec->info.subassy->part_count = n_parts;
    return new_rec;
}

Inventory *create_subassy_reocrd_btr(int n_parts)
{
    Inventory *new_rec;
    new_rec = malloc(sizeof(Inventory));
    if (new_rec != NULL)
    {
        new_rec->info.subassy = malloc(sizeof(SubassyInfo));
        if (new_rec->info.subassy != NULL)
        {
            new_rec->info.subassy->part = malloc(n_parts * sizeof(PartInfo));
            if (new_rec->info.subassy->part != NULL)
            {
                new_rec->type = SUBASSY;
                new_rec->info.subassy->part_count = n_parts;
                return new_rec;
            }
            free(new_rec->info.subassy->part);
        }
        free(new_rec);
    }
    return NULL;
}