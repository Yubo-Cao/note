#include <stdio.h>
#include <stdlib.h>

void swap(float *a, float *b) {
    float temp = *a;
    *a = *b;
    *b = temp;
}

int main(int argc, char const *argv[]) {
    float a, b, c;
    printf("Enter the three sides of a triangle: ");
    scanf("%f %f %f", &a, &b, &c);

    // Then let's sort them, so that a is largest
    if (a < b) swap(&a, &b);
    if (a < c) swap(&a, &c);
    if (b < c) swap(&c, &b);
    // a > b > c
    // if c <= 0, then it's not a triangle
    // if b + c <= a, then it is degenerate
    if (c <= 0 || b + c <= a) {
        printf("Not a triangle\n");
        return EXIT_FAILURE;
    } else if (a == b && b == c)
        printf("Equilateral\n");
    else if (a == b || b == c)
        printf("Isoceles\n");
    else
        printf("Scalene\n");
    return EXIT_SUCCESS;
}
