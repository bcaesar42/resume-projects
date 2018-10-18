#include "linkedList.h"

/**
 * @brief The so called "constructor" for the linked list
 *
 * The linked list constructor builds a non circular linked list that
 * contains a dummy head not.  This is meant to be similar to a Java
 * linked list constructor.
 *
 * @return LinkedList * - The linked list pointer that contains a dummy head node but is not circular.
 */
LinkedList * linkedList()
{
   LinkedList* newList = (LinkedList*) calloc(1, sizeof(LinkedList));
   newList->size = 0;
   newList->head = (Node*) calloc(1, sizeof(Node));
   return newList;
}


/**
 * @brief The add last function for the linked list
 *
 * Appends the specified node to the end of this list
 *
 * @param theList - The specified linked lis
 * @param nn - The node to be added
 *
 * @warning - The passed in LinkedList * theList is checked - exit(-99) if NULL
 * @warning - The passed in Node * nn is checked - exit(-99) if NULL
 */
void addLast(LinkedList * theList, Node * nn)
{
   if (theList != NULL & nn != NULL)
   {
       Node *cur = theList->head;

       while (cur->next != NULL)
       {
           cur = cur->next;
       }

       nn->next = NULL;
       nn->prev = cur;
       cur->next = nn;

       theList->size++;
   }
}

/**
 * @brief The add first function for the linked list
 *
 * Appends the specified node to the beginning of this list
 *
 * @param theList - The specified linked list
 * @param nn - The node to be added
 *
 * @warning - The passed in LinkedList * theList is checked - exit(-99) if NULL
 * @warning - The passed in Node * nn is checked - exit(-99) if NULL
 */
void addFirst(LinkedList * theList, Node * nn)
{
   if (theList == NULL || nn == NULL)
   {
      perror("The list or the past-in node is NULL\n");
      exit(-99);
   }

   nn->next = theList->head->next;
   nn->prev = theList->head;
   theList->head->next = nn;
   theList->size++;

   if (nn->next != NULL)
       nn->next->prev = nn;
}

/**
 * @brief The remove first function for the linked list
 *
 * Removes the specified node to the beginning of this list
 *
 * @param theList - The specified linked list
 * @param *removeData - The function pointer for freeing the specific data type
 *
 * @warning - The passed in LinkedList * theList is checked - exit(-99) if NULL
 */
void removeFirst(LinkedList * theList, void (*removeData)(void *))
{
   if (theList == NULL || theList->size < 1 || theList->head->next == NULL)
   {
       perror("The list is empty.\n");
       exit(-99);
   }
      
   Node * cur = theList->head->next;

   if (theList->size > 1)
   {
       theList->head->next = cur->next;
       cur->next->prev = theList->head;
   }
   else
   {
       theList->head->next = NULL;
   }

   theList->size--;
   removeData(cur->data);
   cur->next = NULL;
   cur->data = NULL;
   cur->prev = NULL;
   free(cur);
}

/**
 * @brief The remove last function for the linked list
 *
 * Removes the specified node to the end of this list
 *
 * @param theList - The specified linked list
 * @param *removeData - The function pointer for freeing the specific data type
 *
 * @warning - The passed in LinkedList * theList is checked - exit(-99) if NULL
 */
void removeLast(LinkedList * theList, void (*removeData)(void *))
{
    if (theList == NULL || theList->size < 1|| theList->head->next == NULL)
    {
      perror("The list is empty.\n");
      exit(-99);
    }
    
    Node * cur;
    for (cur = theList->head->next; cur->next != NULL; cur = cur->next)
    {
      //Empty for loop
    }
    
    cur->prev->next = NULL;
    removeData(cur->data);
    cur->prev = NULL;
    free(cur);
    theList->size--;
}

/**
 * @brief The remove item function for the linked list
 *
 * Removes the first occurrence of the specified element from this list, if it
 * is present.  If this list does not contain the element, it is unchanged.
 *
 * @param theList - The specified linked list
 * @param nn - The node to be added
 * @param *removeData - The function pointer for freeing the specific data type
 * @param *compare - The compare function to compare specific data type
 *
 * @warning - The passed in LinkedList * theList is checked - exit(-99) if NULL
 * @warning - The passed in Node * nn is checked - exit(-99) if NULL
 */
int removeItem(LinkedList * theList, Node * nn, void (*removeData)(void *), int (*compare)(const void *, const void *))
{
   if (theList == NULL || nn == NULL || theList->size < 1)
   {
       removeData(nn->data);
       free(nn);
       return 0;
   }

   Node * test = theList->head->next->next;
   Node * cur = theList->head->next;
   int result = 0;
   
   while ((cur != NULL) && (compare(nn->data, cur->data) != 0))
   {
       cur = cur->next;
   }
   
   if (cur != NULL)
   {
      if (cur->next != NULL)
      {
         cur->next->prev = cur->prev;
      }
      
      cur->prev->next = cur->next;
      cur->next = NULL;
      cur->prev = NULL;
      removeData(cur->data);
      cur->data = NULL;
      free(cur);
      theList->size--;
      result = 1;
   }
   else
   {
       result = 0;
   }

    nn->next = NULL;
    nn->prev = NULL;
    removeData(nn->data);
    free(nn);
    return result;
}

/**
 * @brief Empties the list and its contents
 *
 * Removes all of the elements from this list.  The list will be empty after the function completes
 *
 * @param theList - The specified linked list
 * @param *removeData - The function pointer for freeing the specific data type
 * @param *compare - The compare function to compare specific data type
 *
 * @warning - The passed in LinkedList * theList is checked - if NULL nothing happens
 */
void clearList(LinkedList * theList, void (*removeData)(void *))
{
   if (theList != NULL)
   { 
      Node* cur;
      Node* del;
      for (cur = theList->head->next; cur != NULL; theList->size--)
      {
         del = cur;
         cur = cur->next;
         del->next = NULL;
         del->prev = NULL;
         removeData(del->data);
         free(del);
      }
      
      free(theList->head);
   }
}

/**
 * @brief Prints the contents of this linked list
 *
 * Prints the contents, if there are any of this linked list.  If the list is NULL
 * or empty, "Empty List" is printed.
 *
 * @param theList - The specified linked list
 * @param *convert - The function pointer for printing the specific data type
 *
 * @warning - The passed in LinkedList * theList is checked - if NULL "Empty List" is printed
 */
void printList(const LinkedList * theList, void (*convertData)(void *))
{
   if (theList == NULL || theList->head->next == NULL)
      printf("Empty List\n");
      
      else
      {
         Node* cur;
         for (cur = theList->head->next; cur != NULL; cur = cur->next)
         {
            convertData(cur->data);
         }
      }
}
