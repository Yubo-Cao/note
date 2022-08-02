#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX_COLS 20
#define MAX_INPUT 1000

int read_column_numbers(int columns[], int max);
void rearrange(char *output, char const *input, int n_columns, int const columns[]);

int main(void)
{
    int n_columns;
    int columns[MAX_COLS];
    char input[MAX_INPUT];
    char output[MAX_INPUT];

    n_columns = read_column_numbers(columns, MAX_COLS);

    while (fgets(input, MAX_INPUT, stdin) != NULL)
    {
        printf("Original input: %s\n", input);
        rearrange(output, input, n_columns, columns);
        printf("Rearranged line: %s\n", output);
    }

    return EXIT_SUCCESS;
}

int read_column_numbers(int columns[], int max)
{
    int num = 0;
    int ch;

    /*
    读取列标号
     */
    while (num < max && scanf("%d", &columns[num]) == 1 && columns[num] >= 0)
    {
        num += 1;
    }
    /*
    保证偶数个标号
     */
    if (num % 2 != 0)
    {
        puts("Last column number is not paired.");
        exit(EXIT_FAILURE);
    }
    /*
    丢弃行中最后一部分内容
     */
    while ((ch = getchar()) != EOF && ch != '\n')
        ;
    return num;
}

void rearrange(char *output, char const *input, int n_columns, int const columns[])
{
    int col;        // columns 数组索引
    int output_col; // 输出列计数器
    int len;        // 输入行的长度

    len = strlen(input);
    output_col = 0;

    for (col = 0; col < n_columns; col += 2)
    {
        int nchars = columns[col + 1] - columns[col] + 1;
        // 输入行结束或输出行数组满,则结束
        if (columns[col] >= len || output_col == MAX_INPUT - 1)
            break;
        // 空间不够，则只复制可以容纳的部分
        if (output_col + nchars > MAX_INPUT - 1)
            nchars = MAX_INPUT - output_col - 1;
        // 复制相关的数据
        strncpy(output + output_col, input + columns[col], nchars);
        output_col += nchars;
    }
    output[output_col] = '\0';
}