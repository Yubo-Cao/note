# 语句

## 空语句

`;`

## 表达式语句

- 赋值是一个表达式，所以直接在赋值表达式后面加上一个分号就可以实现赋值语句了。

```c
x = y + 3;
```

- 因为 C 允许任何表达式作为一个语句出现，所以如下的语句是合法的：

```c
y + 3;
getchar();
```

- 他们的结果在被求值后立刻丢弃，但是事实上，函数也是表达式——所以我们能调用函数，还要感
  谢这种语法设计。通常情况下，这被称为 `side effect` -- 利用副作用。利用副作用的例子还有：

```c
a++;
printf("Hello World!\n");
```

## 代码块

```c
{
    declarations
    stmts
}
```

- 代码块中可以存在声明或者任何 `stmts`。代码块可以用来任何需要语句的地方，因为其本身也
  是一条语句。这样的设计允许在只允许一条语句的地方使用多条语句。

## `if`

```c
if(expr)
    stmt
else
    stmt
```

- 括号是 `if` 语句的一部分。因此，就算是再简单的表达式也强制要求括号的出现。
- 注意：这里要求的 `stmt` 而不是 `stmts`
- `expr != 0` 为真， `expr == 0` 为假 —— C 没有布尔类型。
  - 因此，C 中关系运算符（比较）返回都是 0 或 1，而非 false/true.
  - 整型变量可以用于表示布尔值。特别的，两个不同的非零值相比较结果为假，这就是没有布尔
    变量的遗憾了。
  - `dangling else`: `else` 子句总是和最接近的 `if` 进行配对，而和缩进无关。这是因为
    `parser` 是贪婪匹配 `else` 的。
    - 如果确实需要不一样的行为，请使用代码块。

## `while`

```c
while (expr)
    stmt
```

- 同样的，`expr != 0` 测试。

### `break` 和 `continue`

- `break` 在 `while` 中跳出循环。
- `continue` 结束本次循环，测试 `expr` 之后决定是否再次执行循环。
- 如果存在嵌套的循环，只对最内层循环起作用。

### 执行过程

- 首先判断 `expr` 的值
  - 如果不为 0, 运行 `stmt`
  - 如果为 0, 跳出循环
  - 如果 `stmt` 中的 `break` 被执行，跳出循环
  - 如果 `stmt` 中的 `continue` 被执行，判断 `expr` 决定是否继续执行

![image-20220629212731295](/home/yubo/.config/Typora/typora-user-images/image-20220629212731295.png)

- 有些时候，`expr` 以及其副作用就已经完成了任务。可以使用 `;` 空语句。
- 使用 `if(expr) dosomething` 和 `if(!expr) continue` 是没有性能上的区别的。

## `for`

```c
for (init_expr; cond_expr; upd_expr)
    stmt
```

- `stmt` 是循环体
- `init_expr` 是初始化，仅在循环开始的时候执行一次
- `cond_expr` 是条件，每次循环前都会执行并检查
- `upd_expr` 是调整/更新循环变量，用于在条件部分执行之前执行
- 三个表达式都是可选的。`cond_expr` 如果省略，则循环条件总为真。
  - `break`, `continue` 仍然可以使用。

### 执行过程

- 解除语法糖

```c
init_expr;
while(cond_expr){
    stmt;
    upd_expr;
}
```

- `for` 事实上和 `while` 循环是一样的。不过，唯一特别的地方是 `continue` 语句会直接跳过
  循环体的剩余部分而直接回到调整部分，而不是条件部分。
- `for` 循环风格上有优势，可以把所有操作循环的表达式收集在一起，方便寻找。

## `do`

类似于 `repeat` 语句，测试在循环体执行之后才进行，而不是先于循环体执行。所以，这个循环
的循环体至少执行一次。

```c
do
    stmt
while(expr);
```

- 我们使用 `do-while`, 如果循环体需要**至少执行一次**。

## `switch`

```c
switch(expr)
    (case const_expr:
        stmt)+
```

- `expr` 的结果必须是整型值。
- `switch` 语句内部可以只用一条单一的语句，但是毫无意义。大多数时候，我们都是使用 `case const_expr: stmt`
- `const_expr` 必能是变量，必须能在编译期间求值。
- `switch` 语句的执行流贯穿各个 `case` 标签，而不是停留在单个 `case` 标签 —— 这只是语句
  列表的进入点，而不用于划分他们。`break` 语句用来解决 `passthrough` 的问题。
  - `default` 语句可以出现在任何地方，并且总是被最后执行。加入 `default` 语句可以让程序
    更强健，而不至于静悄悄的接受错误值。
  - `fallthrough` 的情况很罕见。为此，建议在特别需要这种特性的地方加入注释声明意图，以
    防被认为是 bug.

