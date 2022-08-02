#include<stdio.h>

int main(void){
    int quantity;
    scanf("%d", &quantity);
    double price;
    scanf("%lf", &price);
    char* department;
    scanf("%s", department);
    printf("%d %g %s\n", quantity, price, department);
}