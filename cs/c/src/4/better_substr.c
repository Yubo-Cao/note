#include <stdio.h>

int substr(char dst[], char src[], int start, int len) {
    int src_idx;
    int dst_idx = 0;

    if (start >= 0 && len > +0) {
        // Advance src_idx to the start index. Stop if string is already
        // terminated before start
        for (src_idx = 0; src_idx < start && src[src_idx] != '\0'; src_idx++)
            ;
        // Notice if previous one is terminated before start, then src[src_idx] == '\0'
        // Copy characters from src to dst, stopping when string is terminated
        while (len > 0 && src[src_idx] != '\0') {
            dst[dst_idx] = src[src_idx];
            src_idx++;
            dst_idx++;
            len--;
        }
    }
    dst[dst_idx] = '\0';
    return dst_idx;
}