# 函数

## 函数定义

- 函数定义就是函数体的实现
- 函数声明出现在函数被调用的地方，用于向编译器提供和这个函数相关的信息

```c
返回类型 函数名（形参）{
    代码块
}
```

- 空函数

    ```c
    fun() {
    
    }
    ```
    
    - 这样的函数用来作为一种存根（`stub`）的目的，即，为一些此时尚未实现的代码保留一个位置。
    - 可以保持程序在结构上的完整性，便于编译和测试程序的其他部分。和 Python 中的 `pass` 差不太多

- 函数声明的风格

  - 旧式风格

    ```c
    int *find_int(k, arr, len)
        int k;
    	int arr[];
    	int len;
    {
        
    }
    ```

  - 我们应当坚持使用现代风格，因为旧式风格是冗余的，并且也不便于函数原型的使用（即，降低了编译器再函数调用时检查错误的能力）

    ```c
    #include <stdio.h>
    
    int *find_int(int k, int arr[], int len){
        int i;
        for(i = 0;i < len; i++)
            if(arr[i] == k)
                return &arr[i];
        return NULL;
    }
    ```

- `return`

  - 当执行流到达函数的末尾时，函数就会返回。
  - 使用 `return` 在函数提任何位置返回
    - 没有返回值的函数被称为 `procedure`, 声明为 `void`。再表达式中调用一个 `procedure` 是不合法的，会让垃圾（不可预测的值）参与运算。
    - 真正的函数返回一个值，但是你大可丢弃这个值。

## 函数声明

### 原型

- 向编译器提供关于函数的新消息。此后，编译器就会之后所有对该函数的调用并对其参数类型及返回值进行检查。

  - 旧式风格定义的函数只有返回值被检查。

- 函数原型，**function prototype** 是极好办法——你只需声明函数头，而无需实现。

  - 应当把函数原型至于单独的源文件中，使用 `#include` 引用。
  - 函数原型可以不包含参数名字（只有类型是必须的），但是插入描述性的参数名可以给调用者提供有用的信息。如 `char *strcpy(char *dst, char *src)` 较 `char *strcpy(char*, char*)` 更有意义。

- 函数原型可以声明在代码块中，但是编译器将会在退出代码块时遗忘这些原型。为此，对于多个代码块中的原型声明，原型是否一致没有保证。

- 单次声明避免问题

  - `#include "func.h"`

- 一个没有任何参数的函数

  ```c
  // 旧式风格
  int *func()
  ```

  ```c
  // 新式风格
  int *func(void)
  ```

### 默认

- 如果没有调用函数的原型，编译器假设参数的类型和数量是正确的以及函数的返回值为整型。

- 这意味着，函数的返回值，将会被强行解释成浮点变量（而这往往是错误的），然后在进行强制类型转化为调用者所期望的值。

  - ```c
    #include <stdio.h>
    
    int main(int argc, char const *argv[])
    {
        float f;
        f = xyz();
        printf("%f\n", f);
        return 0;
    }
    
    
    float xyz()
    {
        return 3.14;
    }
    ```

    对于这样存在问题的源文件，`gcc 8.3.0` 拒绝编译。在 `float xyz` 处 `error`

    但是，对于一些旧式的编译器，浮点数将被以整型的方式解释，然后再强制转化为整型。

## 传值的方式

- `C` 函数的所有参数都已传值调用的方式进行传递
  - 传递给函数的标量参数是传值调用的
  - 传递给函数的数组参数也是传值调用的（指针的拷贝）。因此，修改传入指针指向的数组意味着修改调用程序的数组元素——就好像通过传址调用的那样
- 特别的，旧式语法写出来的东西总是被 `default argument promotion`,
  - `float` 变成 `double`
  - `char` 和 `short` 变成 `int`
  - 为了兼容性，ANSI 将会对旧式风格声明的函数进行这种转换。使用原型的函数不执行这样的转换，所以**混用这两个风格会造成错误！**（但是编译过）

