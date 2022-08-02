#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "DblNode.h"

/**
 * Insert a new node into a double linked list.
 * @param rootp The pointer to the root of the double linked list.
 * @param new_val The value of the new node.
 * @return true if successful, false otherwise.
 */

bool insert_dbl(Node *rootp, int val)
{
    Node *cur, *nxt, *new;
    for (cur = rootp; (nxt = cur->fwd) != NULL; cur = nxt)
    {
        if (nxt->val == val)
            return false;
        if (nxt->val > val)
            break;
    }
    new = malloc(sizeof(Node));
    if (new == NULL)
        return false;
    if (nxt != NULL)
    {
        if (cur != rootp) // middle
        {
            cur->fwd = new;
            new->bwd = cur;
            nxt->bwd = new;
        }
        else // head
        {
            rootp->fwd = new;
            new->bwd = NULL;
            nxt->bwd = new;
        }
        new->fwd = nxt;
    }
    else
    {
        if (cur != rootp) // tail
        {
            cur->fwd = new;
            new->bwd = cur;
            rootp->bwd = new;
        }
        else // init
        {
            rootp->fwd = new;
            new->bwd = NULL;
            rootp->bwd = new;
        }
        new->fwd = NULL;
    }
    return true;
}