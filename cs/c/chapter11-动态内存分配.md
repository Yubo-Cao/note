# 动态内存分配

- 数组的长度必须为编译期常量，因此无法在运行时创建数组——他们的内存在编译时就已经被分配。
- 动态内存分配可以用来在运行时分配内存。

## Why

- 很多时候，数组的长度只有在运行时才知道——他们的内存空间取决于输入数据。
  - 声明大数组造成
    - 内存空间的浪费
    - 超过了数组的容纳范围时，程序就会失败。是编写时的限制阻止了我们利用计算机的资源。
    - 数组永远不会溢出的错误想法——我们假定程序的输入都是有效的。

## 函数

### `malloc`

- `void *malloc(size_t size)`

- 从内存池中拿出内存，并返回指向这块内存的指针。
  - 如果无法拿出内存，就返回 `NULL`
  - 返回的内存没有初始化
- 传入需要分配的内存的字节数即可。
- 分配连续内存。分配的内存可能比请求的稍微多一点，处于边界对齐的需要。但是我们不能指望这一点。
- `void *` 指针可以被转化为任何类型的指针。

### `free`

- `void free(void *pointer)`
- 释放内存池中的内存，以供以后之需
- 接受一个 `NULL` 指针。这么做不会返回任何结果。

### `calloc`

- `void *calloc(size_t num_elements, size_t element_size)`
- 计算需要分配的内存的大小，根据元素数量和元素大小。
- 将分配的内存初始化为 0

### `realloc`

- `void *realloc(void *ptr, size_t new_size)`
- 修改一个已经分配的内存块的大小，可以使内存块扩大或缩小。
  - 如果第一个指针为 `NULL`，和 `malloc` 的行为一样
  - 总是应当使用该方法返回的指针，因为 `malloc` 可能会将旧内存复制到新内存空间，如果原本的地方没有位置了。

## 例子

```c
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
    int *pi;
    pi = malloc(25 * sizeof(int));
    if (pi == NULL)
    {
        printf("Out of Memory\n");
        exit(EXIT_FAILURE);
    }

    int *pt;
    for (pt = pi; pt < pi + 25; pt++)
        *pt = pt - pi + 1;

    int i;
    for (i = 0; i < 25; i++)
        printf("%d\n", pi[i]);
    return 0;
}
```

- 返回的内存可以作为数组使用，也可以作为指针使用（本来间接访问和下标就是差不多的）
- 因此，在上面的例子中，我们使用两个循环，一个指针，一个下标来初始化并遍历数组。

## 错误

### `NULL` 解除引用——没有检查返回的指针是不是 `NULL`

- 可以通过预处理指令来解决这个问题

```c
#include <stdlib.h>

#define malloc
#define MALLOC(num, type) (type*) alloc((num) * sizeof(type))
extern void *alloc(size_t size);
```

```c
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

```

```c
#include <stdio.h>

#include "alloc.h"

int main(int argc, char const *argv[])
{
    int *pi;
    pi = alloc(25 * sizeof(int));
    for (int *p = pi; p < pi + 25; p++)
        *p = p - pi + 1;
    for(int i = 0; i < 25; i++)
        printf("%d\n", pi[i]);
    return 0;
}

```

### 操作时越界

### 释放内存

- 非动态分配的内存，或者释放动态分配内存的一部分，或者在释放后继续使用动态内存都是错误的。
- `realloc` 通过改变动态内存的大小来实现释放部分动态内存。
- 动态分配的内存不再需要使用时需要释放，这样就可以被重新分配利用。
  - 如果操作系统的所有程序共享一个通用内存池，那么内存泄漏将会榨干所有内存。
  - 如果操作系统记住程序使用的内存，并在程序终止时释放；对于一个长期运行的程序仍然存在耗尽内存的可能。

## 例子

### 排序

```c
#include <stdlib.h>
#include <stdio.h>

int compare_integers(void const *a, void const *b)
{
    register int const *pa = a;
    register int const *pb = b;
    return *pa > *pb ? 1 : *pa < *pb ? -1
                                     : 0;
}

int main()
{
    int *arr;
    int n_values;
    int i;

    printf("Enter the number of values: ");
    if (scanf("%d", &n_values) != 1 || n_values <= 0)
    {
        printf("Invalid number of values\n");
        return EXIT_FAILURE;
    }

    arr = malloc(n_values * sizeof(int));
    if (arr == NULL)
    {
        printf("Out of Memory\n");
        return EXIT_FAILURE;
    }

    printf("Enter the values: ");
    for (i = 0; i < n_values; i++)
    {
        if (scanf("%d", &arr[i]) != 1)
        {
            printf("Invalid value\n");
            return EXIT_FAILURE;
        }
    }

    qsort(arr, n_values, sizeof(int), compare_integers);

    for (i = 0; i < n_values; i++)
        printf("%d\n", arr[i]);
    free(arr);
    return EXIT_SUCCESS;
}
```

### 动态内存拷贝字符串

```c
char *strdup(char const *str){
    char *new_str = malloc(strlen(str) + 1);
    if (new_str != NULL)
        strcpy(new_str, str);
    return new_str;
}
```

- 使用这个函数，我们就可以方便的处理读取字符串进入缓冲区，或者很大的而不确定最大长度的字符串。

### 动态内存创建结构

```c
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
```

