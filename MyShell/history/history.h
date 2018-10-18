#ifndef MSSH_HISTORY_H
#define MSSH_HISTORY_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../linkedlist/linkedList.h"

struct historyEntry
{
    char * entry;
    int num;
};
typedef struct historyEntry Hist;

void cleanTypeHist(void * item);

void * buildTypeHist(int num, char * str);

void printTypeHist(void * item);

int isHistoryCommand(char * str);

void showHistory(LinkedList * theList, int HISTCOUNT, char * str);

int isRedo(char * str);

void redo(LinkedList * theList, char * str);

#endif //MSSH_HISTORY_H