```c
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

int get_days_since_start(int month);
int parse_int(const char *str);

int main(int argc, char const *argv[])
{
    int month, result, days;
    if (!(argc == 3 && 1 <= (month = parse_int(argv[1])) <= 12 && 1 <= (days = parse_int(argv[2])) <= 31))
    {
        printf("Please input month: ");
        scanf("%d", &month);
        printf("Please input days: ");
        scanf("%d", &days);
    }
    printf("%d\n", get_days_since_start(month - 1) + days);
    return EXIT_SUCCESS;
}

int parse_int(const char *str)
{
    char *pt;

    errno = 0;
    long try = strtol(str, &pt, 10);

    if (errno != 0 || *pt != '\0' || try < INT_MIN || try > INT_MAX)
        printf("Invalid input %ld\n", try);
    else
        return (int)try;
}

int get_days_since_start(int month)
{
    int days = 0;
    switch (month)
    {
    case 12:
        days += 31;
    case 11:
        days += 30;
    case 10:
        days += 31;
    case 9:
        days += 30;
    case 8:
        days += 31;
    case 7:
        days += 31;
    case 6:
        days += 30;
    case 5:
        days += 31;
    case 4:
        days += 30;
    case 3:
        days += 31;
    case 2:
        days += 28;
    case 1:
        days += 31;
    }
    return days;
}
```

## 结语

- C 没有任何**异常处理语句**。
- C 所有的 IO 操作都是通过标准库中的函数实现的。

## 练习

1. 是合法的，它计算了一个变量 `x`  3 *x * x - 4*x + 6 然后丢弃这个运算结果。

2. `identifier = expr`  // 错误，C 没有 assignment stmt, 只有 expr.

3. 是合法的，但是我不曾想过这样使用。

4. 可以在 `then` 语句中加入空语句，或者将 `cond_expr` 取反，直接将 `else` 语句变成 `then` 语句。

5. 将会打印从 0 到 9 的数字。因为 `i == 10` 的时候循环条件不满足，所以不打印 10.

6. **如果没有初始化或者adjustment**，使用 while

7. 这个实现虽然会正确的计算 `checksum` ，但是只会将最后一个字符输出到标准输出（值得注意的是，这往往就是EOF字符，并非有效的字符），因为 `while` 后面的循环体只有 `checksum += ch`

8. 如果你希望至少计算一次。

9. **将会打印 odd, even, odd, even, 即 1 - 4 之间的奇偶数。**错误，因为没有 break，所以对于所有偶数，这两条信息都将被打印。

10. ```c
    #include <stdio.h>
    #include <stdlib.h>
    
    int main(int argc, char const *argv[])
    {
        printf("Please input a number: ");
        int n;
        scanf("%d", &n);
        while (n != 0)
        {
            printf("\n");
            n -= 1;
        }
        return 0;
    }
    ```

11. ```c
    #include <stdint.h>
    #include <stdio.h>
    
    void read_int(char *name, int *result)
    {
        printf("Please input '%s': ", name);
        scanf("%d", result);
    }
    
    int main(int argc, char const *argv[])
    {
        int x, y, a, b;
        read_int("x", &x);
        read_int("y", &y);
        read_int("a", &a);
        read_int("b", &b);
        if (x < y || a >= b)
            printf("WRONG\n");
        else
            printf("RIGHT\n");
        return 0;
    }
    ```

12. ```c
    #include <stdio.h>
    
    int main(int argc, char const *argv[])
    {
        int year;
        printf("Please input a year: ");
        scanf("%d", &year);
        // 被 4 整除且不被 100 整除，或被 400 整除
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
            printf("%d is a leap year.\n", year);
        else
            printf("%d is not a leap year.\n", year);
        return 0;
    }
    ```

13. ```c
    #include <stdio.h>
    #include <stdlib.h>
    
    // we want to return a boolean value about whether we are hungry
    int hungry(void)
    {
        return rand() & 1;
    }
    
    void eat_hamberger(void)
    {
        printf("I'm eating a hamburger.\n");
    }
    
    int main(int argc, char const *argv[])
    {
        do
        {
            eat_hamberger();
        } while (hungry());
        return 0;
    }
    ```

## 编程

1. 平方根

```c
long double my_sqrt(long double x)
{
    // 更好的实现应当检查负数等情况
    long double result = 1;
    long double last_result;
    do
    {
        last_result = result;
        result = (result + x / result) / 2;
    } while (result != last_result);
    return result;
}
```

2. 质数

```c
#include <stdio.h>

int main(int argc, char const *argv[])
{
    for (int i = 0; i <= 100; i++)
    {
        int is_prime = 1;
        for (int j = 2; j < i; j++) // 优化，偶数
        {
            if (i % j == 0)
            {
                is_prime = 0;
                break;
            }
        }
        if (is_prime)
        {
            printf("%d, ", i);
        }
    }
    return 0;
}
```

3. 三角形分类

```c
#include <stdio.h>

void *scan_trig(int *trig)
{
    for (int i = 0; i < 3; i++)
    {
        scanf("%d", &trig[i]);
    }
    return trig;
}

int is_trig(int *trig)
{
    // 三角形三边关系
    return trig[0] + trig[1] > trig[2] &&
           trig[0] + trig[2] > trig[1] &&
           trig[1] + trig[2] > trig[0];
}

char *classify_trig(int *trig)
{
    if (trig[0] != trig[1] && trig[1] != trig[2])
        return "scalene";
    else if (trig[0] == trig[1] && trig[1] == trig[2])
        return "equilateral";
    else
        return "isosceles";
}

int main(int argc, char const *argv[])
{
    int trig[3];
    scan_trig(trig);
    if (!is_trig(trig))
        printf("Not a triangle\n");
    else
        printf("A %s triangle\n", classify_trig(trig));
    return 0;
}
```

