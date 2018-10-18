#ifndef MSSH_ALIAS_H
#define MSSH_ALIAS_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../linkedlist/linkedList.h"

struct commandAlias
{
    char * name;
    char * command;
};
typedef struct commandAlias Alias;

void cleanTypeAlias(void * item);

void * buildTypeAlias(char * str);

int isAliasCommand(char * str);

int isUnaliasCommand(char * str);

int compareTypeAlias(const void * p1, const void * p2);

void * buildTypeUnalias(char * str);

void replaceAlias(LinkedList * linkedList1, char * str);

#endif //MSSH_ALIAS_H
