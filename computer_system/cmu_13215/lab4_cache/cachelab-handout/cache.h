#ifndef CACHE_H
#define CACHE_H
#include "memory.h"
typedef struct cache {
  int time;
  int E;
  int s;
  int b;
  memory_t ** memory;
} cache_t;
void free_cache(cache_t *const cache);
cache_t* initialize_cache(int E,int S,int B);
typedef struct report {
  int misses;
  int hits;
  int evictions;
}report_t;
void access_memory(const char *mode,u_int64_t addr,unsigned int size, report_t *report ,cache_t * const cache);

#endif
