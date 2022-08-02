# Array

## 一维数组

- ```c
  int a;
  int b[10];
  ```

- `b` 不是数组，而是指向数组头的（的元素的类型的）指针字面量。

  - 数组变量表现为指针当且仅当数组名作为表达式一部分使用。
  - 因为数组是指针字面量，其值不能被更改——整个数组移动到内存的其他位置是唯一的修改其位置的方式；而程序在运行时，内存中数组位置已经固定，不可修改。

- `sizeof` 返回整个数组的长度

- `&a[0]` 在大多数时候和 `a` 是等价的。但是，不可以对其进行赋值——字面量不是有效的左值。

## 下标

除了优先级之外，下标引用和间接访问完全相同

```c
array[subscript];
*(array + (subscript));
```

- 上述两个操作没有区别，因为数组在表达式中作为指针运算。

考虑这些表达式

```c
int arr[10];
int *ap = arr + 2;
```

| 表达式      | 间接引用                          | 解释                                     |
| ----------- | --------------------------------- | ---------------------------------------- |
| `ap`        | `arr + 2`                         | 第二个元素指针                           |
| `*ap`       | `*(arr + 2)`                      | 第二个元素                               |
| `ap[0]`     | `arr + 2`                         | 第二个元素指针                           |
| `ap + 6`    | `arr + 8`                         | 第八个元素指针                           |
| `*(ap + 6)` | `*(arr + 8)`                      | 第八个元素                               |
| `&ap`       | 谁知道 `ap` 在 `arr` 的什么位置？ |                                          |
| `ap[-1]`    | `arr + 1`                         | 第一个元素                               |
| `ap[9]`     | `arr + 11`                        | 第十一个元素；因为不存在，所以结果未定义 |
| `2[arr]`    | `arr[2]`                          |                                          |

- 上面的例子展示出下标检查在 C 语言的难度

## 指针和下标

- 假定指针和下标两种方式都可以，下标往往产生可读性更好的程序。
- 但是，**下标绝对不会比指针更有效率，有时指针比下标的速度更快**。从运行时效率的角度考虑，使用指针。

### 遍历数组

```c
#include <stdio.h>

int main(int argc, char const *argv[])
{
    int arr[10];
    for (int *ap = arr; ap < arr + 10; ap++) // 只有一次乘法运算， 1 * 4。这个运算在编译时就被优化了，所以运行时更高效。
        *ap = 0;
        
    printf("%d\n", arr[5]); 
    return 0;
}
```

```c
#include <stdio.h>

int main(int argc, char const *argv[])
{
    int array[10];
    for (int i = 0; i < 10; i++)
        array[i] = 0; // 每次都需要 i * 4，浪费时间和空间
    printf("%d\n", array[5]);
    return 0;
}
```

- 遍历数组的时候，**指针比下标更有效率。**这样的程序更小也更有效率。

### 相同效率

```c
a = get_val();
arr[a] = 0;
```

和

```c
a = get_val();
*(arr + a) = 0;
```

效率是一样的，因为 `a` 在编译期无法知道具体的值。

### 复制

指针有时比下标更有效率，前提是被正确的使用。

- 纯索引，直截了当，但是效率较差。`i * 4` 会被反复计算（聪明一些的现代编译器不会）

    ```c
    void arr_cpy(){
        for(i = 0; i < SIZE; i++)
            x[i] = y[i];
    }
    ```

- 指针办法没有快很多，因指针减法和除法。

    ```c
    void arr_cpy(){
        for(p1 = x, p2 = y; p1 - x < SIZE;)
            *p1++ = *p2++;
    }
    ```

- 快一些，因为指针减法被干掉了

    ```c
    void arr_cpy(){
        for(i = 0, p1 = x, p2 = y; i < SIZE; i++)
            *p1++ = *p2++;
    }
    ```

- 寄存器来避免复制指针值。这个办法带来很大改进，避免了复制指针

  ```c
  void arr_cpy(){
      register int *p1, *p2;
      register int i;
      for(i = 0, p1 = x, p2 = y; i < SIZE; i++)
          *p1++ = *p2++;
  }
  ```

