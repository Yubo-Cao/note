#  Operator & Expr

- C 提供了所有你期望编程语言提供的运算符，是甚至一些意想不到的。
- C 令人诟病的一点也在于此，因为提供的操作符太多。我们将会学习求值的规则，运算符优先级，以及类型转换（算数转换？）

## 运算符

### Arithmetic

- `+ - * / %`
- 除了 `%`，其他的所有运算符都可以用于浮点类型和整数类型。
  - `%` 只接受整数运算符
  - `/` 在两个操作符都是整数的时候，才会执行整除运算。反之，都是浮点数除法。

### Bit wise

- 简单的把一个二进制位进行移动，溢出的高位/低位将被直接丢弃。
  - 左移动
    - 右边空出来的用 0 补齐。
  - 右移动
    - 逻辑移位：空出的位用 0 填充
      - 无符号只执行的所有移位操作都是逻辑移位
    - 算数移位：空出的位根据符号填充
      - 有符号值进行的右移位到底是逻辑还是算数取决于编译器，**不可移植**
      - 根据本人在 `gcc` 上的测试，是算数移位。
- `<<` 为左移位运算符，`>>` 为右移位运算符。操作数只能为**整型类型**

```c
// Simple function to count bits that are one in number
int count_one_bits(unsigned val) {
    int ones;
    for (ones = 0; val; val >>= 1) {
        ones += val & 1;
    }
    return ones;
}
```

### 位操作

- `&` 和
- `|` 或
- `^` 异或
- `~` 非/求补

利用这些运算符，我们可以将整型位的值进行修改：

- `val = val | 1 << bit_number` 可以把指定位置的 bit 设定为 1
- `val = val & ~ (1 << bit_number)` 可以把指定位置的 bit 清 0
- `val & 1 << bit_number` 测试一个位是否为 1

### 赋值

- 赋值是表达式，其结果位左值的新值。例如，`x = y + 3` evaluate to `y + 3`
- 赋值表达式是 right associative, 所以，从右至左进行求值，如：`a = x = y + 3` 相当于

```c
x = y + 3;
a = x;
```

- 特别的，赋值表达式甚至可以作为更多运算的一部分，如 `r = s + (t = u-v) / 3` 相当于

```c
t = u - v;
r = s + t / 3;
```

- 所以，不要走极端！有些时候，多写两行代码没什么坏处。

> 警告 :warning:
>
> `a = x = y + 3` 这个表达式，说其等同于 `a = y + 3`, `x = y + 3` 是错误的。因为事实上，`a` 被赋予的是 `y + 3` 可能被截断（用于给 `x` 赋值后），然后在可能被提升（a 可能是更大的数据类型），的值。这种截断是造成难以调试的代码的原因，如：
>
> ```c
> char ch;
> while((ch = getchar()) != EOF) {
>     
> }
> ```
>
> EOF 是整型，而 ch 是字符型。所以，循环可能永远不会终止，或者过早的终止，取决于 EOF 在具体字符集上的定义！

### 复合赋值

```c
a += expr;
// Is equivalent to
a = a + (expr);
// 注意，上述写法保证 expr 在加法运算前已被完整求值，哪怕包括优先级低于加法运算的操作福。
```

- 这个符号可以写出更加清晰的代码，也便于编译器进行优化。如下：

```c
a[2 * (y - 6 * f(x))] = a[2 * (y - 6*f(x))] + 1;
a[2 * (y - 6 * f(x))] += 1; 
```

- 下面的代码效率更高也更简洁。一方面，函数不需要被写两遍；另一方面，编译器也可以确认函数 f 没有副作用，因此只需计算一次。

### `unary`

```c
! ++ - & sizeof
~ -- + * (type)
```

- `!` 进行逻辑取反， `~` 是求补操作
- `+`, `-` 是表示符号的运算符。事实上，`+` 毫无用处。
- `&` 产生操作数的地址
- `*` 是间接访问操作符，用于访问指针所指向的值。

```c
int a, *b; // a 是整型变量， b 是指向整型变量的指针
b = &a; // b 现在指向 a 的地址
printf("%d\n", *b); // 获得 a 的值
```

- `sizeof` 获得操作数的类型长度，以字节为单位。操作数可以是单个表达式，或者加上括号的类型名。如

```c
sizeof x; // 获得变量 x 所占据的字节数
sizeof (int); // 获得整型变量的字节数
```

- 特别的，如果传入一个数组，将会返回数组的长度。

- 表达式的操作数两边可以加上括号——也就是括号表达式。

- **获取表达式的长度不需要对表达式求值**，因此，`sizeof(a = b + 1)`的 `side effect` 并不会发生

