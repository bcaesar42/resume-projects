#include "./pipes/pipes.h"
#include "./utils/myUtils.h"
#include "./process/process.h"
#include "./tokenize/makeArgs.h"
#include "./linkedlist/linkedList.h"
#include "./alias/alias.h"
#include "./history/history.h"
#include "./linkedlist/listUtils.h"

int main()
{
    //Startup:
    int HISTCOUNT = 100; //Default number of history entries displayed by the history command. Default: 100
    int HISTFILECOUNT = 1000; //Max size of the linked list for history entries. Default: 1000
    int HistEntryNumber = 1;
    LinkedList * histList = linkedList();
    LinkedList * aliasList = linkedList();

    FILE * rcFile = fopen(".msshrc", "r");
    if (rcFile != NULL)
    {
        char * temp = (char *) calloc(1000, sizeof(char));
        /* Because temp is being allocated 1000 bytes, the maximum length of any one line of
         * the .msshrc file is 999 characters (byte #1000 reserved for null terminating byte).
         */

        fscanf(rcFile, "%s", temp);
        char * save = NULL;
        strtok_r(temp, "=", &save);
        HISTCOUNT = atoi(strtok_r(NULL, "\n", &save));

        fscanf(rcFile, "%s", temp);
        save = NULL;
        strtok_r(temp, "=", &save);
        HISTFILECOUNT = atoi(strtok_r(NULL, "\n", &save));

        while (fscanf(rcFile, "%s", temp) != -1)
        {
            if (isAliasCommand(temp))
            {
                addFirst(aliasList, buildNode_Type(buildTypeAlias(temp)));
            }
            else if (containsPath(temp))
            {
                updatePath(temp);
            }
        }

        free(temp);
	fclose(rcFile);
    }

    FILE * histFile = fopen(".mssh_history", "r");
    if (histFile != NULL)
    {
        char * temp = (char *) calloc(100, sizeof(char));
        while (fscanf(histFile, "%s", temp) != -1)
        {
            if (histList->size == HISTFILECOUNT)
                removeLast(histList, cleanTypeHist);
            addFirst(histList, buildNode_Type(buildTypeHist(HistEntryNumber++, temp)));
        }
        free(temp);
	fclose(histFile);
    }
//-------------------------------------------------------------------
//-------------------------------------------------------------------

    //Command Handling:
    int argc, pipeCount;
    char **argv = NULL, s[MAX];
    int preCount = 0, postCount = 0, removeResult;
    char ** prePipe = NULL, ** postPipe = NULL;
  
    printf("command?: ");
    fgets(s, MAX, stdin);
    strip(s);

    while(strcmp(s, "exit") != 0)
    {
        if (strlen(s) > 0) //If the command is empty, do nothing and re-prompt.
        {
            //Add the command to the history list.
            if (histList->size == HISTFILECOUNT)
                removeLast(histList, cleanTypeHist);
            addFirst(histList, buildNode_Type(buildTypeHist(HistEntryNumber++, s)));

            //-------------------------------------------------------------------
            //Is it the "!" Command:
            if (isRedo(s))
            {
                redo(histList, s);
            }

            //-------------------------------------------------------------------
            /*If the command is the name of an existing alias,
              substitute that alias's command into s. */
            replaceAlias(aliasList, s);

            //-------------------------------------------------------------------
            //Piping:
            pipeCount = containsPipe(s);
            if (pipeCount > 0)
            {
                prePipe = parsePrePipe(s, &preCount);
                postPipe = parsePostPipe(s, &postCount);
                pipeIt(prePipe, postPipe);
                clean(preCount, prePipe);
                clean(postCount, postPipe);
            }// end if pipeCount

            //-------------------------------------------------------------------
            //History Command:
            else if (isHistoryCommand(s))
            {
                showHistory(histList, HISTCOUNT, s);
            }

            //-------------------------------------------------------------------
            //cd Command:
            else if (isCDCommand(s))
            {
                changeDirectory(s);
            }

            //-------------------------------------------------------------------
            //Making an alias:
            else if (isAliasCommand(s))
            {
                addFirst(aliasList, buildNode_Type(buildTypeAlias(s)));
                printf("New alias added.\n");
            }

            //-------------------------------------------------------------------
            //Removing an alias:
            else if (isUnaliasCommand(s))
            {
                removeResult = removeItem(aliasList,
                                          buildNode_Type(buildTypeUnalias(s)), cleanTypeAlias, compareTypeAlias);
                if (removeResult)
                    printf("The alias was removed.\n");
                else
                    printf("Alias not found/removed.\n");
            }

            //-------------------------------------------------------------------
            //The Path Command:
            else if (containsPath(s))
            {
                if (updatePath(s) == 1)
                    printf("PATH updated.\n");
                else
                    printf("Unable to set PATH to specified value.\n");
            }

            //-------------------------------------------------------------------
            //Redirection with < or >:
            else if (containsRedirection(s))
            {
                printf("Redirection commands not supported yet.");
            }

            //-------------------------------------------------------------------
            //Special case: echo $PATH
            else if (strcmp(s, "echo $PATH") == 0)
            {
                printf("%s\n", getenv("PATH"));
            }

            //-------------------------------------------------------------------
            //Standard Single Command:
            else
            {
                argc = makeargs(s, &argv);
                if (argc != -1)
                    forkIt(argv);

                clean(argc, argv);
                argv = NULL;
            }
        }
//-------------------------------------------------------------------
//-------------------------------------------------------------------
        //Re-prompting
        printf("command?: ");
        fgets(s, MAX, stdin);
        strip(s);

    }// end while (stop accepting new commands)

    FILE * output = fopen(".msshrc", "w");
    if (output != NULL)
    {
        fprintf(output, "HISTCOUNT=%d\n", HISTCOUNT);
        fprintf(output, "HISTFILECOUNT=%d\n", HISTFILECOUNT);

        if (aliasList->size > 0)
        {
            Alias * al = NULL;
            for (Node * alCur = aliasList->head->next; alCur != NULL; alCur = alCur->next)
            {
                al = (Alias *) alCur->data;
                fprintf(output, "alias %s=%s\n", al->name, al->command);
            }
        }

        fprintf(output, "PATH=%s\n", getenv("PATH"));
    }
    fclose(output);

    FILE * histOut = fopen(".mssh_history", "w");
    if (histOut != NULL && histList->size > 0)
    {
        Hist * hist = NULL;
        for (Node * cur = histList->head->next; cur != NULL; cur = cur->next)
        {
            hist = (Hist *) cur->data;
            fprintf(histOut, "%s\n", hist->entry);
        }
    }
    fclose(histOut);

    clearList(aliasList, cleanTypeAlias);
    free(aliasList);
    aliasList = NULL;

    clearList(histList, cleanTypeHist);
    free(histList);
    histList = NULL;

    return 0;
}// end main
