#include <stdio.h>

enum Word
{
    WHO = 1,
    WHAT = 2,
    WHEN = 3,
    WHERE = 4,
    WHY = 5
};

char *get_word(enum Word word)
{
    switch (word)
    {
    case WHO:
        return "who";
    case WHAT:
        return "what";
    case WHEN:
        return "when";
    case WHERE:
        return "where";
    case WHY:
        return "why";
    default:
        return "don't know";
    }
}

int main(int argc, char const *argv[])
{
    enum Word which_word = WHO;
    printf("%s\n", get_word(which_word));
    return 0;
}