- 消除计数器，直接比较地址。干掉自增自减运算，并且引入 `&x[SIZE]` 来加速（编译时求值）。这样的编译代码基本可以和编译程序员的代码相媲美了。

  ```c
  void arr_cpy(){
      register int *p1, *p2;
      for(p1 = x, p2 = y; p1 < &x[SIZE];)
          *p1++ = *p2++;
  }
  ```

- 根据固定数目的增量在数组中移动，指针变量比下标产生效率更高的代码。寄存器变量的指针比静态内存和堆栈中的指针效率更高。

- 如果可以测试一个已经初始化并调整大的内容判断循环是否可以终止，就可以避免单独的计数器。

- 常量表达式代价小。运行时求值的表达式更为昂贵。

**绝大多数情况下，都不应该使用最后一个写法——为了几十微秒的速度。**这产生莫名奇妙，无法维护，速度很快而无益的代码。

## 函数传值

- 所以参数都是传值进行的。

- 因为数组的值就是指向数组头部的指针，所以传递给函数的就是一份该指针的拷贝。
  - 函数可以自由操作指针形参，而不会担心修改传递进来的指针。
  - 间接访问操作可以使函数修改他们。

```c
void strcpy(char *buf, char const *str)
{
    while ((*buf++ = *str++) != '\0')
        ;
}
```

- 这里使用 `const` 用来解释函数的行为——这是一个良好的文档习惯，也方便编译器捕获试图修改这个数据的错误。这也会允许向该函数传递 `const` 参数的行为。
- 现代编译器应当会自动将 `buf` 和 `str` 变成 `register` 变量。手动分配可能会降低这些聪明的编译器的速度和效率。

## 声明数组参数

- 考虑如下两个不同的声明

```c
int strlen(char *string);
int strlen(char string[]);
```

- 应当使用指针的方式声明，因为指针更加准确。
- 这也解释了为什么函数原型中的一维数组形参无需写明其元素数目，因为函数并不为数组参数分配内存空间。以及，为什么我们无法获得数组的长度，而只是数组分配空间的长度。
- 如果数组的长度需要被获知，必须作为一个显示参数传递。

## 初始化

```c
int vector[5] = {10, 20, 30, 40, 50};
```

### 静态初始化

对于静态变量的数组，他们在程序开始之前时就被链接器完成了静态初始化，所有的初始值都被自动设置为0。

自动变量在每次进入代码块的时候进行初始化，默认是未初始化的。如果给出默认值，那么每次执行流进入作用域，都被隐式初始化一次——如果存在很多值，就可能产生的大量的的赋值语句，造成可观的初始化时间。

因此，就需要权衡利弊——将数组每次进行重新初始化是不是有必要的。如果不是，应使用 `static` 予以修正。

### 不完整

我们可以在给出数组长度的情况下省略初始化值。

```c
int vector[5] = {1,2,3}; // equiv {1, 2, 3, 0, 0}
```

或者，如果不给出长度，编译器就自动把数组的长度设置为能恰好容纳所有初始值的长度

### 字符串数组

```c
char msg[] = "Hello"; // equiv {'H', 'e', 'l', 'l', 'o', 0}
```

上面的初始化并没有产生一个字符串常量，而是字符串数组初始化的快速记法。具体是字符串，还是数组，取决于声明的变量类型。

## 多维数组

```c
int d[3][6][10];
```

- `d` 是一个包含三个元素的数组，每个元素都是一个包含 6 个元素的数组，这 6 个元素中，每个又是一个包含 10 个整形元素的数组。
  - `d` 是一个 3 层，6 行，10 列的三维数组

- `c` 数组的存储顺序为 `row major order`（行主序）；或换言之，按照最右边的下标先变化的方式。

```c
int array[3][6]
```

- 在内存中看起来是这样的

![image-20220712164926687](assets/image-20220712164926687.png)

- 我们可以使用一个指针来遍历数组，`flatten the array`。这在绝大多数情况下工作，但是实际上是非法的。所以还是避免为好。数组到底是 3 行 6 列还是6 列三行，完全取决于如何解释——但是，下标的规则是不变的。

