#include <stdio.h>
#include <stdlib.h>

// we want to return a boolean value about whether we are hungry
int hungry(void)
{
    return rand() & 1;
}

void eat_hamberger(void)
{
    printf("I'm eating a hamburger.\n");
}

int main(int argc, char const *argv[])
{
    do
    {
        eat_hamberger();
    } while (hungry());
    return 0;
}
