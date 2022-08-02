# 字符串，字符，和字节

- C 语言没有字符串数据类型，因为字符串在  C 中以字符串常量的形式出现或者存储在字符数组中。

- 字符串常量适用于不会程序不会对其修改的字符串，所有其他字符串必须存在于字符数组或动态分配的内存中。
- 本章，我们将会学习字符串的常用函数

### 字符串基础

- 字符串是0或多个字符加上一个`0/NUL` 字节结尾的数组。
  - 字符串不能包含 `NUL` 字节
  - `NUL` 字节不可打印， 所以上述规定不出现问题
  - `string.h` 提供了字符串函数的原型和声明。因为字符串函数的返回值不是整形就是被忽略，可以不包含这个文件，但是这样编译器就不能进行错误检查。

### 长度

```c
#include <stddef.h>
#include <string.h>

size_t my_len(char const *str)
{
    size_t len = 0;
    while (*str++ != '\0')
        len++;
    return len;
}


int main(int argc, char const *argv[])
{
    char *str = "Hello World";
    printf("%zu\n", my_len(str));
    printf("%zu\n", strlen(str));
    return 0;
}
```

- 上述两个函数都返回字符串长度。`size_t` 是一个无符号整数类型，在 `stddef.h` 中定义。
- 因为这个是无符号数，所以可能会产生一些奇怪的错误
  - `strlen(x) >= strlen(y)` 是正确的
  - `strlen(x) - strlen(y) >= 0` 永远是真，因为无符号数永远不为负值

## 不受限制

### 复制字符串

- `char *strcpy(char *dst, char const *src)`
  - 将 `src` 复制到 `dst`。如果 `src` 和 `dst` 在内存中重叠，结果为未定义。
  - `dst` 必须为动态分配内存的数组指针或者字符数组。字符串常量是不行的。
- 请保证 `dst` 的长度是足够的。否则，将复制原先存储在数组后面内存空间的值——`strcpy` 不能判断目标数组的长度。

### 连接字符串

- `char *strcat(char *dst, char const *src)`
  - 将字符串的拷贝添加到 `dst` 的末尾。
  - 如果位置发生重叠，其结果是为定义的。。
- 保证 `sizeof dst >= (strlen(src) + strlen(dst)) - 1`

```c
#include <stdio.h>
#include <string.h>

int main(int argc, char const *argv[])
{
    char buf[100];
    printf("%zu\n", sizeof(buf) / sizeof(char));
    strcat(buf, "Hello World");
    printf("%s\n", buf);
    strcat(buf, " Hello C");
    printf("%s\n", buf);
    char dst[100];
    strcpy(dst, buf);
    printf("%s\n", dst);
    return 0;
}
```

### 返回

- 他们返回第一个参数的指针的拷贝。因此，我们可以嵌套调用这些函数，如

```c
strcat(strcpy(dst, a), b)
```

- 但是这样的调用和下面的方法相比，功能上没有优势

```c
strcpy(dst, a);
strcat(dst, b);
```

- 上面这些函数的大多数调用，返回值只是被简单的省略。

### 比较

- 根据字符的 `codepoint` 进行比较。如果每个码点都是相等的，那么使用字符串的长度进行比较——成为词典比较。对于只包含大写的小写字母的字符串比较，这种方式比较的结果和日常使用的字母顺序相同。
- `int strcmp(char const *s1, char const *s2)`
  - 如果 `s1 < s2`，返回值小于0（不一定是 -1）
  - 反之，返回大于 0 的值（不一定是 1）
  - 如果相等，返回 0（需要注意的是，这个计算为 `false`）
- 因为不修改任何参数，所以不存在溢出的危险。但是，参数必须以 `NUL/'\0'` 结尾。若非如此，就可能对参数后面的字节进行比较，而其结果是无意义的。

## 长度受限的字符串函数

- `char *strncpy(char *dst, char const *src, size_t len)`

  - 总是写入 `len` 个字符；如果 `strlen(src) < len`，那么将会额外的填充 `NUL` 字节直至到达 `len` 长度。

  - 如果 `strlen(src) > len`，那么只有 `len` 个字符被复制，并且**不会以 NUL 字节结尾**。

  - 因为这个原因，`strncpy` 不总是返回字符串；在这样的错误的字符串上调用 `strlen`，`printf`往因此，我们往出现严重的问题——`NUL` 字节永远不会找到，直到程序崩溃

    - 因此，使用这这些字符串函数时，应当手动保证字符串已 `NUL` 结尾

    - ```c
      char buf[SIZE];
      strncpy(buf, name, SIZE);
      buf[SIZE - 1] = '\0';
      ```

- `char *strncat(char *dst, char const *src, size_t len)`

  - `strncat` 总是在结尾添加一个 `NUL` 字节，也不会进行 `NUL` 字节填充。最多从 `src` 复制  `len` 个字符。

- `int strncmp(char const *s1, char const *s2, size_t len)`

  - 最多比较 `len` 个字符