- `(type)` 用来执行强制类型转换，`cast`。其优先级很高，基本上总是改变其后第一个标志的值，所以，为了将整个表达式进行强制类型转换，必须用括号将表达式括起来。

- `++` 和 `--` 要求其操作数必须为一个左值或右值，并且有前缀和后缀形式。

  - 在前面的话，表达式的值为左值增加了以后的左值；在后面话，表达式的值为左值被增加之前的值。

  - ```c
    int a, b, c, d;
    a = b = 10;
    c = ++a; // 11
    d = b++; // 10
    // a, b 此时为 11
    ```

  - 增值操作符都获得了变量值的拷贝并将其进行运算，返回的是变量值的拷贝。为此，`++a = 10` 这样的运算是不合法的。

### 关系

- `> >= < <= != ==`
- 上述所有运算符返回的都是整型，而非布尔值。在 `if`, `while` 等语句中，他们根据这样的规则来判断：`0 = false`, `!= 1 -> true`。因此，下面的一些简写是合理的：

```c
if (expr != 0) ;
// is equivalent to
if (expr) ;

if (expr == 0) ;
// is equivalent to
if (!expr) ;
```

- 需要注意的是不要把 `=` 符号写到 `if` 和 `while` 里面去。这虽然是合法的，但是往往不是你的意图所在。

### 逻辑

- 逻辑操作符在 C 中都是短路的。也就是，形如 `expr1 ||/&& expr2`的表达式而言，如果在不计算 `expr2` 的情况下可以得到表达式的值，那么 `expr2` 将不会被计算。
- 这被称为 **短路求值**（**short circuited evaluation**），在检查数组下标是否合法等方面很有用。
- 需要注意的是，他们和位运算符是不可互换的
  - 位运算符不会短路
  - 为运算符不根据 0 和非 0 来计算，反而是操作符中对应的位

### 条件/三元

- `cond ? expr1 : expr2` 是否加入括号基本上不会产生问题，因为其优先级实在太低。但是，大多数时候我们都倾向于给其加上括号。
- 这个运算符用来让 `if` 语句变成表达式，如

```c
if (a > 5)
    b[2 * c + d(e / 5)] = 3;
else
   	b[2 * c + d(e / 5)] = -20;
// 相较于下面这个写法，上面的写法冗长并且产生更大的目标代码
b[2 * c + d(e / 5)] = a > 5 ? 3 : -20
```

### 逗号

- 用来将多个表达式分割开来，然后从从左向右逐个求值。
- 整个逗号表达式的值就是最后那个表达式的值，而前面的部分就被丢弃了。这方便我们写出更复杂的循环条件，或者初始化等等。

```c
a = get_value();
count_value(a);
while(a > 0){
    a = get_value();
    count_value(a);
}
```

这个代码不如下面这个代码，如

```c
while(count_value(a = get_value()) > 0)
    ;
while(a = get_value(), count_value(a), a > 0)
    ;
```

- 使用逗号表达式提升可读性。

- 不过，不要过度利用逗号运算符。如果它不能让程序变得更易读，就不要使用它。

```c
while(x < 10)
    b += x,
	x += 1;
```

- 这个办法不是个好做法，但是确实让代码写的和 `Python` 一般了。

### 下标引用、函数调用、和结构体

```c
array[idx]
*(array + (idx))
```

上述两种写法在获得数组元素的值时，都是一样的。C 的下标引用操作和间接访问表达式是等价
的。

函数调用操作符接受一个或多个操作数，第一个为希望调用的函数名，而剩余的是传给函数的参
数。表达式因此可以代替常量作为函数名。

`.` 和 `->` 用来访问结构的成员。如果 `s` 是结构变量，那么 `s.a` 访问 `s` 中名为
`a` 的成员。如果有结构的指针，就要使用 `->` 来访问成员。

## 布尔

- **0** 是假，任何非零值皆为真。

- 为此，将两个真值相互进行比较时应当将整型变量替换为 `!= 0` 来避免奇怪的异常。

  - 例如

  - ```c
    #define FALSE 0
    #define TRUE 1
    
    if (flag == TRUE) {}
    ```

  - 上述代码并不能比较 `flag` 是否为真还是假——比较的是是不是 0 或者 1。

- 避免混合使用整型和布尔值，任何整型都应当在 `!= 0` 后加入运算。

  - 如果一个变量不是布尔值，就不应该使用 `if (x)` 的简写。

## 左值和右值

