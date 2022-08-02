#include <float.h>

static double income_limits[] = {0, 23350, 56550, 117950, 256500, DBL_MAX};
static float tax_rates[] = {0, 0.15, 0.28, 0.31, 0.36, 0.396};
static float base_tax[] = {0, 3502.5, 12798.5, 31832.5, 81710.5};

double single_tax(double income)
{
    int category;

    for (category = 1; income >= income_limits[category]; category++)
        ;
    category--;
    return base_tax[category] + (income - income_limits[category - 1]) * tax_rates[category];
}