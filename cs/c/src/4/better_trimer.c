#include <stdio.h>
#define NUL '\0'

void deblack(char* str) {
    char* dst;
    char* src;
    int ch;

    src = str;
    dst = str++;

    // Set the src and dst to beginning to str.
    // Move str to second char

    while ((ch = *src++) != NUL) {
        if (is_white(ch)) {
            // At beginning
            // previous char is not white space
            // store it
            if (src == string || !is_white(dst[-1])) *dst++ = ' ';
        } else {
            // Just store it
            *dst++ = ch;
        }
    }
    *dst = NUL;
}

int is_white(int ch) {
    return ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r';
}