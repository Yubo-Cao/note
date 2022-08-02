#include <stdio.h>
#include <string.h>

static char *digits[] = {
    "",
    "one",
    "two",
    "three",
    "four",
    "five",
    "six",
    "seven",
    "eight",
    "nine",
    "ten",
    "eleven",
    "twelve",
    "thirteen",
    "fourteen",
    "fifteen",
    "sixteen",
    "seventeen",
    "eighteen",
    "nineteen",
};

static char *tens[] = {
    "",
    "",
    "twenty",
    "thirty",
    "forty",
    "fifty",
    "sixty",
    "seventy",
    "eighty",
    "ninety",
};

static char *magnitude[] = {
    "",
    "thousand",
    "million",
    "billion",
    "trillion",
    "quadrillion",
    "quintillion",
};

static void add_space(char *buf)
{
    strcat(buf, " ");
}

static void do_one_group(unsigned amount, char *buf, char **magnitude)
{
    int val;

    val = amount / 1000;
    if (val > 0)
        do_one_group(val, buf, magnitude + 1);
    amount %= 1000;
    val = amount / 100;
    if (val > 0){
        strcat(buf, digits[val]);
        add_space(buf);
        strcat(buf, "hundred");
        add_space(buf);
    }

    val = amount % 100;
    if (val > 0)
    {
        if (val > 20)
        {
            strcat(buf, tens[val / 10]);
            add_space(buf);
        }
        strcat(buf, digits[val % 10]);
        add_space(buf);
        strcat(buf, *magnitude);
        add_space(buf);
    }
}

void written_amount(unsigned amount, char *buf){
    if(amount == 0)
        strcat(buf, "zero");
    else{
        *buf = '\0';
        do_one_group(amount, buf, magnitude);
    }
}

int main(int argc, char const *argv[])
{
    char buf[1000];
    written_amount(123456789, buf);
    printf("%s\n", buf);
    return 0;
}
