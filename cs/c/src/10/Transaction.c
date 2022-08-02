#include <stdio.h>

#define PRODUCT_NAME_LEN 20

typedef struct
{
    char product[PRODUCT_NAME_LEN];
    int qty;
    float price;
    float total_amount;
} Transaction;

void print_receipt(register Transaction const *transaction)
{
    printf("%s\b", transaction->product);
    printf("%d @ $%.2f cost $%.2f\n", transaction->qty,
           transaction->price, transaction->total_amount);

    // 声明为 const 之后，结构体不可以被修改
    // transaction -> total_amount = transaction->qty * transaction->price;
}

// 最好的方案，并且将结构体声明的细节从调用者隐藏
void compute_total_amount(register Transaction *transaction)
{
    transaction->total_amount = transaction->qty * transaction->price;
}

int main(int argc, char const *argv[])
{
    Transaction t1 = {
        "iPhone",
        2,
        599.99,
        0};
    compute_total_amount(&t1);
    print_receipt(&t1);
    return 0;
}
