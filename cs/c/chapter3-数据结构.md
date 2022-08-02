# C 和数据

- 我们将会学习 C 中变量的三个属性
  - 作用域
  - 链接属性
  - 存储类型
- 我们将会考虑他们的可视性和生命周期

## 基本数据类型

- `Integer`
- `Float`
- `Pointer`
- 和聚合类型，即一个或多个基本数据类型的集合

### 整型

- `char`, `short`, `int`, 和 `long`
- 他们都有各自的 `signed` 和 `unsigned` 版本

> 长整形至少和整型一样长，而整型至少和短整型一样长。

- 下面为各个变量的最小范围，ANSI 在这个上面的规定极大提升了可移植性。特别的，`int` 的究竟是 32 还是 16 为整型，由编译器的设计者决定，为该机器最为高效的位数。

| 类型                 | 最小范围                        |
| -------------------- | ------------------------------- |
| `char`               | 7  bit                          |
| `signed char`        | -127 - 128 （8 bit）            |
| `unsigned char`      | 0 - 255 （8 bit）               |
| `short int`          | -32767 - 32767                  |
| `unsigned short int` | 0 - 65535 (16 bit)              |
| `int`                | -32767 - 32768 (16 bit)         |
| `unsigned int`       | 0 - 65535 (16 bit)              |
| `long int`           | $-2^{31}+1$ - $2^{31}$ (32 bit) |
| `unsigned long int`  | 0 - 4294967295 (32 bit)         |

- 所有大小的限制都被定义在 `limits.h` 中。通过 `#include <limits.h>` 可以获得如下信息。

```c
#include <limits.h>
#include <stdio.h>
#include <stdlib.h>

int main(void)
{
    printf("int max: %d int min: %d\n", INT_MAX, INT_MIN);
    printf("uint max: %u\n", UINT_MAX);

    printf("long max: %ld long min: %ld\n", LONG_MAX, LONG_MIN);
    printf("ulong max: %lu\n", ULONG_MAX);

    printf("long long max: %lld long long min: %lld\n", LLONG_MAX, LLONG_MIN);
    printf("ulong long max: %llu\n", ULLONG_MAX);

    printf("char max: %d char min: %d\n", CHAR_MAX, CHAR_MIN);
    printf("signed char max: %d signed char min: %d\n", SCHAR_MAX, SCHAR_MIN);
    printf("uchar max: %u\n", UCHAR_MAX);
    return EXIT_SUCCESS;
}
```

```text
int max: 2147483647 int min: -2147483648
uint max: 4294967295
long max: 9223372036854775807 long min: -9223372036854775808
ulong max: 18446744073709551615
long long max: 9223372036854775807 long long min: -9223372036854775808
ulong long max: 18446744073709551615
char max: 127 char min: -128
signed char max: 127 signed char min: -128
uchar max: 255
```

#### `char`

- 需要注意的是，`char` 并不能保证 `char` 为 `signed char` 或者 `unsigned char` —— 他们不过是很小整数。仅当程序使用的 `char` 型变量都是 `signed char` 和 `unsigned char` 的交集（ASCII）中，程序才是可移植的。
  - 编译器将会自己随便选择 `signed char` 和 `unsigned char`
  - 直接声明为`signed/unsigned char` 是个问题，因为大多数字符库函数都是处理 `char` 的：显示声明往往带来兼容性问题。
  - 为了可移植性，只有 `char` 被声明为 `unsigned` 和 `signed` 才进行计算，其他时候都保证 `char` 变量在 0 - 127 中。

#### `int/long/char` literal

- 字面量，又称字面值常量，是一种制定其自身值并且不允许发生改变的实体。

- 具体是什么整型类型，取决于后面的后缀
  - `l` 或 `L` 后缀使得整数被解释为 `long`
  - `U` 或 `u` 后缀则变成 `unsigned` 整型值
  - 如果两个一起用，就是 `unsigned long`
- 例子
  - `123`
  - `65535`
  - `-275` 这个不是字面量，而是 `unary operator -` + 字面量
- 不同进制
  - 以 `0` 开头得到八进制
  - 以`0x` 开头得到十六进制
  - 如果一个字面值主要用于位运算，那么写成十六进制或八进制更为合适，便于阅读。
