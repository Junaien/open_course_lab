/*
 * Code for basic C skills diagnostic.
 * Developed for courses 15-213/18-213/15-513 by R. E. Bryant, 2017
 * Modified to store strings, 2018
 */

/*
 * This program implements a queue supporting both FIFO and LIFO
 * operations.
 *
 * It uses a singly-linked list to represent the set of queue elements
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "harness.h"
#include "queue.h"

/*
  Create empty queue.
  Return NULL if could not allocate space.
*/
queue_t *q_new()
{
    queue_t *q =  malloc(sizeof(queue_t));
    /* What if malloc returned NULL? */
    if(q == NULL)return NULL;
    q->head = NULL;
    q->tail = NULL;
    q->size = 0;
    return q;
}

/* Free all storage used by queue */
void q_free(queue_t *q)
{
    if(q == NULL)return;
    /* How about freeing the list elements and the strings? */
    list_ele_t * cur = q -> head;
    while(cur != NULL){
      list_ele_t * prev = cur;
      cur = cur -> next;
      free(prev->value);
      free(prev);
    }
    /* Free queue structure */
    free(q);
}

/*
  Attempt to insert element at head of queue.
  Return true if successful.
  Return false if q is NULL or could not allocate space.
  Argument s points to the string to be stored.
  The function must explicitly allocate space and copy the string into it.
 */
bool q_insert_head(queue_t *q, char *s)
{
    if(q == NULL || s == NULL)return false;
    list_ele_t *newh;
    newh = malloc(sizeof(list_ele_t));
    if(newh == NULL)return false;
    char * str = malloc((strlen(s) + 1) * sizeof(char));
    if(str == NULL){
      free(newh);
      return false;
    }
    /* What should you do if the q is NULL? */
    /* Don't forget to allocate space for the string and copy it */
    /* What if either call to malloc returns NULL? */
    strcpy(str,s);
    newh->value = str;
    newh->next = q->head;
    q->head = newh;
    if(++(q->size) == 1)q->tail = newh;
    return true;
}


/*
  Attempt to insert element at tail of queue.
  Return true if successful.
  Return false if q is NULL or could not allocate space.
  Argument s points to the string to be stored.
  The function must explicitly allocate space and copy the string into it.
 */
bool q_insert_tail(queue_t *q, char *s)
{
    /* You need to write the complete code for this function */
    /* Remember: It should operate in O(1) time */
    if(q == NULL || s == NULL)return false;
    list_ele_t *newt;

    newt = malloc(sizeof(list_ele_t));
    if(newt == NULL)return false;

    char * str = malloc((strlen(s) + 1) * sizeof(char));
    if(str == NULL){
      free(newt);
      return false;
    }

    strcpy(str,s);
    newt->value = str;
    newt->next = NULL;
    if(++(q->size) == 1){
      q->head = newt;
      q->tail = newt;
    }else{
      q->tail->next = newt;
      q->tail = newt;
    }
    return true;
}

/*
  Attempt to remove element from head of queue.
  Return true if successful.
  Return false if queue is NULL or empty.
  If sp is non-NULL and an element is removed, copy the removed string to *sp
  (up to a maximum of bufsize-1 characters, plus a null terminator.)
  The space used by the list element and the string should be freed.
*/
bool q_remove_head(queue_t *q, char *sp, size_t bufsize)
{
    /* You need to fix up this code. */
    if(q == NULL || q-> size == 0 || sp == NULL)return false;
    list_ele_t *n = q->head;
    strncpy(sp,n->value,bufsize - 1);
    sp[bufsize-1] = '\0';
    q->head = q->head->next;
    free(n->value);
    free(n);
    if(--(q->size) == 0)q->tail = NULL;
    return true;
}

/*
  Return number of elements in queue.
  Return 0 if q is NULL or empty
 */
int q_size(queue_t *q)
{
    /* You need to write the code for this function */
    /* Remember: It should operate in O(1) time */
    if(q == NULL)return 0;
    return q->size;
}

/*
  Reverse elements in queue
  No effect if q is NULL or empty
  This function should not allocate or free any list elements
  (e.g., by calling q_insert_head, q_insert_tail, or q_remove_head).
  It should rearrange the existing ones.
 */
void q_reverse(queue_t *q)
{
    /* You need to write the code for this function */
    if(q == NULL || q->size <= 1)return;
    list_ele_t *A = q->head;
    list_ele_t *B = q->head->next;
    list_ele_t *C = q->head->next->next;
    while(true){
      B-> next = A;
      if(C == NULL)break;
      A = B;
      B = C;
      C = C->next;
    }
    q->tail = q->head;
    q->tail->next = NULL;
    q->head = B;
}
