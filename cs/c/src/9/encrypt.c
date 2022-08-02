#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>

#define key_size 27

const char *alphabets = "abcdefghijklmnopqrstuvwxyz";

bool prepare_key(char *key)
{
    char tmp[key_size];
    char *tpt = tmp;
    char *pt;

    for (pt = key; *pt != '\0'; pt++)
    {
        if (!isalpha(*pt))
            return false;
        *pt = tolower(*pt);
        if (strchr(tmp, *pt) == NULL)
            *tpt++ = *pt;
    }

    for (pt = alphabets; *pt != '\0'; pt++)
        if (strchr(tmp, *pt) == NULL)
            *tpt++ = *pt;

    *tpt = '\0';
    strcpy(key, tmp);
    return true;
}

void encrypt(char *data, char const *key)
{
    char *ch, tmp;
    for (ch = data; *ch != '\0'; ch++)
    {
        if (isalpha(*ch))
        {
            tmp = key[strchr(alphabets, tolower(*ch)) - alphabets];
            if (isupper(*ch))
                *ch = toupper(tmp);
            else
                *ch = tmp;
        }
    }
}

void decrypt(char *data, char const *key)
{
    char *ch, tmp;
    for (ch = data; *ch != '\0'; ch++)
    {
        if (isalpha(*ch))
        {
            tmp = alphabets[strchr(key, tolower(*ch)) - key];
            if (isupper(*ch))
                *ch = toupper(tmp);
            else
                *ch = tmp;
        }
    }
}

int main(int argc, char const *argv[])
{
    char key[key_size] = "TRAILBLAZERS";
    prepare_key(key);
    printf("%s\n", key);
    char data[] = "ATTACK AT DAWN";
    encrypt(data, key);
    printf("%s\n", data);
    decrypt(data, key);
    printf("%s\n", data);
    return 0;
}
