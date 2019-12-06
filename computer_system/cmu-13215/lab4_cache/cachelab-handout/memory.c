#include "memory.h"
#include<stdlib.h>

int compare_memory(const memory_t *a, const memory_t *b){
  return   a->timestamp == b->timestamp &&
           a->tag       == b->tag       &&
           a->valid     == b->valid;
}

void update_timestamp(int timestamp, memory_t * const m){
  m-> timestamp = timestamp;
}
void set_valid(int v_bit, memory_t * const m){
  m-> valid = v_bit;
}