- 左值 `L-Value` 和右值 `R-Value`。任何左值就是可以出现在赋值符号左边的东西，右值就是可以出现在赋值符号右边的东西。
- `a = b + 25` 中，`a` 是左值（指明存储结果的位置），而 `b + 25` 是一个右值（指定一个值）。
  - 但是 `b + 25` 不可以作为一个左值，因为其结果存储的位置不是不确定的，那么下次再次运行这个表达式就无法无法访问这个值——所以没有意义。
  - 因此，字面量和大多数表达式不能作为左值。
- 但是，左值可以是表达式，如
  - `int a[30]; a[b + 10] = 0;` 上述左值是表达式，只不过返回了一个元素的位置
  - `int a, *pi; pi = &a; *pi = 20;` 这里将指向位置进行修改，因此也是合法的左值。

## 表达式求值

- 优先级和结合性
- 可能需要隐式类型转化

### 隐式类型转换

- 所有算数计算都是以默认整型类型的精度完成的，所以所有的比 `int` 更小的值都会在计算前提升为整型（**Integral promotion**）。
- 例如， `char a, b, c; a = b + c;` 中的计算，`b + c` 将会计算出一个整数，然后截断在放回 `a` 中。
- 在例如，`a = (~a ^ b << 1) >> 1` 用于计算一系列字符的 `checksum`；计算之前，所有变量都变成整型。如果编译器认为不使用整型不会造成溢出，那么也不必使用整型。

### 算数转换

- 操作符各个操作数必须处于相同的类型，即，`usual arithmetic conversion` 将会被执行以保证所有操作数在计算时都为同类。

- 操作数总是被转化为可以容纳更多数据的类型（如果表达式中存在），然后计算。

```c
long double;
double;
float;
unsigned long float;
unisnged int;
int
```

### 优先级

- 如果表达式中包含一个以上的操作符号，优先级和结合性将用于确定其和表达式中其余操作符的关系以及运算顺序。
  - 两个**相邻**操作符的执行顺序由他们的优先级决定。
    - 如果优先级相同，由结合性决定。
  - 此外，编译器可以使用任何顺序对表达式进行求值，只要其不违反 `,` `&&`, `||` 和 `?:` 操作符号的限制。
- 换言之，操作符的优先级只决定表达式组成部分在求值过程中如何聚组。

```c
a + b * c; // b * c将会先运算，然后是 a + 结果
a * b + c * d + e * f; // 因为只有相邻操作符的运算顺序有关系，
/*
a * b, c * d, e * f
加一起

a * b 
c * d
res1 * res2
e * f
res3 + res4
先把一部分算出来，然后是剩下一部分，也是可以的
*/
```

- 因为优先级规则只能决定两个相邻运算符的运算顺序，子表达式的运行顺序是 `undefined`。如下：

```c
c + --c; // --c 在 c 之前或之后执行，是未定义的。
```

```c
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[]) {
    int i = 10;
    i = i-- - --i * (i = -3) * i++ + ++i;
    printf("%d\n", i);
    return 0;
}
```

- | 编译器  | 结果 |
  | ------- | ---- |
  | `gcc`   | 36   |
  | `clang` | -63  |

- 因为这种原因，如果一个函数不是 `stateless`，即其调用顺序对结果产生区别，建议使用一个临时变量 `tmp` 来把每一步执行步骤都写明白。

## 问题

1. 类型：`float`，值：2.0

2. 不确定：假设编译器首先计算 left operand, 则 `2 - 3 * 4 = -10`；反之，可为 `4 - 2 * 3 = -2`

3. 例如，`%2 == 0` 可以使用 `& 1` 替代；`^` 可以进行那个简单的加密；移位操作符可以实现高效的 /2 和 *2 运算（至少，在没有溢出的前提下）-> 以及可以由 $\sum_{i}^{n}2^i$ 来近似的表达式的值。

4. **条件操作符更快，因为编译器可以生成更紧凑的目标代码。** **错误！** 没有区别，因为是事实上只有一条语句会被执行。

5. `leap_year = year & (1 << 2) && ( year % 100 != 0 || year % 400 == 0 )`

6. `--,++,+=,-=` 等等

7. 下面这个代码段总是打印 `In range`, 因为无论是否 `1 <= a`，返回结果必然在 `0, 1` 之间，必然小于 `<= 10`

8. ```c
   while(c = f3(a = f1(x), b = f2(x + a)) > 0){
       stmts;
       x++;
   }
   // or
   for(a = f1(x); b = f2(x + a), c = f3(a + b), c > 0; a = f1( ++ x)){
       stmts;
   }
   ```
   
9. 不行：假设一个 `array` 为 `[-1, 1]` 则该方法将会失败。。

