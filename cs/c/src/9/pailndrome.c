#include <string.h>
#include <stdio.h>
#include <stdbool.h>
#include <ctype.h>

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

int main(int argc, char const *argv[])
{
    printf("%d\n", pailndrom("abcd"));  // 0
    printf("%d\n", pailndrom("abcdcba")); // 1
    printf("%d\n", pailndrom("aB cdcbA")); // 
    return 0;
}
