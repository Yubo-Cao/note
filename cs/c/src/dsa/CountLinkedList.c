#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "SilNode.h"

int sil_len(Node *head)
{
    int len = 0;
    while (head != NULL)
    {
        len++;
        head = head->next;
    }
    return len;
}

void sil_print(Node *head)
{
    while (head != NULL)
    {
        printf("%d ", head->data);
        head = head->next;
    }
    printf("\n");
}

Node *sil_create(int *arr, int len)
{
    Node *head = NULL;
    int *pt = arr + len - 1;
    Node *cur, *nxt = NULL;
    while (pt >= arr)
    {
        cur = (Node *)malloc(sizeof(Node));
        if (cur == NULL)
        {
            for (; cur != NULL; cur = cur->next)
                free(cur);
            return NULL;
        }
        cur->data = *pt;
        cur->next = nxt;
        nxt = cur;
        pt--;
    }
    head = cur;
    return head;
}

Node *sil_index_of(Node *head, int val)
{
    while (head != NULL)
    {
        if (head->data == val)
            return head;
        head = head->next;
    }
    return NULL;
}

bool sil_remove(struct Node **rootp, struct Node *node)
{
    Node *cur = *rootp;
    if (cur == node)
    {
        *rootp = cur->next;
        free(cur);
        return true;
    }
    while (cur != NULL)
    {
        if (cur->next == node)
        {
            cur->next = node->next;
            free(node);
            return true;
        }
        cur = cur->next;
    }
    return false;
}

int main(int argc, char const *argv[])
{
    Node *head = sil_create((int[]){1, 2, 3, 4, 5}, 5);
    sil_print(head);
    printf("%d\n", sil_len(head));

    Node *tmp = sil_index_of(head, 3);
    sil_print(tmp);

    sil_remove(&head, tmp);
    sil_remove(&head, head);
    sil_remove(&head, sil_index_of(head, 5));

    sil_print(head);

    return 0;
}