10. 1. -25
    2. -25, b = -24
    3. 9, a = 9
    4. 1
    5. 4
    6. -5
    7. 40
    8. -4 // (whether signed or not signed shift is platform specific)
    9. 1
    10. 0
    11. binary string for `a = 1010`, binary string for `b=11111111111111111111111111100111`. Then `a & b = 0b10 = 2`
    12. `a ^ b = 111111...1101 = -19`
    13. `a | b = -17`
    14. 24
    15. 0
    16. 1
    17. 10
    18. 12, a 为 12
    19. 4, b 为 4
    20. 4, b 为 4
    21. 4, a 为 4
    22. 1, d 为 1
    23. 3, a, b, c 为 3
    24. -27
    25. -65
    26. -1 // (not portable)
    27. 1
    28. 1
    29. 1
    30. 0
    31. a - 10 > b + 10 -> 1

## 编程

1. ```c
   #include <stdio.h>
   #include <stdlib.h>
   // Covert upper case letter to lower case letter
   int main(int argc, char const *argv[]) {
       printf("Input some characters:\n");
       char c;
       while ((c = getchar()) != '\n') {
           putchar((c >= 'A' && c <= 'Z') ? c + 'a' - 'A' : c); // 对于一些大些字母不连续的字符集，如 EBCDIC ，就会失败
       }
       putchar('\n');
       return EXIT_SUCCESS;
   }
   ```

   ```c
   #include <stdio.h>
   #include <ctype.h>
   // 值得提倡的解决方案
   int main(int argc, char const *argv[])
   {
       int ch;
       while ((ch = getchar()) != EOF) putchar(tolower(ch));
       return 0;
   }
   
   ```
   
2. ```c
   #include <stdio.h>
   #define OFFSET 13
   
   // Simple ceaser cipher
   int main(int argc, char const *argv[]) {
       char ch;
       while ((ch = getchar()) != '\n') {
           // if it is an upper or lower case letter, shift 13
           putchar((ch >= 'A' && ch <= 'Z')   ? (ch + OFFSET - 'A') % 26 + 'A'
                   : (ch >= 'a' && ch <= 'z') ? (ch + OFFSET - 'a') % 26 + 'a'
                                              : ch);
       }
       return 0;
   }
   ```

3. ```c
   #include <stdio.h>
   
   // Reverse all bits of a integer
   #include <stdio.h>
   
   unsigned int reverse_bits(unsigned int value) {
       unsigned int result = 0;
       for (int i = 0; i < sizeof(int) * 8; i++) {
           if (value & (1 << i)) result |= (1 << (sizeof(int) * 8 - i - 1));
       }
       return result;
   }
   
   unsigned int reverse_bits2(unsigned int value) {
       unsigned int result = 0;
       // 为了避免可移植性问题，应当使用 i != 0 的方式，和 unsigned int i. 这样，循环
       // 就和机器的字长无关了。
       for (int i = 0; i < sizeof(int) * 8; i++) {
           result <<= 1; // Act like as result is a deque.
           result |= (value & 1); // Add the last bit of value to the front of result.
           value >>= 1; // Remove the last bit of value.
       }
       return result;
   }
   
   int main(int argc, char const *argv[]) {
       printf("%u\n", reverse_bits2(25U));
       return 0;
   }
   ```
   
4. ```c
   #include <stdio.h>
   #include <stdlib.h>
   
   // Bit Array
   void set_bit(char bit_array[], unsigned bit_number) {
       bit_array[bit_number / 8] |= (1 << (bit_number % 8));
   }
   
   void clear_bit(char bit_array[], unsigned bit_number) {
       bit_array[bit_number / 8] &= ~(1 << (bit_number % 8));
   }
   
   void assign_bit(char bit_array[], unsigned bit_number, char value) {
       if (value)
           set_bit(bit_array, bit_number);
       else
           clear_bit(bit_array, bit_number);
   }
   
   void test_bit(char bit_array[], unsigned bit_number) {
       return bit_array[bit_number / 8] & (1 << (bit_number % 8));
   }
   ```

5. ```c
   #include <stdio.h>
   #include <stdlib.h>
   
   int store_bit_field(int ori, int val, unsigned start, unsigned end) {
       int mask = ~0 << (end) & ~(~0 << start + 1);
       return ori & ~mask | (val << end) & mask;
   }
   
   
   int main(int argc, char const *argv[]) {
       printf("%x\n", store_bit_field(0x0, 0x1, 4U, 4U)); // 10
       printf("%x\n", store_bit_field(0xffff, 0x123, 15U, 4U)); // 123f
       printf("%x\n", store_bit_field(0xffff, 0x123, 13U, 9U)); // c7ff
   }
   ```