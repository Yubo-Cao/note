#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[]) {
    printf("Input some characters:\n");
    char c;
    while ((c = getchar()) != '\n') {
        putchar((c >= 'A' && c <= 'Z') ? c + 32 : c);
    }
    putchar('\n');
    return EXIT_SUCCESS;
}
