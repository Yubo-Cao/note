#include <stdio.h>

typedef struct
{
    int area_code;
    int prefix;
    int line;
} PhoneNumber;

typedef struct
{
    PhoneNumber *caller_phone;
    PhoneNumber *callee_phone;
    PhoneNumber *payment_phone;
} PhoneCall;


int main(int argc, char const *argv[])
{

    return 0;
}
