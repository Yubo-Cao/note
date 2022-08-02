#include <stdio.h>

void *scan_trig(int *trig)
{
    for (int i = 0; i < 3; i++)
    {
        scanf("%d", &trig[i]);
    }
    return trig;
}

int is_trig(int *trig)
{
    // 三角形三边关系
    return trig[0] + trig[1] > trig[2] &&
           trig[0] + trig[2] > trig[1] &&
           trig[1] + trig[2] > trig[0];
}

char *classify_trig(int *trig)
{
    if (trig[0] != trig[1] && trig[1] != trig[2])
        return "scalene";
    else if (trig[0] == trig[1] && trig[1] == trig[2])
        return "equilateral";
    else
        return "isosceles";
}

int main(int argc, char const *argv[])
{
    int trig[3];
    scan_trig(trig);
    if (!is_trig(trig))
        printf("Not a triangle\n");
    else
        printf("A %s triangle\n", classify_trig(trig));
    return 0;
}
