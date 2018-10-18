#include "listUtils.h"

Node * buildNode_Type(void * passedIn)
{
   Node* nn = (Node*) calloc( 1 , sizeof(Node));
   nn->next = NULL;
   nn->prev = NULL;
   nn->data = passedIn;
   return  nn;
}
