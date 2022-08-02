#include <stdio.h>
#include <string.h>

void print_tokens(char *line)
{
    static char whitespace[] = " \f\n\r\t\v";
    char *token;

    // Strtok 保存传入的 line 地址，以便下次调用时使用
    // 从第二次调用开始，直接传入 NULL 指针即可
    // Strtok 会修改传入的字符串，因此需要使用拷贝如果传入字符串指针不可被修改
    // strtok 保存处理函数的局部状态信息，因此不可以同时用来处理两个字符串。但是可以使用不同的分隔符集合
    for (token = strtok(line, whitespace); token != NULL; token = strtok(NULL, whitespace))
        printf("%s\n", token);
}

int main(int argc, char const *argv[])
{
    char line[100];
    printf("Enter a line: ");
    fgets(line, 100, stdin);
    print_tokens(line);
    return 0;
}
