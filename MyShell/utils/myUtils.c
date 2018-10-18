#include "myUtils.h"

void strip(char *array)
{
	if(array == NULL)
	{
		perror("array is null");
		exit(-99);
	}// end if

	int len = strlen(array), x = 0;
   
	while(array[x] != '\0' && x < len)
	{
	  if(array[x] == '\r')
		 array[x] = '\0';

	  else if(array[x] == '\n')
		 array[x] = '\0';

	  x++;

	}// end while
   
}// end strip

int isCDCommand(char * str)
{
    int comp;
    char * copy = (char *) calloc(strlen(str) + 1, sizeof(char));
    strcpy(copy, str);

    char * save = NULL;
    char * token = strtok_r(copy, " ", &save);

    if (strcmp(token, "cd") == 0)
        comp = 1;
    else
        comp = 0;

    free(copy);
    return comp;
}

void changeDirectory(char * str)
{
    int didWork;
    char * copy = (char *) calloc(strlen(str) + 1, sizeof(char));
    strcpy(copy, str);

    char * save = NULL;
    char * token = strtok_r(copy, " ", &save);
    token = strtok_r(NULL, "\n", &save);

    didWork = chdir(token);

    if (didWork != 0)
        printf("Could not change directory\n");

    free(copy);
}

int containsPath(char * str)
{
    char * copy = (char *) calloc(strlen(str) + 1, sizeof(char));
    strcpy(copy, str);
    char * save = NULL;

    char * keyword = strtok_r(copy, "=", &save);
    if (keyword != NULL && strcmp(keyword, "PATH") == 0 && strlen(str) > 5 && str[4] == '=')
    {
        free(copy);
        return 1;
    }

    free(copy);
    return 0;
}

int updatePath(char * str) // PATH=$PATH:/bin
{
    int result = 0;
    char * copy = (char *) calloc(strlen(str) + 1, sizeof(char));
    strcpy(copy, str);
    char * save = NULL;
    char * keyword = strtok_r(copy, "=", &save);
    char * firstValue = strtok_r(NULL, ":", &save);

    //Double check that it is a PATH command.
    if (keyword != NULL && strcmp(keyword, "PATH") == 0 && firstValue != NULL)
    {
        int newPathSize = 1;
        char * newPath = NULL;

        if (strlen(save) == 0 && strcmp(firstValue, "$PATH") == 0) //Maintaining the current PATH.
            result = 1;
        else if (strcmp(firstValue, "$PATH") == 0) //Appending to the current PATH.
        {
            newPathSize += strlen(getenv("PATH"));
            newPathSize += strlen(save);

            newPath = (char *) calloc(newPathSize + 1, sizeof(char));
            strcat(newPath, getenv("PATH"));
            strcat(newPath, ":");
            strcat(newPath, save);
        }
        else //Overriding the current PATH.
        {
            newPathSize += strlen(firstValue);
            newPathSize += strlen(save);

            if (strlen(save) > 0)
                newPathSize++;

            newPath = (char *) calloc(newPathSize, sizeof(char));
            strcat(newPath, firstValue);
            if (strlen(save) > 0)
            {
                strcat(newPath, ":");
                strcat(newPath, save);
            }
        }
        if (newPath != NULL && setenv("PATH", newPath, 1) == 0)
            result = 1;

        free(newPath);
    }

    free(copy);
    return result;
}