- 上述这些函数提供了一个长度参数，用于限定复制或比较的字符数，防止难以预料的长字符串从其目标数组一处。

### 字符串查找

#### 字符

- `char *strchr(char const *str, int ch)`
- `char *strrchr(char const *str, int ch)`
- 第一个函数寻找 `ch` 第一个出现的位置，并返回一个指向该位置的指针或 `NULL` 如果不存在。`strrchr` 寻找最后一次出现的位置，并同样返回 `NULL` 或指向该位置的指针。

### 几个字符

- `char *strpbrk(char const *str, char const *group)`
- 寻找 `str` 中第一个匹配 `group` 中任何一个字符串的字符位置

### 子串

- `char *strstr(char const *s1, char const *s2)`
- 在 `s1` 中寻找 `s2` 第一次出现的位置，并返回一个指向该位置的指针
  - `s2` 为空，返回 `s1`
  - `s2` 没有出现在 `s1` 的任何地方，返回 `NULL` 指针

### 右边找

- 标准库中并没有提供 `strrpbrk` 或者 `strrstr` 这样的函数。不过，可以使用递归的方式来实现这个函数。

### 查找前缀

- `size_t strspn(char const *str, char const *group)`
  - 返回字符串开头所有字符都是 `group` 字符的一部分的长度
- `size_t strcspn(char const *str, char con *group)`
  - 返回字符串开头所有字符都是 `group` 字符的补集（complement） 的一部分的长度

### 查找标记

- `char *strtok(char *str, char const *sep)`
  - `sep` 定义分隔符的字符集合
  - 第一个参数指定字符串，包含另个或多个由 `sep` 字符串中一个或多个分割符分割的标记。`strtok` 找到 `str` 的下一个标记，并将其用 `NUL` 结尾，然后返回一个指向这个标记的指针。

### 错误信息

- 调用函数时，如果出现错误是通过设置一个外部的整形变量 `errno` 进行错误代码报告的。
- `char *strerror(int errno)`接受一个错误代码，然后返回一个描述错误的字符串（的首指针）

### 字符操作

#### 字符分类

- `ctype.h`

| 函数       | 作用                            |
| ---------- | ------------------------------- |
| `iscntrl`  | 控制字符                        |
| `isspace`  | 空白：`[ \f\n\r\t\v]`           |
| `isdigit`  |                                 |
| `idxdigit` | 16 进制数字，`[0-9a-fA-F]`      |
| `islower`  | 小写字母                        |
| `isupper`  | 大写字母                        |
| `isalpha`  | `islower || isupper`            |
| `isalnum`  | `[a-zA-Z0-9]`                   |
| `ispunct`  | 标点符号，`!isalnum && isprint` |
| `isgraph`  | 图形字符                        |
| `isprint`  | 可打印字符，包含图形和空白字符  |

#### 字符转化

- `int tolower(int ch)`
- `int toupper(int ch)`
- 如果本身就是大写或者小写，不做转化。直接比较 `ch >= 'A' && ch <= 'Z'` 降低程序的可移植性，所以还是不做为好。

## 内存操作

- 如果字符串内包含 `NUL` 字符，可以使用内存操作函数来实现。这些函数在遇到 `NUL` 字节时不会停止操作
- `void *memcpy(void *dst, void const *src, size_t length)`
  - 从 `src` 的起始位置复制 `length` 个字节到 `dst`
  - 如果 `src` 和 `dst` 重叠，结果是未定义的
  - 任何指针都可以转化为`void*` 指针

- `void *memmove(void *dst, void const *src, size_t length)`
  - 源和目标可以重叠
    - 将源复制到临时位置，这个位置不会和 `src` 或者 `dst` 重叠
    - 然后把临时位置复制到目标位置。无法使用机器提供的特殊字节/字符串处理指令，因此会缓慢一些。但是，如果 `src` 或者 `dst` 在任何形式上可能重叠，就应当使用 `memmove`
- `void *memcmp(void const *a, void const *b, size_t length)`
  - 对内存内容进行比较，比较 `length` 个字节
  - 将每个字节当作无符号字符
- `void *memchr(void cosnt *a, int ch, size_t length)`
  - 查找 `ch` 第一次出现的位置，并返回该位置的指针。查找 `length` 个字节。如果未找到，返回 `NULL`
- `void *memset(void *a, int ch, size_t length)`
  - 把 `a` 开始的 `length` 个字节设置为 `ch`

## 问题

1. 这是一个优点，因为我们可以像处理指针或者普通的数组一样来处理字符串。**虽然这个方法操作字符数组很有效率，很灵活**，但是这也产生错误、溢出、下标越界、无法改变数组长度等等。现代的面向对象技术对字符串类的所有操作进行错误检查，动态内存分配，等等——这样做虽然慢，但是方便程序的开发和运行。

2. 无符号值可以存储有符号值两倍的大小，使用同样数量的空间。但是这也造成了对返回值进行计算和处理的苦难。

3. 这可以让之后的对字符串的工作更快的完成，因为不再需要寻找字符串的结尾。

4. `memcpy(y, x, 50)`

