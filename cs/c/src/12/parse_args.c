#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define CHR_BUF_SIZE 64

int bounded_strlen(char *str, int sz)
{
    int i = 0;
    while (str[i] != '\0' && i < sz)
        i++;
    return i;
}

char *process_stdin(void)
{
    char chr_buf[CHR_BUF_SIZE];
    char *str = NULL;
    int size = 0;
    while (fgets(chr_buf, CHR_BUF_SIZE, stdin) != NULL)
    {
        size += bounded_strlen(chr_buf, CHR_BUF_SIZE);
        str = realloc(str, size);
        strncat(str, chr_buf, CHR_BUF_SIZE - (size == 0));
    }
    // ensure null-terminated
    str[size - 1] = '\0';
    return str;
}

char *process_file(char const *filename)
{
    printf("Processing file %s\n", filename);
    FILE *fp = fopen(filename, "r");
    if (fp == NULL)
    {
        printf("Failed to open file %s\n", filename);
        return;
    }
    char chr_buf[CHR_BUF_SIZE];
    char *str = NULL;
    int size = 0;
    while (fgets(chr_buf, CHR_BUF_SIZE, fp) != NULL)
    {
        size += bounded_strlen(chr_buf, CHR_BUF_SIZE);
        str = realloc(str, size);
        strncat(str, chr_buf, CHR_BUF_SIZE - (size == 0));
    }
    // ensure null-terminated
    str[size - 1] = '\0';
    return str;
}

// etc. default to 0(false)
bool option_a, option_b;

int main(int argc, char const *argv[])
{
    // argv 是指向字符的指针的指针，也就是所有的参数
    // 跳过第一个参数，并且要求首字符为 -
    char ch;
    while (*++argv != NULL && **argv == '-')
    {
        if (*(*argv + 1) != '-')
            return;

        // 处理短格式， -ab = -a, -b 的亲光
        while ((ch = *++*argv) != '\0')
        {
            switch (ch)
            {
            case 'a':
                option_a = true;
                break;
            case 'b':
                option_b = true;
                break;
            default:
                fprintf(stderr, "Unknown option: %c\n", **argv);
                return EXIT_FAILURE;
            }
        }
    }
    // 上面的处理方式破坏了 argv 内部指针。所以，只可处理一次。
    char *str = NULL;
    if (*argv == NULL)
    {
        str = process_stdin();
        printf("%s\n", str);
    }
    else
    {
        do
        {
            str = process_file(*argv);
            printf("%s\n", str);
        } while (*++argv != NULL);
    }
    return 0;
}
