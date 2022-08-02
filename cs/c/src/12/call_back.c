#include <stdio.h>
#include <stdlib.h>
#include "node.h"

// 类型无关的链表查找。
// void * 是必须的；void * 可以转化为任何类型指针，然后就完了

Node *node_search(Node *node,
                  void const *val,
                  int (*cmp)(void const *, void const *))
{
    while (node != NULL)
        if (cmp(node->val, val) == 0)
            return node;
        else
            node = node->next;
    return NULL;
}

Node *node_create(size_t const sz, size_t const nmemb, void const *val)
{
    Node *cur, *nxt = NULL;

    for (int i = sz - 1; i >= 0; i--)
    {
        cur = malloc(sizeof(Node));

        if (cur == NULL)
            for (; nxt != NULL; nxt = nxt->next)
                free(nxt);

        cur->val = val + i * nmemb;
        cur->next = nxt;
        nxt = cur;
    }

    return cur;
}

void node_print(Node *node, void (*print)(void const *))
{
    while (node != NULL)
    {
        print(node->val);
        node = node->next;
    }
}

void print_int(void const *val)
{
    printf("%d\n", *(int *)val);
}

int test_int(void const *a, void const *b)
{
    return *(int *)a - *(int *)b;
}

int main(int argc, char const *argv[])
{
    int a[] = {1, 2, 3, 5, 4};
    Node *node = node_create(5, sizeof(int), a);
    node_print(node, print_int);
    int target = 2;
    node_print(node_search(node, &target, test_int),
               print_int);
    return 0;
}
