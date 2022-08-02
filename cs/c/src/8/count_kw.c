#include <string.h>
#include <stdio.h>

int lookup_kw(char const *const desired_word, char const *kws[], int const size)
{
    char const **kwp;
    for (kwp = kws; kwp < kws + size; ++kwp)
        if (strcmp(*kwp, desired_word) == 0)
            return kwp - kws;
    return -1;
}

int main(int argc, char const *argv[])
{
    return 0;
}