5. 不能；如果 `some_other_string` 的长度大于 `BSIZE - 1`，且 `buffer` 的最后一个字符不是 `\0`，那么上述函数将会失败。（但如果 `buffer` 本身因为默认初始化或者 `static init` 使得最后一个字符为 `NUL` ，那么上述函数将会成功）

6. 可一致性更好。下面的方式假设计算机使用的字符集中，大写和小写字母是连续的。

7. 可以去掉 `islower` 测试，因为 `toupper` 对于传入错误的 `char` 情况，直接返回传入值而而不修改。

   ```c
   register int ch;
   for(pstring = msg; (ch = *pstring) != '\0'; )
       *pstring++ = toupper(ch);
   ```

   

8. 第一个表达式的值为 `ptrdiff_t` （定义在 `stddef.h`）；对于一个未结束的字符串，其结果是未定义的（不再同一个数组中的指针不可以做减法）；第二个函数返回 `size_t`，对于为未结束的字符串返回随机数一样的东西。一般来说，字符串函数找不到 NUL 字节；并且就算这个是我们希望的字节，也应当使用内存操作函数来操作内存。

   ```c
   memchr(buffer, 0, SIZE) - buffer;
   strlen(buffer);
   ```

   

### 编程

1. ```c
   #include <stdio.h>
   #include <ctype.h>
   
   int main(int argc, char const *argv[])
   {
       // cntrl, space, num, lower, upper, punct, not printable
       double count[7] = {0};
       int ch;
       while((ch = getchar()) != EOF) {
           if(isalpha(ch)){
               if(isupper(ch)){
                   count[4]++;
               }else{
                   count[3]++;
               }
           }else if(isdigit(ch)){
               count[1]++;
           }else if(isspace(ch)){
               count[2]++;
           }else if(ispunct(ch)){
               count[5]++;
           }else if(iscntrl(ch)){
               count[0]++;
           }else{
               count[6]++;
           }
       }
       int sum = 0;
       for(int i = 0; i < 7; i++)
           sum += count[i];
       for (int i = 0; i < 7; i++)
           count[i] /= sum;
      	// 应当加入一个 total == 0 的判断，这样程序更加强健。
       printf("Control characters: %.2f\n", count[0]);
       printf("Spaces: %.2f\n", count[2]);
       printf("Digits: %.2f\n", count[1]);
       printf("Uppercase letters: %.2f\n", count[4]);
       printf("Lowercase letters: %.2f\n", count[3]);
       printf("Punctuation characters: %.2f\n", count[5]);
       printf("Other characters: %.2f\n", count[6]);
       return 0;
   }
   ```
   
2. ```c
   #include <stdio.h>
   
   int my_strlen(char *str, int len)
   {
       int l = 0;
       while(*str++ != '\0' && l < len)
           l++;
       return l;
   }
   
   int main(int argc, char const *argv[])
   {
       /* code */
       return 0;
   }
   ```

3. ```c
   char *my_strcpy(char *dst, char *src, int size)
   {
       strncpy(dst, src, size);
       dst[size - 1] = '\0';
       return dst;
   }
   ```

4. ```c
   char *my_strcat(char *dst, char *src, int size)
   {
       strncat(dst, src, size);
       dst[size - 1] = '\0';
       return dst;
   }
   ```

5. ```c
   // concat src to dst, make sure no overflow assuming sizeof(dst) == dst_len
   void my_strncat(char *dst, char *src, int dst_len){
       strncpy(dst, src, dst_len - strlen(dst));
   }
   ```

6. ```c
   char *my_strcpy_end(char *dst, char *src)
   {
      strcpy(dst, src);
      return dst + strlen(dst); 
   }
   ```

   ```c
   // Better solution
   char *my_strcpy_end(register char *dst, register char const *src){
       while((*dst ++ = *src++) != '\0')
           	;
      	return dst - 1;
   }
   ```
   
   
7. ```c
   char *my_strrchr(char const *str, int ch){
       char *pt = str + (strlen(str) - 1);
       while(pt >= str){
           if(*pt == ch)
               return pt;
           pt--;
       }
       return NULL;
   }
   ```

8. 指定出现的次数

   ```c
   char *my_strnchr(char const *str, int ch, int which)
   {
       while (true)
       {
           if (*str == '\0')
               return NULL;
           if (*str == ch)
               which--;
           if (which == 0)
               return str;
       }
   }
   
   ```

9. ```c
   int count_chars(char const *str, char const *chars){
       int count = 0;
       while(*str != '\0')
           if(strchr(chars, *str++) != NULL)
              count++;
       return count;
   }
   ```

10. `pailndrome`

    ```c
    int pailndrom(char *str)
    {
        char *lo = str, *hi = str + strlen(str) - 1;
        while (lo < hi)
        {
            while(!isalpha(*lo) && lo < hi)
                lo++;
            while(!isalpha(*hi) && lo < hi)
                hi--;
            
            if (tolower(*lo++) != tolower(*hi--))
                return false;
        }
        return true;
    }
    ```

11. 