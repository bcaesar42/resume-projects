#include "alias.h"

void cleanTypeAlias(void * item)
{
    Alias * al = (Alias *)item;
    free(al->name);
    al->name = NULL;
    free(al->command);
    al->command = NULL;
    free(al);
}

void * buildTypeAlias(char * str)
{
    char * copy = (char *) calloc(strlen(str) + 1, sizeof(char));
    strcpy(copy, str);
    char * save = NULL;
    char * cmd = strtok_r(copy, " ", &save);
    char * nm = strtok_r(NULL, "=", &save);
    char * content = strtok_r(NULL, "\n", &save);
    Alias * newAlias = (Alias *)calloc(1, sizeof(Alias));

    newAlias->name = (char *) calloc(strlen(nm) + 1, sizeof(char));
    strcpy(newAlias->name, nm);

    newAlias->command = (char *) calloc(strlen(content) + 1, sizeof(char));
    strcpy(newAlias->command, content);

    free(copy);
    return newAlias;
}

int isAliasCommand(char * str)
{
    char * copy = (char *) calloc(strlen(str) + 1, sizeof(char));
    strcpy(copy, str);
    char * save = NULL;
    char * cmd = strtok_r(copy, " ", &save);
    char * name = strtok_r(NULL, "=", &save);
    char * content = strtok_r(NULL, "\n", &save);

    if (strcmp(cmd, "alias") == 0 && name != NULL && content != NULL)
    {
        free(copy);
        return 1;
    }
    else
    {
        free(copy);
        return 0;
    }
}

int isUnaliasCommand(char * str)
{
    char * copy = (char *) calloc(strlen(str) + 1, sizeof(char));
    strcpy(copy, str);
    char * save = NULL;
    char * cmd = strtok_r(copy, " ", &save);
    char * name = strtok_r(NULL, "\n", &save);

    if (strcmp(cmd, "unalias") == 0 && name != NULL)
    {
        free(copy);
        return 1;
    }
    else
    {
        free(copy);
        return 0;
    }
}

int compareTypeAlias(const void * p1, const void * p2)
{
    Alias * a1 = (Alias *) p1;
    Alias * a2 = (Alias *) p2;

    return strcmp(a1->name, a2->name);
}

void * buildTypeUnalias(char * str)
{
    char * copy = (char *) calloc(strlen(str) + 1, sizeof(char));
    strcpy(copy, str);
    char * save = NULL;
    char * cmd = strtok_r(copy, " ", &save);
    char * nm = strtok_r(NULL, "\n", &save);
    Alias * newAlias = (Alias *)calloc(1, sizeof(Alias));

    newAlias->name = (char *) calloc(strlen(nm) + 1, sizeof(char));
    strcpy(newAlias->name, nm);

    newAlias->command = NULL;

    free(copy);
    return newAlias;
}

void replaceAlias(LinkedList * theList, char * str)
{
    if (theList != NULL && str != NULL && theList->size > 0)
    {
        Node *cur = theList->head->next;
        Alias * al = NULL;
        int found = 0;

        while (cur != NULL && found == 0)
        {
            al = (Alias *) cur->data;
            if (strcmp(al->name, str) == 0)
            {
                strcpy(str, al->command);
                found = 1;
            }
            else
                cur = cur->next;
        }
    }
}