```c
#include <stdio.h>

int main(int argc, char const *argv[])
{
    int matrix[3][4] = {
        {1, 2, 3, 4},
        {5, 6, 7, 8},
        {9, 10, 11, 12}};

    int *mp;
    mp = *matrix;

    printf("First value is %d\n", *mp);
    printf("Second value is %d\n", *++mp);
    printf("Third value is %d\n", *++mp);
    printf("Fourth value is %d\n", *++mp);
    printf("Welcome to next line, the value is %d\n", *++mp); // 5
    printf("Back to previous line, the value is %d\n", *--mp); // 4

    printf("%d\n", matrix[0][0]);
    printf("%d\n", matrix[2][3]); // 12
    //But matrix[3][2] won't work.

    return 0;
}
```

### 指针

- 一维数组的名字是指向首元素类型的的指针。
- 多维数组的名字是指向第一个数组的指针。一些老试的编译器并没有实现它。
- 多维数组的下标引用是间接访问的伪装，如

```c
int matrix[3][10];
matrix[1][5];
*(*(matrix  + 1) + 5); // 获得内部数组的指针，内部数组，以及 5 个偏移量的元素。

```

- 多维数组的下标利用指向数组的指针的概念，从左向右计算来工作。不断的获得数组头部指针，指针指向的数组，以及其指针；递归下去，直到找到一个数组元素

- C 提供了逗号表达式，所以对多维数组进行 `matrix[3, 4]` 这样的运算不会报错，但是也不会访问 `matrix[3][4]`，反而直接访问了 `matrix[3]`。这几乎总是一个错误，但是没有任何编译器警告。

### 指向数组的指针

```c
#include <stdio.h>

int main(int argc, char const *argv[])
{
    int vector[10], *vp = vector;
    int matrix[3][4], (*mp)[4] = matrix;
    return 0;
}
```

- 不可以使用 `*mp = matrix` 来声明指向整形数组的指针。`(*p)[10]` 这个表达式是这样计算的：`*p` 首先变成指针，然后下标引用指明这个指针指向某种类型的数组。

```c
    for(; mp < matrix + 3; ++mp)
        for(int *p = *mp; p < *mp + 4; ++p)
            *p = vp++ - vector,
            printf("%d\n", *p);
```

- 上面的程序利用数组指针和指针来对多维数组进行遍历。对于指向数组的指针，必须指明数组的长度（否则默认为 0 长度指针，加法运算的调整就没了）；这样，对指针进行加法运算时，就可以让指针一行一行在数组中移动。

### 作为函数参数

因为多维数组每个元素本身也是一个数组，编译器需要知道其第二维，以及其以后维度的长数才可能对其下标表达式进行求值。

```c
int fn(int (*mat)[10]);
int fn(int mat[][10]);
```

- 上面的声明方式是正确的。第一个下标根据有10个元素整形数组的长度调整，第二个下标根据整形长度调整。

```c
int fn(int **mat)
```

- 上述方法错误。这个和指向整形数组的指针不是一回事，而是指向整形指针的指针。

### 初始化

```c
#include <stdio.h>

int main(int argc, char const *argv[])
{
    int matrix[2][3] = {1, 2, 3, 4, 5, 6};
    int *mp = *matrix;
    for (; mp < matrix[1] + 3; ++mp)
        printf("%d\n", *mp); // 1 2 3 4 5 6
    return 0;
}
```

- 可以把所有的初始化元素都写成一行，此时，数组实际上为 `[[1,2,3],[4,5,6]]`

```c
int mat2[3][2] = {{1, 2}, {3, 4}, {5, 6}};
```

- 将每个数组都独立初始化。这样的方式产生更容易理解的代码。花括号可以用来利用 0 进行不完整的初始化列表。一个大型多维数组的每一维的初始化列表都是一个独立的初始化列表。

```c
int fd[2][2][2][2] = {{1}, {1}}; // 将每个立方体的 [0][0][0] 赋值为 1，其余 0
int fd[2][2][2][2] = {1, 0, 0, 0, 0, 0, 0, 0, 1}; // 难以阅读 
```