### ADT 和封装

- C 可以用来设计和实现抽象数据类型（**ADT**，**abstract data type**），用来限制函数和数据定义的作用域。
  - 这被称为黑盒设计（**black box**）
  - 换言之，依赖抽象而不是具体实现——依赖接口（头文件），说明模块执行的任务；而不依赖模块内部的数据类型和具体实现。
    - `static` 关键字用来限制对非接口的函数以及数据的访问。

- 例子

  ```c
  #define NAME_LENGTH 30
  #define ADDR_LENGTH 100
  #define PHONE_LENGTH 11
  
  #define MAX_ADDRSSES 10000
  
  char const *lookup_address(char const *name);
  
  char const *lookup_phone(char const *name);
  ```

  ```c
  // 接口
  #include "addr_list.h"
  #include <stdio.h>
  
  // 结构体更好，不过我们没有 struct
  static char name[MAX_ADDRSSES][NAME_LENGTH];
  static char address[MAX_ADDRSSES][ADDR_LENGTH];
  static char phone[MAX_ADDRSSES][PHONE_LENGTH];
  
  static int find_entry(char const *name_to_find)
  {
      int entry;
      for (entry = 0; entry < MAX_ADDRSSES; entry += 1)
          if (strcmp(name_to_find, name[entry]) == 0)
              return entry;
      return -1;
  }
  
  
  char const *lookup_address(char const *name_to_find)
  {
      int entry = find_entry(name_to_find);
      if (entry == -1)
          return NULL;
      return address[entry];
  }
  
  char const *lookup_phone(char const *name_to_find)
  {
      int entry = find_entry(name_to_find);
      if (entry == -1)
          return NULL;
      return phone[entry];
  }
  
  ```

- 通过将实现细节和具体实现分隔开，我们可以修改内部实现而无需担心造成损坏外部调用者。

## 递归

- C 通过运行时堆栈支持递归函数的实现——最为臭名昭著的应属阶乘和斐波那契数列。在这两个例子中，他们都效率极低或不提供任何优越之处。

- 将二进制字符转化为无符号十进制字符表示形式。

  - ```c
    #include <stdio.h>
    
    void binary_to_ascii(unsigned val)
    {
        unsigned quotient = val / 10;
        if (quotient != 0)
            binary_to_ascii(quotient); // 打印 quotient 当前值的各位数字（quotient 现在更接近 0）
        putchar(val % 10 + '0');
    }
    ```

  - 阅读递归函数最重要是信任——信任数学归纳法，信任递归函数会完成任务。只要每个步骤都是正确的，所有的限制条件都是正确的，并且每一步都使得结果更接近限制条件，**递归函数总是正确完成任务。**

- 递归不是干活的最好方法

  - 递归函数调用涉及运行时开销——参数必须被压到堆栈中、局部变量需要被分配内存空间、寄存器的值需要被保存、等等。每次递归函数返回，上面的操作就需要还原并恢复到原有的样子。
  - 如果我们使用循环来计算，就不会出现这样的问题，事情就会好办一些。

- 阶乘

  - ```c
    long factorial(int n){
        return (n <= 1) ? 1 : factorial(n - 1) * n; 
    }
    
    long iter_factorial(int n)
    {
        long result = 1;
        for (int i = 1; i <= n; i++)
            result *= i;
        return result;
    }
    ```

  - 迭代实现的速度比递归实现的效率更高，哪怕其可读性会更差。**仅当一个问题相当复杂，难以使用迭代方式实现时，递归实现的简洁性才可能补偿其运行时开销。**

- 斐波那契数列

  - 如果我们真的使用 `fib(n) = (n <= 2)? 1 : fib(n - 1) + fib(n - 2)` 来解决问题，大量的额外时间将会被耗费在递归的计算上，产生大量的额外开销。
  - 利用循环计算速度真的会快很多很多倍！

## 可变参数

