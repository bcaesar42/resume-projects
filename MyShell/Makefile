mssh:	mssh.c ./tokenize/makeArgs.o ./utils/myUtils.o ./process/process.o ./pipes/pipes.o ./linkedlist/linkedList.o ./alias/alias.o ./history/history.o ./linkedlist/listUtils.o
	gcc -g mssh.c ./tokenize/makeArgs.o ./utils/myUtils.o ./process/process.o ./pipes/pipes.o ./linkedlist/linkedList.o ./alias/alias.o ./history/history.o ./linkedlist/listUtils.o -o mssh

makeArgs.o:	./tokenize/makeArgs.c ./tokenize/makeArgs.h
	gcc -g -c ./tokenize/makeArgs.c

myUtils.o:	./utils/myUtils.c ./utils/myUtils.h
	gcc -g -c ./utils/myUtils.c

process.o:	./process/process.c ./process/process.h
	gcc -g -c ./process/process.c

pipes.o:	./pipes/pipes.c ./pipes/pipes.h
	gcc -g -c ./pipes/pipes.c

linkedList.o:	./linkedlist/linkedList.c ./linkedlist/linkedList.h
	gcc -g -c ./linkedlist/linkedList.c

alias.o:	./alias/alias.c ./alias.h
	gcc -g -c ./alias/alias.c

history.o:	./history/history.c ./history/history.h
	gcc -g -c ./history/history.c

listUtils.o:	./linkedlist/listUtils.c ./linkedlist/listUtils.h
	gcc -g -c ./linkedlist/listUtils.c

clean:
	rm ./pipes/pipes.o
	rm ./utils/myUtils.o
	rm ./process/process.o
	rm ./tokenize/makeArgs.o
	rm ./linkedlist/linkedList.o
	rm ./alias/alias.o
	rm ./history/history.o
	rm ./linkedlist/listUtils.o
	rm mssh