- 如果没有明确指明后缀，那么其字面值的类型就是 `int`, `uint`, `long`, `ulong` 中最短但足以容纳整个值的类型。
- `char` 是已一个单引号包围起来的单个字符
  - 如 `'M'` 是字符字面量。
  - 特别的，也可以存在多字节字符常量，但是在不同的环境中可能不一样，如 `'abc'` 因此不建议使用。
  - `L` 在多字节字符常量前就是 `wide character literal`, 宽字符常量。
  - 字符字面量不论采用何种字符集，总是产生正确的值，为此可提升程序的可移植性。

#### 枚举

- `enum` 是一种值为符号常量而不是字面值的类型，实质上一 **整型的方式存储**，所有的符号名的实际值都是整型值。
  - 可以将枚举变量作为整型参与运算，但是不建议。
  - 可以给枚举变量赋值；一个没有被赋值的变量在前面有枚举变量的情况下为前面的枚举变量的值 +1, 或为 0。

```c
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
    enum Jar_Type
    {
        CUP,
        PINT,
        QUART,
        HALF_GALLON,
        GALLON
    };
    enum Jar_Type jar_type = CUP;
    printf("%d\n", jar_type); // 0
    enum
    {
        CUP_SIZE = 8,
        PINT_SIZE = 16,
        QUART_SIZE = 32,
        HALF_GALLON_SIZE = 64,
        GALLON_SIZE = 128
    } gallon_sz = GALLON_SIZE;
    printf("%d\n", gallon_sz); // 128
    printf("%d\n", CUP_SIZE + 1); // 9
    return EXIT_SUCCESS;
}
```

### 浮点型

- 浮点型通过一个小数和某个假定数我为基数的指数组成，如
  - `.1100010010000011111x2^2` 等等。
- `float`, `double`, `long double` 是 C 提供的浮点数，分别为单精度，双精度，以及扩展精度的浮点数。
  - 所有浮点类型至少容纳 $10^{-37}$ 至 $10^{37}$ 之间的值 —— 事实上，这比 32 位整数所能容纳的数字大的多，不过代价就是精度。
- `float.h` 定义了浮点数家族成员的最大值。

```c
#include <stdio.h>
#include <stdlib.h>
#include <float.h>

int main(int argc, char const *argv[])
{
    printf("float max: %f\n", FLT_MAX); // 3.40282347e+38
    printf("float min: %f\n", FLT_MIN); // 0
    printf("double max: %lf\n", DBL_MAX); // 1.7976931348623157e+308
    printf("double min: %lf\n", DBL_MIN); // 0
    printf("long double max: %Lf\n", LDBL_MAX); // 1.18973149535723176502e+4932L
    printf("long double min: %Lf\n", LDBL_MIN); // 0
    return EXIT_SUCCESS;
}
```

- 这个头文件当然也定义了关于机器精度之类，有效数字，等等。
- 浮点数可以有一个指数或是一个小数点。
- 后缀
  - 无后缀为 `double`
  - `F` 或 `f` 为 `float`
  - `L` 或 `l` 为 `long`

```c
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
    printf("%f\n", 3.14159);
    printf("%f\n", 1E10);
    printf("%f\n", .25);
    printf("%f\n", -6.023e23);
    return 0;
}
```

### 指针 `Pointer`

- 优缺点

  - 指针使得程序员可以高效实现 `tree`, `list` 等 `ADT`。

  - C 对指针不加限制，给予程序员更多自由的同时也是错误的根源。

- 概念

  - 变量的内存地址就是指针，或可认为是内存地址的别名
  - 指针变量的值为内存地址的变量。
  - C 语言允许我们获得指针指向的值或者数据结构，或者通过值获得指针。

#### 指针字面量

- C 语言没有 pointer literal/pointer constant。因为值在内存中的具体位置取决于编译器，操作系统，栈空间等等——程序员**无法实现知道变量在内存中存储的位置**。
- 所以，常量表达式为数值字面值的形式**毫无意义**

#### 字符串字面量

- C 语言的字符串是一串以 `NUL` 字节结尾的 0 或多个字符。
- 字符串通常存储在**字符数组中**，因此没有显示的字符串类型。`NUL` 字节用于中介字符串，故字符串内不能有 `NUL` 字节 —— 但是这个字符不可打印，所以往往不出现问题。
- 字符串可以为空。
- 如下几个例子都是字符串：
  - `"Hello"`
  - `"\aWarning!\a"`
  - `"Line 1\nLine2"`
  - `""`
- ANSI C 没有明确规范对字符串常量进行修改的结果（未定义的）——为此，编译器可以把一个字符串常量存储在同一个地方以便复用。这就使得修改字符串常量极端危险，可能会影响其他字符串常量。**如果需要修改，请使用字符数组**。