- 如果提供了初始化列表，第一维的长度可以省略——编译器将会自动推断第一位的维度。但是剩余的维度必须显示的写出，因为允许子列表省略初始化值。那么，就必须要 multiple pass 源代码才能初始化这个数组，所以编译器不做推断。

```c
int two_dim[][5] = {
    {00, 01, 02},
    {10, 11},
    {20, 21}
};
```

## 指针数组

```c
int *api[10];
```

- 这产生一个数组，其中每个元素都是之指向整形的指针（因为你可以对元素执行间接访问操作）

```c
#include <stdio.h>

int main(int argc, char const *argv[])
{
    char *kws[5] = {
        "return",
        "if",
        "else",
        "fn",
        "for",
    };

    for (int i = 0; i < 5; ++i)
        printf("%s\n", kws[i]);

    return 0;
}
```

- 判断一个词是否和关键字列表中的单词匹配，若匹配返回索引，反之 -1

```c
int lookup_kw(char const *const desired_word, char const *kws[], int const size)
{
    char const **kwp;
    for (kwp = kws; kwp < kws + size; ++kwp)
        if (strcmp(*kwp, desired_word) == 0)
            return kwp - kws;
    return -1;
}
```

- 我们也可以把关键字存储在矩阵中（此时无法用于这个函数）

```c
char const kws[][9] = {
    "do",
    "for",
    "if",
    "return",
   	"while"
};
```

- 这个声明创建了一个矩阵，每一行的长度刚好可以容纳最长的关键字——效率看起来比较低，因为每一行的长度都固定为刚好能容纳最长的关键字。但是，不需要指针。
- 想要让 `lookup_kw` 接受字符矩阵，只需要修改形参定义为 `char const (*kws)[9]` 即可。

## 问题

1. | 表达式        | 值                         | 表达式      | 值                            |
   | ------------- | -------------------------- | ----------- | ----------------------------- |
   | `ints`        | 100 (首元素指针)           | `ip`        | 112                           |
   | `ints[4]`     | 50                         | `ip[4]`     | 80                            |
   | `ints + 4`    | 116                        | `ip + 4`    | 128                           |
   | `*ints + 4`   | 14                         | `*ip + 4`   | 34                            |
   | `*(ints + 4)` | 50                         | `*(ip + 4)` | 80                            |
   | `ints[-2]`    | illegal                    | `ip[-2]`    | 20                            |
   | `&ints`       | 指向 ints 数组的指针的位置 | `&ip`       | 指向 ints[3] 元素的指针的位置 |
   | `&ints[4]`    | 116                        | `&ip[4]`    | 116                           |
   | `&ints + 4`   | 420                        | `&ip + 4`   | 436                           |
   | `&ints[-2]`   | `92`                       | `&ip[-2]`   | 104                           |

2. 不相等。一个访问 `array` 在 `i + j` 处的值（也可以作为左值）；另一个为 `i` 加上 `array` 在 `j` 处的值，并且不能作为左值

3. 行，不过对于传入 `idx = 0` 的情况下，可能会出现 `segmentation error`

4. ```c
   #include <stdio.h>
   #include <stdbool.h>
   #include <string.h>
   
   bool is_palindrom(char const *const str)
   {
       char const *p = str;
       char const *q = str + strlen(str) - 1;
       while (p < q)
           if (*p++ != *q--)
               return false;
       return true;
   }
   
   int main(int argc, char const *argv[])
   {
       char *str = "racecar";
       printf("%s is %s\n", str, is_palindrom(str) ? "a palindrom" : "not a palindrom");
       return 0;
   }
   ```

5. 下标可以产生可读性更好的代码。使用指针如果产生狗屁不通的代码，还不如不使用指针。

6. pass 我不懂汇编

7. pass

8. 

9. ```c
   int main(int argc, char const *argv[])
   {
       int coin_values[] = {1, 5, 10, 25, 50, 100};
       return 0;
   }
   ```

10. | 表达式         | 值                                   |
    | -------------- | ------------------------------------ |
    | `array`        | 1000                                 |
    | `array + 2`    | 1004                                 |
    | `array[3]`     | array[3] 的值                        |
    | `array[2] - 1` | array[2] 的值 - 1                    |
    | `&array[1][2]` | 实质上越界，但是 1000 + 4 + 4 = 1008 |
    | `&array[2][0]` | 1008                                 |

