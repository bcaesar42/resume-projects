#include "pipes.h"
#include "../tokenize/makeArgs.h"

int containsPipe(char *s)
{
    int len, x;
    len = strlen(s);
    for (x = 0; x < len; x++)
    {
        if (s[x] == '|')
            return 1;
    }

    return 0;
}

char ** parsePrePipe(char *s, int * preCount)
{
    char* saveptr = NULL;
    char copy[100];
    strcpy(copy, s);

    char* token = strtok_r(copy, "|" , &saveptr);

    char** argv = NULL;
    int total = makeargs(token, &argv);

    *preCount = total;

    return argv;
}

char ** parsePostPipe(char *s, int * postCount)
{
    char* saveptr = NULL;
    char copy[100];
    strcpy(copy, s);

    char* postToken = strtok_r(copy, "|" , &saveptr);
    postToken = strtok_r(NULL, "|" , &saveptr);

    char** argv = NULL;
    int total = makeargs(postToken, &argv);

    *postCount = total;

    return argv;
}

void pipeIt(char ** prePipe, char ** postPipe)
{
    pid_t pid;
    int fd[2], res, status;

    pid = fork();

    if (pid < 0) //fork failed
    {
        printf("The main fork failed.\n");
        exit(-1);
    }
    else if (pid > 0) //Main
    {
        waitpid(pid, &status, 0);

        if (status != 0)
        {
            printf("Command not found\n");
        }
    }
    else //Descendant of Main
    {
        res = pipe(fd);

        if (res < 0)
        {
            printf("Pipe Failure\n");
            exit(-1);
        }// end if

        pid = fork();

        if (pid != 0) //Still a child of Main
        {
            waitpid(pid, &status, 0);

            if (status != 0)
            {
                exit(-1);
            }

            //Close the write end of the pipe
            close(fd[1]);
            //dup2 the read end of the pipe as stdin
            dup2(fd[0], 0);
            close(fd[0]);
            execvp(postPipe[0], postPipe);
            exit(-1);
        }// end if AKA child of main
        else
        {
            close(fd[0]);
            close(1);
            dup(fd[1]);
            close(fd[1]);
            execvp(prePipe[0], prePipe);
            exit(-1);
        }// end else AKA grandchild of main
    } // end descendant of Main
}

int containsRedirection(char * str)
{
    return 0;
}