#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

#include "SilNode.h"

bool insert_sil(Node *cur, int new_val)
{
    Node *prev, *new;

    // find the node that has data larger than new_val
    // or, if not exists, insert at end
    while (cur->data < new_val)
    {
        prev = cur;
        cur = cur->next;
        if (cur == NULL)
            break;
    }

    new = malloc(sizeof(Node));
    if (new == NULL)
        return false;

    new->data = new_val;
    prev->next = new;
    new->next = cur;
    return true;
}

void print_sil(Node const *head)
{
    while (head != NULL)
    {
        printf("%d ", head->data);
        head = head->next;
    }
    printf("\n");
}

bool insert_sil_btr(Node **rootp, int new_val)
{
    Node *cur = *rootp, *prev = NULL, *new;
    while (cur != NULL && cur->data < new_val)
    {
        prev = cur;
        cur = cur->next;
    }
    new = malloc(sizeof(Node));
    if (new == NULL)
        return false;
    new->data = new_val;
    new->next = cur;
    if (prev == NULL)
        *rootp = new;
    else
        prev->next = new;
    return true;
}

bool insert_sil_btr2(register Node **linkp, int new_val)
{
    register Node *cur, *new;
    while ((cur = *linkp) != NULL && cur->data < new_val)
        linkp = &cur->next;
    new = malloc(sizeof(Node));
    if (new == NULL)
        return false;
    new->data = new_val;
    new->next = cur;
    *linkp = new;
    return true;
}

int main(int argc, char const *argv[])
{
    Node last = {NULL, 15};
    Node prev = {&last, 10};
    Node head = {&prev, 5};
    Node *root = &head;
    print_sil(root);
    insert_sil_btr2(&root, 3);
    print_sil(root);
    return 0;
}
