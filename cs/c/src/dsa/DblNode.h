typedef struct Node
{
    struct Node *fwd;
    struct Node *bwd;
    int val;
} Node;