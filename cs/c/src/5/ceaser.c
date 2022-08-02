#include <stdio.h>
#define OFFSET 13

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