11. | 表达式               | 值   | 类型              |
    | -------------------- | ---- | ----------------- |
    | `array`              | 1000 | `(*int)[2][3][6]` |
    | `array + 2`          | 1288 | `(*int)[2][3][6]` |
    | `array[3]`           | 1432 | `int[][3][6]`     |
    | `array[2] - 1`       | 1216 | `int[][3][6]`     |
    | `array[2][1]`        | 1360 | `int[][6]`        |
    | `array[1][0] + 1`    | 1168 | `int[][6]`        |
    | `array[1][0][2]`     | 1192 | `int[]`           |
    | `array[0][1][0] + 2` | 1080 | `*int`            |
    | `array[3][1][2][5]`  | ?    | `int`             |
    | `&array[3][1][2][5]` | 1572 | `*int`            |

12. 如果我们希望展平一个数组

13. | 表达式                       | 下标             |
    | ---------------------------- | ---------------- |
    | `*array`                     | `array[0]`       |
    | `*(array + 2)`               | `array[2]`       |
    | `*(array + 1) + 4`           | `array[1] + 4`   |
    | `*(*(array + 1) + 4)`        | `array[1][4]`    |
    | `*(*(*(array + 3) + 1) + 2)` | `array[3][1][2]` |
    | `*(*(*array + 1) + 2)`       | `array[0][1][2]` |
    | `*(**array + 2)`             | `array[0][0][2]` |
    | `**(*array + 1)`             | `array[0][1][0]` |
    | `***array`                   | `array[0][0][0]` |

14. 如果 `i` 被声明为 `*int` ，就不会产生任何警告或者错误的通过编译

15. 第二条语句更合理，因为我们希望避免数组越界。

16. `array1` 是数组的指针，并且其长度并不能保证为 `10` 或是内存已分配。但是 `array2` 是数组，并且其长度必定为10，内存必定被分配。除此以外，第一个传入的参数可以被修改，而第二个参数是一个字面量，不可以被修改。

17. 第一个 `const`仅作为文档的注释，因为传入的是一个标量。第二个`const` 避免数组内部的值被改变。

18. `void function(int (*array)[2][5])`

## 编程

### 2.

```c
#include <stdio.h>

float THRESHOLD[] = {0, 23350.0, 56550.0, 117950.0, 256550.0};
float TAX_RATE[] = {0.15, 0.28, 0.31, 0.36, 0.396};

float single_fax(float income)
{
    float *thres_pt = THRESHOLD, *tax_pt = TAX_RATE, tax = 0.0;

    while (income > *++thres_pt)
        tax += *tax_pt++ * (*thres_pt - thres_pt[-1]);

    tax += *tax_pt * (income - thres_pt[-1]);
    return tax;
}

int main(int argc, char const *argv[])
{
    printf("%.2f\n", single_fax(40000.0f));
    return 0;
}
```

### 3.

```c
#include <stdbool.h>

bool identity_matrix(int const *const matrix[10])
{
    int i;
    for (i = 0; i < 10; ++i)
        if (matrix[i][i] != 1)
            return false;
    return true;
}
```

```c
#include <stdio.h>
#include <stdbool.h>

bool identitiy_matrix(int *mat, int sz)
{
    int row, col;
    for (row = 0; row < sz; row++)
        for (col = 0; col < sz; col++)
            if (*mat++ != (row == col))
                return false;
    return true;
}
```

- Second implementation is more robust because it checks the situation where the identity matrix is expected to be zero.

## 4. 

```c
void matrix_multiply(int *m1, int *m2, int *r, int x, int y, int z)
{
    register int *m1p, *m2p;
    int row, col;

    for (row = 0; row < x; row++)
        for (col = 0; col < z; col++)
        {
            m1p = m1 + row * y;
            m2p = m2 + col;
            *r = 0;
            for (int i = 0; i < y; i++, m1p++, m2p += z)
                *r += *m1p * *m2p;
            r++;
        }
}
```

