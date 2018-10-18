#include "process.h"
#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>
#include <wait.h>

void forkIt(char ** argv)
{
    int status;
    pid_t  myID = fork();

    if (myID == 0) //Child
    {
        execvp(argv[0] , argv);
        printf("Command not found\n");
        exit(-1);
    }
    else //Parent
    {
	    waitpid(myID, &status, 0);
    }
}