- 字符串是**指向字符的指针的字面量**。字符串出现在表达式中时，表达式使用的是字符所存储的地址，而非字符本身——所以，可以声明一个 `char *` 变量用来指向这些字符串存储的地址——但是，不可以把字符串赋给一个字符数组，因为为字符串常量的直接值是指针，而不是字符本身（字符数组）。

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char const *argv[])
{
    char *str = "Hello, World!";
    printf("%s\n", str);
    char str2[100];
    strcpy(str2, str);
    printf("%s\n", str2);
    return EXIT_SUCCESS;
}
```

- `string.h` 包含了操作字符串的常见函数。

## 基本声明

```c
specifier declaration_exprs;
```

- `specifier`

  - 用于描述被声明的标识符的

    1. 类型，如 `int`, `char` 等

    2. 指明变量的长度或是否有符号，如 `short`, `long`, `signed`, `unsigned` 等

    3. 如果已经存在 2, 1 可以被省略

  - 例子

    - | `specifier` | `equiv specifier` |
      | ----------- | ----------------- |
      | `short`     | `signed short/signed short int/short int`       |
      | `int` | `signed int/signed` |  
      | `long` | `signed long/signed long int/long int` |
      | `unsigned short` | `unsigned short int` |
      | `unsigned int` | `unsigned` |
      | `unsigned long` | `unsigned long int` |

    - 默认为 `signed`

    - 浮点类型除了 `long double` 之外，其余的说明符都不可用。

- 声明表达式可以是

  - 被声明的标识符的列表，即 `identifiers`
  - 表达式，显示被声明的名字的可能用处。
    - 可以进行初始化，如 `int j = 15;`
    - 可以声明数组，如 `int values[20];`
      - 通过对 `identifier` `values` 进行下标运算，产生了一个 `int` 类型的值，即 `int`。
      - 数组的下标从 0 开始。
      - 编译器不会进行数组下标检查。所以，只要是通过用户输入产生的下标，就应当检查下标是否越界。
    - 可以声明指针，如 `int *a;`
      - `*`  indirection (间接访问/间接寻址 运算符)。这个运算符只对指针变量有效。
      - 产生的结果类型为 `int`, `a` 是一个指向 `int` 的指针。
      - 建议把间接访问符号和标识符连接在一起，因为
        - `int* a, b, c` 只声明了一个指针，`a`
        - `int *a, *b, *c` 声明了三个指针
      - 指针变量可以被赋予初始值，例如 `char *msg = "Hello world!";`，不过这里初始化赋予 `msg`，而非表达式 `*msg`

- 隐式声明
  - 不显示声明参数，返回值，或变量的类型，编译器就会默认认为他们都是整型。

## `typedef`

- 这个语句可以用来给数据类型定义新的名字。其声明的写法和普通声明相同，不过 `typedef` 出现在前面，如下：

```c
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
    typedef char *char_pt;
    char_pt str = "Hello, World!";
    printf("%s\n", str);
    return EXIT_SUCCESS;
}
```

- 特别的，不要使用 `#define` 来创建新的类型，因为其无法处理指针类型。

## `const`

- 使用 `const` 来声明常量，他们的值不能修改。`const` 在 `specifier` 的顺序是任意的。
- 初始化常量
  - 在声明时初始化，如 `int const a = 15;`
  - 在函数中声明 `const` 的形参表明函数不修改形参，但是调用时得到实参的值。
  - 指针变量以及其实体都可能变成常量，
    - 如果声明 `int const *pci`, 可以修改指针的值，但是不能修改其指向的值。
    - 如果声明 `int * const pci`，可以修改指向的值，但是指针，作为一个常量指针的值，不能修改。
    - 加入两个 `const` 来组织对其值、或指针本身的修改。
- `define` 声明的常量可以在任何需要字面值常量的地方使用，例如，声明数组的长度；而 `int const` 变量只能在允许使用变量的地方使用。
- 名字常量很有用，极大的提升了程序的可维护性。

## 作用域

- `lexical scope`
  - 变量仅在某些部分可以使用。
  - 你可以重复使用一些名字。

### 代码块

- `block scope`
- 每个成对的代码块都有自己的作用域。代码块，就是一对花括号之间的语句。
  - 每个变量都将使用最内层存在的变量，即，局部变量可以覆盖/shadow外部代码块中的变量。
  - 如果内层没有，将会到自己父级代码块寻找，以此类推（嵌套代码块）
  - 如果不是嵌套的代码块，不可以访问兄弟代码块的变量。**因为两个非嵌套的代码块不能同时处于活动**，编译器可以让多个变量共享同一个内存地址。

