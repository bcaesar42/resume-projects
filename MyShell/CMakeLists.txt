cmake_minimum_required(VERSION 3.10)
project(mssh C)

set(CMAKE_C_STANDARD 99)

add_executable(mssh mssh.c
        pipes/pipes.h pipes/pipes.c
        process/process.h process/process.c
        tokenize/makeArgs.h tokenize/makeArgs.c
        utils/myUtils.h utils/myUtils.c
        pipes/pipes.c
        linkedlist/linkedList.h linkedlist/linkedList.c
        linkedlist/listUtils.h linkedlist/listUtils.c
        history/history.h history/history.c
        alias/alias.c alias/alias.h)
