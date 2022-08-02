def naive(coefficients, x):
    res = 0
    # a_0 + a_1x + a_2x^2 + ... + a_{n-1}x^{n-1} + a_nx^n
    for exp, coefficient in enumerate(coefficients[1:], start=0):
        res += pow(x, exp) * coefficient
    return res


def better(coefficients, x):
    res = 0
    # a_0 + x(a_1 + x(...(a_{n-1} + x(a_n))))
    for coefficient in coefficients[::-1]:
        res += x * (coefficient + res)
    return res

