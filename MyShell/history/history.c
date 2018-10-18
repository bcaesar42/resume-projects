#include "history.h"

void cleanTypeHist(void * item)
{
    Hist * thisHist = (Hist * )item;
    free(thisHist->entry);
    free(thisHist);
}

void * buildTypeHist(int num, char * str)
{
    Hist * newHist = (Hist *)calloc(1, sizeof(Hist));
    newHist->entry = (char *) calloc(strlen(str) + 1, sizeof(char));
    strcpy(newHist->entry, str);
    newHist->num = num;
    return newHist;
}

void printTypeHist(void * item)
{
    Hist * thisHist = (Hist *)item;
    printf("%d:   %s\n", thisHist->num, thisHist->entry);
}

int isHistoryCommand(char * str)
{
    int result = 0;
    char * copy = (char *) calloc(strlen(str) + 1, sizeof(char));
    strcpy(copy, str);
    if (strcmp("history", strtok(copy, " ")) == 0)
        result = 1;
    free(copy);
    return result;
}

void showHistory(LinkedList * theList, int HISTCOUNT, char * str)
{
    int displayCount = 0;
    int secondArg = 0;
    char * copy = (char *) calloc(strlen(str) + 1, sizeof(char));
    strcpy(copy, str);
    char * save = NULL;
    char * keyword = strtok_r(copy, " ", &save);
    if (strlen(save) > 0)
        secondArg = atoi(save);

    if (secondArg > 0)
        displayCount = secondArg;
    else
        displayCount = HISTCOUNT;

    Node * cur = theList->head->next;
    /*
     * cur will start by walking to the last node that it needs to print.
     * Then, cur will print the entry in each node as it walks backwards to the front of the list.
     * By doing it this way, the last entry to be added to the list will be printed last.
     * */
    for (int i = 0; i < displayCount && cur->next != NULL; i++)
    {
        cur = cur->next;
    }

    //Note: Each call of the history command will not print itself.
    while (cur != theList->head->next && cur != NULL)
    {
        printTypeHist(cur->data);
        cur = cur->prev;
    }

    free(copy);
}

int isRedo(char * str)
{
    if (strlen(str) < 2 || str[0] != '!')
        return 0;

    if (str[0] == '!' && str[1] == '!')
        return 1;
    else if (str[0] == '!' && atoi(str+1) > 0)
        return 1;
    else
        return 0;
}

void redo(LinkedList * theList, char * str)
{
    Node * cur = theList->head->next;
    if (cur == NULL || cur->next == NULL)
    {
        printf("There is no history entry to access.\n");
    }
    else if (strcmp("!!", str) == 0)
    {
        Hist * lastEntry = (Hist *) cur->next->data;
        strcpy(str, lastEntry->entry);
    }
    else if (atoi(&str[1]) > 0)
    {
        int targetHistNum = atoi(&str[1]);
        Hist * curHist = NULL;
        for (Node * cur = theList->head->next; cur != NULL; cur = cur->next)
        {
            curHist = (Hist *) cur->data;
            if (curHist->num == targetHistNum)
            {
                strcpy(str, curHist->entry);
                return;
            }
        }
        printf("History entry %d not found.\n", targetHistNum);
    }
}