- 我们希望函数可以获得数量未定的参数。通过 `starg.h` 宏
  - `va_start`
  - `va_arg`
  - `va_end`
- 我们可以声明一个 `va_list` 的变量，并使用这几个宏来访问参数的值
- 限制
  - 你不能直接访问中间的参数
  - 可变参数部分没有原型，所以所有可变参数都会执行缺省参数类型提升（`default argument promotion`）！
  - 因为类型不是值的属性，**宏不能判断参数的数量或其类型**。必须通过命名参数才有可能解决这些问题——例如， `printf` 中的字符串，指定了参数数量和类型。如果`va_arg` 中制定了错误的类型，那么结果时不可预测的——缺省参数类型提升可谓是罪恶之源。
- 例子

```c
#include <stdarg.h>
#include <stdio.h>

float average(int N, ...)
{
    va_list var_args;
    int count;
    float sum = 0;

    va_start(var_args, N);
    for (count = 0; count < N; count++)
    {
        sum += va_arg(var_args, int);
    }
    va_end(var_args);
    return sum / N;
}

int main(int argc, char const *argv[])
{
    printf("%f\n", average(3, 1, 2, 3));
    return 0;
}
```

## 问题

1. 加上形参列表以及形参名。置入头文件中。**打印传入的参数显示存根函数被调用。**

2. 缺点，因为这意味着默认整型以及编译器不对函数调用进行检查的灾难（真的，为什么就没有 `multiple pass` 的编译器呢？）

3. 编译器可以将其通过一般的隐式转化将 A 转化为 B（as if assigned B to the type of A)。

4. 在`void` 中返回将会**编译报错**

5. 编译器将会将值作为整型解释，然后就会产生不可预计的后果。

6. 如果编译器不知道原型，并在函数调用时传入错误类型的值。则，该值将会被当作形参的值错误的运算。

7. **可以将数组长度作为类型声明的一部分**，只不过，对于大数组没被毛病，但是数组长度不够就会访问数组边界外的值。编译器不会检查。

8. 递归可以被认为是留存变量和局部变量状态的 `while` 循环。他们都有终止条件，更新条件等等——但是，递归写起来更优雅，运行起来更慢。

9. `#include` 只需书写一次，避免了很多问题：在函数中定义函数原型，错误的书写形参列表。并且，这也方便别的程序使用这些函数。

10. ```c
    Calculate 0:1 use 1 calculations.
    Calculate 1:1 use 1 calculations.
    Calculate 2:1 use 1 calculations.
    Calculate 3:2 use 3 calculations.
    Calculate 4:3 use 5 calculations.
    Calculate 5:5 use 9 calculations.
    Calculate 6:8 use 15 calculations.
    Calculate 7:13 use 25 calculations.
    Calculate 8:21 use 41 calculations.
    Calculate 9:34 use 67 calculations.
    Calculate 10:55 use 109 calculations.
    Calculate 11:89 use 177 calculations.
    Calculate 12:144 use 287 calculations.
    Calculate 13:233 use 465 calculations.
    Calculate 14:377 use 753 calculations.
    Calculate 15:610 use 1219 calculations.
    Calculate 16:987 use 1973 calculations.
    Calculate 17:1597 use 3193 calculations.
    Calculate 18:2584 use 5167 calculations.
    Calculate 19:4181 use 8361 calculations.
    Calculate 20:6765 use 13529 calculations.
    Calculate 21:10946 use 21891 calculations.
    Calculate 22:17711 use 35421 calculations.
    Calculate 23:28657 use 57313 calculations.
    Calculate 24:46368 use 92735 calculations.
    Calculate 25:75025 use 150049 calculations.
    Calculate 26:121393 use 242785 calculations.
    Calculate 27:196418 use 392835 calculations.
    Calculate 28:317811 use 635621 calculations.
    Calculate 29:514229 use 1028457 calculations.
    Calculate 30:832040 use 1664079 calculations.
    Calculate 31:1346269 use 2692537 calculations.
    Calculate 32:2178309 use 4356617 calculations.
    Calculate 33:3524578 use 7049155 calculations.
    Calculate 34:5702887 use 11405773 calculations.
    Calculate 35:9227465 use 18454929 calculations.
    Calculate 36:14930352 use 29860703 calculations.
    Calculate 37:24157817 use 48315633 calculations.
    Calculate 38:39088169 use 78176337 calculations.
    Calculate 39:63245986 use 126491971 calculations.
    Calculate 40:102334155 use 204668309 calculations.
    Calculate 41:165580141 use 331160281 calculations.
    Calculate 42:267914296 use 535828591 calculations.
    Calculate 43:433494437 use 866988873 calculations.
    Calculate 44:701408733 use 1402817465 calculations.
    Calculate 45:1134903170 use -2025160957 calculations.
    Calculate 46:1836311903 use -622343491 calculations.
    Calculate 47:-1323752223 use 1647462849 calculations.
    Calculate 48:512559680 use 1025119359 calculations.
    ```

