void strcpy(char *buf, char const *str)
{
    while ((*buf++ = *str++) != '\0')
        ;
}