更好的实现，包括了异常处理以及一个简单的排序

```c
#include <stdio.h>
#include <stdlib.h>

void swap(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

int main(int argc, char const *argv[]) {
    float a, b, c;
    printf("Enter the three sides of a triangle: ");
    scanf("%f %f %f", &a, &b, &c);

    // Then let's sort them, so that a is largest
    if (a < b) swap(&a, &b);
    if (a < c) swap(&a, &c);
    if (b < c) swap(&c, &b);
    // a > b > c
    // if c <= 0, then it's not a triangle
    // if b + c <= a, then it is degenerate
    if (c <= 0 || b + c <= a) {
        printf("Not a triangle\n");
        return EXIT_FAILURE
    } else if (a == b && b == c)
        printf("Equilateral\n");
    else if (a == b || b == c)
        printf("Isoceles\n");
    else
        printf("Scalene\n");
    return EXIT_SUCCESS;
}
```

4. `copy_n` 复制字符串数组

```c
void copy_n(char dst[], char src[], int n)
{
    int src_len = sizeof(src) / sizeof(src[0]);
    int dst_len = sizeof(dst) / sizeof(dst[0]);

    int i = 0;
    // 如果 src 的长度小于 n, 就尽可能多的复制
    while (i < n && i < src_len && i < dst_len)
    {
        dst[i] = src[i];
        i++;
    }
    // 如果 src < n, 那么就在后面补上 NUL 字符
    while (i < dst_len && i < n)
    {
        dst[i] = '\0';
        i++;
    }
}
```

5. 特别的 `unique`

```c
#include <stdio.h>
#include <string.h>

#define MAX_LEN 128

int main(int argc, char const *argv[])
{
    char line[MAX_LEN];
    char prev_line[MAX_LEN];

    while (fgets(line, MAX_LEN, stdin))
    {
        if (strcmp(line, prev_line) == 0)
        {
            printf("%s", line);
            // Consume anyline hereafter that are same as prev_line
            while (fgets(line, MAX_LEN, stdin) && strcmp(line, prev_line) == 0)
                ;
        }
        strcpy(prev_line, line);
    }

    /* code */
    return 0;
}
```

6. `substring`

```c
#include <stdio.h>
#include <string.h>
#define MIN(X, Y) (((X) < (Y)) ? (X) : (Y))

int substring(char dst[], char src[], int start, int len)
{
    int i;
    int dst_len = strlen(dst), src_len = strlen(src);
    if (start < 0 || start >= src_len || len < 0)
        return 0;
    // 具体复制多少，看len是否超出src的长度
    len = MIN(src_len - start, len);
    for (int i = 0; i < len; i++)
    {
        dst[i] = src[start + i];
    }
    return len;
}
```

- 为了避免 `strlen` 和 `sizeof` 调用，下面的实现更好

```c
#include <stdio.h>

int substr(char dst[], char src[], int start, int len) {
    int src_idx;
    int dst_idx = 0;

    if (start >= 0 && len > +0) {
        // Advance src_idx to the start index. Stop if string is already
        // terminated before start
        for (src_idx = 0; src_idx < start && src[src_idx] != '\0'; src_idx++)
            ;
        // Notice if previous one is terminated before start, then src[src_idx] == '\0'
        // Copy characters from src to dst, stopping when string is terminated
        while (len > 0 && src[src_idx] != '\0') {
            dst[dst_idx] = src[src_idx];
            src_idx++;
            dst_idx++;
            len--;
        }
    }
    dst[dst_idx] = '\0';
    return dst_idx;
}
```

7. `trim`

```c
void trim(char str[])
{
    // i 用来过滤 str 中的空格，j 指向 trim 后字符串的位置
    int i = 0, j = 0;
    while (str[i] == ' ')
    {
        i++;
    }
    int len = sizeof(str) / sizeof(str[0]);
    while (str[j] != '\0')
    {
        // remove continued spaces
        while (str[i] == ' ' && (str[i + 1] == ' ' || str[i + 1] == '\0'))
        {
            i++;
        }
        str[j] = str[i];
        i++;
        j++;
    }
    str[j] = '\0';
}
```

- 使用同样的两个 `pointer` 的方式，这次，我们使用指针！

```c
#include <stdio.h>
#define NUL '\0'

void deblack(char* str) {
    char* dst;
    char* src;
    int ch;

    src = str;
    dst = str++;

    // Set the src and dst to beginning to str.
    // Move str to second char

    while ((ch = *src++) != NUL) {
        if (is_white(ch)) {
            // At beginning
            // previous char is not white space
            // store it
            if (src == string || !is_white(dst[-1])) *dst++ = ' ';
        } else {
            // Just store it
            *dst++ = ch;
        }
    }
    *dst = NUL;
}

int is_white(int ch) {
    return ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r';
}
```

- 魔法？？？