### 文件作用域

- `filescope`
- 在代码块之外声明的标识符，从它们声明之处直到源文件的结尾处都是可以访问的。
  - 例如，函数名
  - `#include` 指令包含在代码中的声明，他们的作用域如同直接写在源文件中的文件作用域声明。

### 函数作用域

- `function scope`
- 用于 `goto` 语句标签的声明。要求必须唯一。现代的代码中，我们希望减少 `goto` 语句的使用。

## 链接属性

- 当一个源文件被分别编译后，所有的目标文件以及那些从一个或多个函数库中引用的函数连接在一起，形成可执行程序。

- `linkage` 决定相同标识符在不同源文件出现时，表示什么实体。

- 三种链接属性
  - `external` 外部
    - 不论声明次数，源文件位置都是同一个实体。
  - `internal` 内部
    - 在同一个源文件的所有声明都指向同一个实体，但是在不同源文件的多个声明分属不同的的实体。
  - `none` 无
    - 没有链接属性的标识符为单独的个体，也就是该标识符的多个声明被当作独立不同的实体。
  
- 链接属性的判断

  - ```c
    typedef char *a;
    int b;
    int c(int d)
    {
        int e;
        int f(int g); // 函数调用
    }
    ```

  - `b`, `c`, `f` 的链接属性为 `external`

  - 其余的链接属性则为 `none`

  - 因此，在别的源文件访问这个源文件时，将会获得该源文件所定义的实体。

  - 函数名为 `external`, 因为它链接到其他源文件/函数库，必须为 `external`

- 强行改变链接属性

  - ```C
    static int i;
    int func()
    {
        int j;
        extern int k;
        extern int i;
    }
    ```

  - `extern` 关键字可以让标识符获得 `external` 链接属性，这样就可以让别的源文件访问这个变量作为外部变量。

  - 如果一个声明原本有 `external` 链接属性，加上 `static` 使其变成 `internal` 的链接属性——**避免被其他源文件调用**，私有方法。

    - 但是，只有原本为 `external` 的变量在加入这个关键字才有意义。如果本身不是 `external`，则毫无意义的。

  - 反复声明并不会改变第一次声明指定的链接属性。

## 存储类型

- `storage class` 是存储变量值的内存类型。如下三个不同的类型都是可以的：
  - 普通内存
    - 所有代码块之外声明的变量都存储在这里，静态内存。
      - 他们的存储类型必须在这里，不能被指定。
      - `static` 变量也被指为这种变量。
      - 他们在程序运行之前创建，在整个执行期间存在，并且始终保持原先的值，除非赋一个不同的值或程序续结束。
  - 运行时堆栈
    - 代码块内部声明的变量的默认存储类型是自动的（`automatic`），即村存储在运行时堆栈中。
      - `auto` 用于修饰这种变量，但是几乎没用因为默认就是 `automatic`
      - 这些变量在被执行时创建，在数据流离开时被销毁。每次执行都会被创建、销毁——因此，基本上自动变量的值总是在变化的。
    - 使用关键字 `static` 来将其变成静态的变量。
      - 在整个程序执行过程中一直存在，但是作用域仍然限定在代码块内部。
      - 函数的形参不能为静态，因为递归支持的需要。
  - 硬件寄存器
    - `register` 可以被放在自动变量上，用于提示编译器将变量存储在寄存器而不是内存中——**寄存器变量**。
    - 编译器并不一定理睬寄存器变量，甚至可能忽略
      - 如果太多的变量为寄存器变量，那么只选取其中前几个存储于寄存器中，其余的就当普通变量。
      - 如果有自己的优化方案，可能完全忽略。
      - 寄存器变量的创建和销毁时间和自动变量相同，但是需要额外工作，用以保证调用者的寄存器变量未被破坏——**所以，可能优化的反而更慢了**。
- 初始化
  - 静态变量在可执行文件载入内存的时候就已经初始化了；如果没有初始值（垃圾），那么将会初始化为0（默认值）
  - 自动变量的初始化更为灵活，因此需要更多的开销。
    - 没有默认初始值，必须显示初始化（并且，显式初始化将会隐式地插入一条赋值语句），否则总是垃圾。
    - 使用 `int a=1`  和 `int a; a =1;` 的效率因此是一样的。
    - 每次执行到函数或代码块时，自动变量都会重新初始化。静态变量尽在程序开始执行前初始化一次。

