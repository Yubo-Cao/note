static int w = 5;
// Redefinitation, still int, static, internal
// But not an error.
extern int x;

static float func1(int a, int b, int c)
{
    // You can't refine parameters
    int /* c */ d, e = 1;
    {
        int d, e, w;
        {
            int /* b , c ,*/ d;
            static int y = 2;
        }
    }
    {
        register int a, d, x;
        // You can't access static int y here.
        extern int y;
    }
    return 1.0;
}

static int y;

float func2(int a)
{
    // It is already static, you can't do that.
    extern int y;
    static int z;
}
