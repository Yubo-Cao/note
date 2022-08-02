# 结构，指针，和链表

## 单链表

![image-20220717110630210](/home/yubo/.config/Typora/typora-user-images/image-20220717110630210.png)

### 实现

```c
typedef struct Node {
    struct Node *link;
    int val;
} Node;
```

- 我们首先声明一个跟指针，然后顺着其指针向下行走，直到碰到 `NULL` 指针，象征列表的结束。
- 链表的节点可能村咋与内存中的任何位置；对于一个处理链表的程序，各节点是否在物理位置上相邻没有意义的。
- 单链表无法反方向遍历；因此，如果想要回到前一个节点，就必须从头开始重新遍历。
  - 将链表顺序存储也是可能的。

### 插入（有序链表）

```c
bool sil_insert(Node *cur, int new_val)
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
```

- 上述的插入不能处理插入首元素的情况。对于这样的用例，必须传入首指针的指针。

```c
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
```

- 上述的优化方案解决了首元素，不存在结尾元素的情况。

```c
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
```

- 最后，我们通过直接获得指向下一个元素的指针的指针的方式结束了 `prev == NULL` 这样的判断。
  - C 提供了很好的工具来归纳问题的共性
  - 对数据结构的理解，同样重要
  - 这样的操作威力巨大，但是也容易产生错误的代码

## 双链表

- 每个节点包含指向前一个节点和后一个节点的指针。因此，我们可以使用多种方式遍历双链表
- 在我们的实现中，双链表被实现为类似于环形队列的操作。你可以倒着来，也可以顺着来，甚至循环来。

```c
typedef struct Node
{
    struct Node *fwd;
    struct Node *bwd;
    int val;
} Node;
```

### 实现

- 我们希望实现一个有序集——即其中的元素不可以重复。我们对如下几种情况进行探讨
  - 链表为空
  - 头插入
  - 尾插入
  - 中间插入
- 头，中间插入需要考虑修改下一个节点的`bwd` 指针为当前节点，并且插入节点的 `fwd` 为下一个节点。
- 尾，空需要将 `fwd = NULL` 并且修改根节点/前一个节点的 `fwd` 。根节点的 `bwd` 需要指向插入节点。
- 中间，结束需要修改新节点 `bwd` 为链表前一个节点，前一个节点的`fwd` 为插入节点
- 起始，空链表，`bwd = NULL`

```c
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
```

- 通过 if 语句提炼，我们可以产生更小更紧凑的代码。但是这样的代码可读性差，并不是更好的代码

## 问题

1. ```c
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
   ```

2. 