#ifndef MYUTILS_H
#define MYUTILS_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

void strip(char *array);

int isCDCommand(char * str);
void changeDirectory(char * str);

int containsPath(char * str);

int updatePath(char * str);

#endif
