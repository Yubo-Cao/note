#include <limits.h>

// ensure myshort is at least 16 bit
#if SHRT_MIN >= 32767
#define shrt short
#else
#define shrt int
#endif

// Ensure myint is at least 32 bit
#if INT_MIN >= 2147483647
#define intr int
#else
#define intr long
#endif