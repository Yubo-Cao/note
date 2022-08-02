#include <stdio.h>
#include <ctype.h>

int main(int argc, char const *argv[])
{
    // cntrl, space, num, lower, upper, punct, not printable
    double count[7] = {0};
    int ch;
    while((ch = getchar()) != EOF) {
        if(isalpha(ch)){
            if(isupper(ch)){
                count[4]++;
            }else{
                count[3]++;
            }
        }else if(isdigit(ch)){
            count[1]++;
        }else if(isspace(ch)){
            count[2]++;
        }else if(ispunct(ch)){
            count[5]++;
        }else if(iscntrl(ch)){
            count[0]++;
        }else{
            count[6]++;
        }
    }
    int sum = 0;
    for(int i = 0; i < 7; i++)
        sum += count[i];
    for (int i = 0; i < 7; i++)
        count[i] /= sum;
    printf("Control characters: %.2f\n", count[0]);
    printf("Spaces: %.2f\n", count[2]);
    printf("Digits: %.2f\n", count[1]);
    printf("Uppercase letters: %.2f\n", count[4]);
    printf("Lowercase letters: %.2f\n", count[3]);
    printf("Punctuation characters: %.2f\n", count[5]);
    printf("Other characters: %.2f\n", count[6]);
    return 0;
}
