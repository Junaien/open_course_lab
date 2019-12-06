#include "cache.h"
#include "memory.h"

#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
void en_print(const cache_t * cache){
  memory_t m = {};
  for(int i = 0; i < pow(2,cache->s); i++){
    int cnt = 0;
    for(int j = 0; j < cache->E && cache->memory[i][j].valid != 0; j++){
      cnt++;
    }
    printf("set %d: %d \n",i, cnt );
  }
}
void access_memory(const char *mode, u_int64_t addr, unsigned int size,
                   report_t *report, cache_t * const cache){
   //validation1: input assumption check
   assert(cache != NULL && cache -> s >0 && cache -> b > 0 && cache -> E > 0 && cache -> memory != NULL);
   assert(addr >= 0 && addr <= 0xffffffffffffffff);
   for(int i = 0; i < pow(2,cache -> s); i++)assert(cache->memory[i] != NULL);

   //easy case
   if(*mode == 'I')return;
   //caclulate target memory related information
   u_int64_t target_set  = (addr >> cache->b) %  (1 << cache->s);
   assert(target_set >=0 && target_set < pow(2,cache->s));

   memory_t *lines         = cache -> memory[target_set];
   int cur_min_index       = 0;
   u_int64_t target_tag    = (addr >> (cache -> s + cache -> b)) % (1 << (64 - cache->s - cache->b));
   u_int64_t target_time   =  ++cache->time;
   memory_t empty = {0};

   //linear searching for match
   int i = 0;
   for(; i < cache -> E; i++){
     if(compare_memory(&lines[i], &empty)){break;}
     if(lines[i].timestamp < lines[cur_min_index].timestamp){cur_min_index = i;}
     if(lines[i].tag == target_tag){
       lines[i].timestamp  = target_time;
       report-> hits   ++;
       printf("%s %lx,%d hit",mode, addr, size);
       if(*mode == 'M'){
         printf(" hit");
         report -> hits ++;
       }
       printf("\n");
       return;
     }
   }
   memory_t m = {.timestamp = target_time, .tag = target_tag, .valid = 1};
   printf("%s %lx,%d miss",mode, addr, size);
   if(i < cache->E && compare_memory(&lines[i], &empty)){
     lines[i]   = m;
   }else{
     lines[cur_min_index] = m;
     report-> evictions ++;
     printf(" eviction");
   }
   report-> misses ++;
   if(*mode == 'M'){
     report-> hits++;
     printf(" hit");
   }
   printf("\n");
}

cache_t * initialize_cache(int E, int s, int b){
  //init
  if(E <= 0 || s <= 0 || b <=0)return NULL;
  cache_t*  cache = (cache_t *)calloc(1, sizeof(cache_t));
  if(cache == NULL) return NULL;
  cache_t temp_c =  {.time = 0, .E = E, .s = s, .b = b};
  *cache       = temp_c;
  u_int64_t S = pow(2,s);
  u_int64_t B = pow(2,b);

  //allocate memory from top down
  cache -> memory = (memory_t **)calloc(S ,sizeof(memory_t *));
  if(cache -> memory == NULL){
    free(cache);
    return NULL;
  }

  assert(E > 0 && s > 0 && b > 0 && cache != NULL && cache -> memory != NULL);
  for(int i = 0; i < S; i++){
    assert(cache -> memory[i] == 0);
  }
  for(int i = 0; i < S; i++){
    cache->memory[i] = (memory_t *)calloc(E, sizeof(memory_t));
    if(cache->memory[i] == NULL){
      for(int j = 0; j < i; j++)free_cache(cache);
      return NULL;
    }
  }

  //cache supposed to be correctly set
  memory_t temp = {0};
  for(int i = 0; i < S; i++)
    for(int j = 0; j < E; j++){
      assert(compare_memory(&(cache->memory[i][j]),&temp) == 1);
    }
  return cache;
}

//input[cache]: must have memory allocated correctly
void free_cache(cache_t * const cache){
  u_int64_t S = pow(2,cache->s);
  u_int64_t B = pow(2,cache->b);

  assert(cache != NULL &&cache->s > 0 && cache-> b > 0  && cache-> memory != NULL);
  for(int i = 0; i < S; i++){
    assert(cache -> memory[i] != NULL);
    free(cache->memory[i]);
  }
  free(cache->memory);
  free(cache);
}
