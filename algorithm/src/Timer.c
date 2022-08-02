#include <stdio.h>
#include <time.h>

clock_t start,stop;
double duration;

int main(){
    // 从程序开始运行到现在走过的 ticks 为 start
    start = clock();
    // 函数运行时间
    stop = clock();
    duration = ((double)(stop - start)) / CLOCKS_PER_SEC;
    printf("%f\n", duration);
    return 0;
}