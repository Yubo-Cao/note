#include <stdio.h>
#define N_VALUES 5

int main(int argc, char const *argv[]) {
    float values[N_VALUES];
    float *vp;

    for (vp = &values[0]; vp < &values[N_VALUES];) *vp++ = 0;
    return 0;
}
