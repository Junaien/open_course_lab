#ifndef MEMORY_H
#define MEMORY_H
#include <stdlib.h>
typedef struct memory{
  int timestamp;
  u_int64_t tag;
  int valid;
} memory_t;
int compare_memory(const memory_t *a, const memory_t *b);
void set_valid(int v_bit,memory_t *const m);
void update_timestamp(int timestamp,memory_t *const m);
#endif
