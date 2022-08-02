long factorial(int n)
{
    return (n <= 1) ? 1 : factorial(n - 1) * n;
}

long iter_factorial(int n)
{
    long result = 1;
    for (int i = 1; i <= n; i++)
        result *= i;
    return result;
}

