# 问题

## 简答题

1. 这保证了良好的可阅读性
2. 这可以避免多次写同样的内容。通过这种方式，我们可以只需要维护一个预处理命令的的声明，
   就可以到处使用了。
3. `#define` 定义编译期常量。之后对`define` 定义的变量的每一处使用，都会被直接替代为字面量。避免了手动声明变量的麻烦。

4. ```c
    #include <stdio.h>
    #include <stdlib.h>

    int main(void){
        int num = 10;
        printf("%d\n", num);
        double num2 = 10.02;
        printf("%g\n", num2);
        char c = 'a';
        printf("%c\n", c);
        printf("%d %g %c\n", num, num2, c);
    }
    ```

5. `scanf` test

```c
#include<stdio.h>

int main(void){
    int quantity;
    scanf("%d", &quantity);
    double price;
    scanf("%lf", &price);
    char* department;
    scanf("%s", department);
    printf("%d %g %s\n", quantity, price, department);
}
```

6. C 语言强调信任程序员，为了更高速的性能，为此不检查数组的下标。
7. 如果使用 `strcpy`, 那么 `output` 字符串将被反复的覆盖，无法打印。
8. 可能会出现溢出的问题，即，读入了过多的内容。

## 编程练习题

1. Hello world

```c
#include <stdio.h>

void main(void)
{
    printf("Hello world!\n");
}
```

2. 读取输入并打印到标准输出，加入行号并且避免字符串长度限制。

```c
#include <stdio.h>
#include <stdlib.h>

// 通过逐个字符处理的方式来解决字符串溢出的问题。
int main(void)
{
    int ch;
    int line = 0;
    int at_beginning = 1;

    while ((ch = getchar()) != EOF)
    {
        if (at_beginning)
        {
            at_beginning = 0;
            line++;
            printf("%d ", line);
        }
        putchar(ch);
        if (ch == '\n')
        {
            at_beginning = 1;
        }
    }

    return EXIT_SUCCESS;
}
```

3. 寻找输入的 `checksum`。