## 编程

1. ```c
   #include <stdio.h>
   
   int hermite(int n, int x)
   {
       if (n <= 0)
           return 1;
       if (n == 1)
           return 2 * x;
       return 2 * x * hermite(n - 1, x) - 2 * (n - 1) * hermite(n - 2, x);
   }
   
   
   int main(int argc, char const *argv[])
   {
       printf("%d\n", hermite(3, 2));
       return 0;
   }
   ```

2. ```c
   #include <stdio.h>
   
   int gcd(int M, int N)
   {
       int mod = M % N;
       if (mod == 0)
           return N;
       else if (mod > 0)
           return gcd(N, M % N);
   }
   
   int main(int argc, char const *argv[])
   {
       printf("%d\n", gcd(12, 18));
       printf("%d\n", gcd(18, 12));
       return 0;
   }
   ```

   - 循环实现，尾递归

   ```c
   int gcd(int m, int n)
   {
       if (m <= 0 || n <= 0)
           return;
       int r;
       do
       {
           r = m % n;
           m = n;
           n = r;
       } while (r > 0);
       return m;
   }
   ```

3. ```c
   
   #include <stdio.h>
   
   /**
    * @brief This function converts an ASCII string to an integer.
    *
    * @param str The string to convert.
    * @return int Converted result.
    */
   int ascii_to_integer(char *str)
   {
       int result = 0;
       char ch;
       while ((ch = *str++) != '\0')
       {
           if ('0' <= ch && ch <= '9')
               result = result * 10 + (ch - '0');
           else
               return 0;
       }
       return result;
   }
   
   int main(int argc, char const *argv[])
   {
       char str[] = "12345";
       printf("%d\n", ascii_to_integer(str));
       return 0;
   }
   ```

4. ```c
   #include <stdarg.h>
   #include <stdio.h>
   
   /**
    * @brief Find the max element in an arbitrary list. Require at least one
    * item in the list.
    * 
    * @param first first item in the list.
    * @param ... subsquent items in the list. The last item must be negative.
    * @return int max item.
    */
   int max_list(int first, ...)
   {
       va_list args;
       va_start(args, 0);
       int max = first;
       while (1)
       {
           int tmp = va_arg(args, first)
           if (tmp <= 0)
               break;
           if (tmp > max)
               max = tmp;
       }
       va_end(args);
       return max;
   }
   
   int main(int argc, char const *argv[])
   {
       printf("%d\n", max_list(1, 2, 3, 4, 10, -1));
       return 0;
   }
   
   ```

   - 优雅实现，并且处理第一个元素就是负值

   ```c
   int max_list(int first, ...)
   {
       va_list args;
       int max = 0;
       if (first >= 0)
       {
           int this;
           max = first;
           va_start(args, first); // va_start use va_list and last named argument
           while ((this = va_arg(args, int)) >= 0)
               if (this > max)
                   max = this;
           va_end(args);
       }
       return max;
   }
   ```

   