### 示例

```c
int a = 5; // 静态
extern int b; // 静态
static int c; // 静态

int d(int e) // d 作为函数原型，默认有 external 属性。别的文件可以使用这个函数，但是建议包含函数原型（事实上，只有返回值不是整型的时候才需要原型）
    // 这样的代码总是存储在静态内存中
{
  int f = 15;
  register int b; // 遮盖 extern b
  static int g = 20;
  extern int a; // 什么都没有发生， a 已经被声明
  // ...
  {
      // 他们都是 auto 属性
      int e; // 形参 e 被遮盖
      int a; // 遮盖 extern static a
      extern int h; // 全局变量 h 可以在该代码块中使用，必须使用 extern，存储于静态内存中。非如此，只不过再次定义 h 局部变量。
  }
  // ...
  {
      // 自动，无链接属性，作用域局限于这个代码块
      int x;
      int e; // 形参 e 再次被遮盖
      // ...
  }
}

static int i(){
  // ...
}
```

## 问题

1. 字符范围为 -128 - 127. 我有 `short`, `int`, `long`, `long long`. 他们分别是 16, 32, 64, 64 位的整数。
2. 浮点数不知为何，都是大于 0 的。但是他们的都非常大。
3. 定义头文件 `myints.h`

```c
#include <limits.h>

// ensure myshort is at least 16 bit
#if SHRT_MIN >= 32767
#define shrt short
#else
#define shrt int
#endif

// Ensure myint is at least 32 bit
#if INT_MIN >= 2147483647
#define intr int
#else
#define intr long
#endif
```

- 当然，使用 `stdint.h` 效果完全一样

4. ```c
   #include <stdio.h>
   #include <limits.h>
   
   int main(int argc, char const *argv[])
   {
       long var = LONG_MAX;
       short v2 = var; // 溢出，但不报错
       printf("%d", v2);
       return 0;
   }
   ```

5. ```c
   #include <float.h>
   #include <stdio.h>
   
   int main(int argc, char const *argv[])
   {
       double num = DBL_MAX - 1.0;
       float num2 = num;
       printf("%g\n", num2); // inf
       printf("%f\n", num2); // inf
       return 0;
   }
   ```

- 很不幸， C 似乎没有类型转化检查之类的功能。

6. ```c
   #include <stdio.h>
   
   int main(int argc, char const *argv[])
   {
       enum Coin
       {
           PENNY = 25,
           NICKEL = 1
       };
   
       enum Coin price = PENNY;
       printf("%d", price);
       return 0;
   }
   ```

7. 尝试使用 `%s` 来打印整型类型的数据，造成了 `Address boundary error` 我将其改为 `%d`, 程序运行并打印 32, 48.

   ```c
   #include <stdio.h>
   
   int main(int argc, char const *argv[])
   {
       enum Liquid
       {
           OUNCE = 1,
           CUP = 8,
           PINT = 16,
           QUART = 32,
           GALLON = 128
       };
       enum Liquid jar;
       jar = QUART;
   
       printf("%d\n", jar);
       jar += PINT;
       printf("%d\n", jar);
       return 0;
   }
   ```

8. `gcc` 是我的编译器，它会将同样的字符串使用存储在同个位置，因此不可以。

9. `signed` 关键字对于 `char` 这样的默认情况下不一定是有符号类型的类型进行处理。**别的地方也可以用只是点缀**

10. 否，信息量是一样的。

11. `int` 的精度更大。

12. 没有任何不同。

13. 第二种方式将会报错，不能 assign to const variable

14. 不对。嵌套代码块内部的局部变量可以遮盖外部代码块的局部变量，使其无法被访问。

15. 不对。`extern` 不改变变量的作用域，其他函数无论如何都是无法访问的。

16. 不会。`static` 在这里只改变变量存储的方式，而非其作用域。

17. 不需要，虽然加上 `static` 可以阻止外部访问。

18. 不会，在文件作用域中，怎样都是可以访问的。

19. 不需要，虽然加上 `extern` 可以使我们的意图更明显。

20. 会，请干掉 `static` 否则就是内部变量了。

21. 有可能，但是很大可能这就是个垃圾：堆栈到底存储在哪里，等等，不确定。

22. 一个是 `static extern` 变量，一个是 `auto` 变量

23. ```c
    #include <stdio.h>
    
    char static b = 2;
    
    int y(void)
    {
    }
    
    int extern a = 1;
    
    int x(void)
    {
        int c = 3;
        int d = 4;
    }
    ```

24. 
