#include <stdio.h>
#include <time.h>
#include <math.h>

clock_t start, stop;
double duration;
#define MAXN 10;


double f(double a[], double x){
    // a is coefficients
    // x is value
    double res = 0.0;
    int n = sizeof a;
    for(int i = 0;i <= n;i++){
        res += (a[i] * pow(x, i));
    }
    return res;
}

double f2(double a[], double x){
    double res = 0.0;
    int n = sizeof a;
    for (int i = n;i >= 0; i--) {
        res += x * (a[i] + res);
    }
    return res;
}