5. ```c
   #include <stdbool.h>
   #include <stdarg.h>
   #include <stdio.h>
   #define NUL '\0'
   
   void print_integer(int i)
   {
       int q = i / 10;
       if (q != 0)
           print_integer(q);
       putchar(i % 10 + '0');
   }
   
   void print_double(double f)
   {
       int i = (int)f;
       print_integer(i);
       putchar('.');
       f -= i;
       while (f != 0)
       {
           f *= 10;
           putchar('0' + (int)f);
           f -= (int)f;
       }
   }
   
   void print_string(char *s)
   {
       while (*s != NUL)
           putchar(*s++);
   }
   
   void my_printf(const char *format, ...)
   {
       char ch;
       enum State
       {
           STATE_PERCENT,
           STATE_NORMAL
       };
       enum State state = STATE_NORMAL;
       va_list args;
       va_start(args, 0);
       int idx = 0;
       while ((ch = *format++) != NUL)
       {
           if (ch == '%')
           {
               state = STATE_PERCENT;
               continue;
           }
           if (state == STATE_PERCENT)
           {
               switch (ch)
               {
               case 'd':
                   print_integer(va_arg(args, int));
                   break;
               case 'f':
                   print_double(va_arg(args, double)); // default argument promotion
                   break;
               case 's':
                   print_string((char *)va_arg(args, int));
                   break;
               case 'c':
                   putchar(va_arg(args, int));
                   break;
               default:
                   printf("\nInvalid format specifier: %%%c\n", ch);
                   return;
               }
               state = STATE_NORMAL;
           }
           else
           {
               putchar(ch);
           }
       }
       va_end(args);
   }
   
   int main(int argc, char const *argv[])
   {
       my_printf("Test%c %d %s %f\n", 'c', 1, "World", 1.02);
       return 0;
   }
   ```

6. 本人原本使用了各种各样的 `switch` 语句。显然，使用一个数组作为哈希表远远更有效率

   ```c
   #include <stdio.h>
   #include <string.h>
   
   static char *digits[] = {
       "",
       "one",
       "two",
       "three",
       "four",
       "five",
       "six",
       "seven",
       "eight",
       "nine",
       "ten",
       "eleven",
       "twelve",
       "thirteen",
       "fourteen",
       "fifteen",
       "sixteen",
       "seventeen",
       "eighteen",
       "nineteen",
   };
   
   static char *tens[] = {
       "",
       "",
       "twenty",
       "thirty",
       "forty",
       "fifty",
       "sixty",
       "seventy",
       "eighty",
       "ninety",
   };
   
   static char *magnitude[] = {
       "",
       "thousand",
       "million",
       "billion",
       "trillion",
       "quadrillion",
       "quintillion",
   };
   
   static void add_space(char *buf)
   {
       strcat(buf, " ");
   }
   
   static void do_one_group(unsigned amount, char *buf, char **magnitude)
   {
       int val;
   
       val = amount / 1000;
       if (val > 0)
           do_one_group(val, buf, magnitude + 1);
       amount %= 1000;
       val = amount / 100;
       if (val > 0){
           strcat(buf, digits[val]);
           add_space(buf);
           strcat(buf, "hundred");
           add_space(buf);
       }
   
       val = amount % 100;
       if (val > 0)
       {
           if (val > 20)
           {
               strcat(buf, tens[val / 10]);
               add_space(buf);
           }
           strcat(buf, digits[val % 10]);
           add_space(buf);
           strcat(buf, *magnitude);
           add_space(buf);
       }
   }
   
   void written_amount(unsigned amount, char *buf){
       if(amount == 0)
           strcat(buf, "zero");
       else{
           *buf = '\0';
           do_one_group(amount, buf, magnitude);
       }
   }
   
   int main(int argc, char const *argv[])
   {
       char buf[1000];
       written_amount(123456789, buf);
       printf("%s\n", buf);
       return 0;
   }
   ```

   

