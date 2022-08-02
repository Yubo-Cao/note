#include <stdio.h>

float THRESHOLD[] = {0, 23350.0, 56550.0, 117950.0, 256550.0};
float TAX_RATE[] = {0.15, 0.28, 0.31, 0.36, 0.396};

float single_fax(float income)
{
    float *thres_pt = THRESHOLD, *tax_pt = TAX_RATE, tax = 0.0;

    while (income > *++thres_pt)
        tax += *tax_pt++ * (*thres_pt - thres_pt[-1]);

    tax += *tax_pt * (income - thres_pt[-1]);
    return tax;
}

int main(int argc, char const *argv[])
{
    printf("%.2f\n", single_fax(40000.0f));
    return 0;
}
