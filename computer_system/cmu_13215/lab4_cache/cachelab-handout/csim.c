#include "cachelab.h"
#include <stdio.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include "cache.h"


int main(int argc, char *argv[])
{
    extern char *optarg;
    extern int  optind, opterr, optopt;

    int error_flag   = 0;
    int optcnt       = 0;
    int help_flag    = 0;
    int verbose      = 0;
    int set_arg      = -1;
    int line_arg     = -1;
    int block_arg    = -1;
    char trace_file[20];
    char opt;

    //reads in argument from command lien
    while ((opt = getopt(argc, argv, "s:E:b:t:hv")) != -1){
      optcnt ++;
      switch (opt) {
          case 'v': verbose = 1;
                    break;
          case 'h': help_flag = 1;
                    break;
          case 's': set_arg   = atoi(optarg);
                    break;
          case 'E': line_arg  = atoi(optarg);
                    break;
          case 'b': block_arg = atoi(optarg);
                    break;
          case 't': strncpy(trace_file,optarg,20);
                    break;
          default : error_flag = 1;
      }
    }

    if(optcnt > 6 || optcnt < 4)error_flag = 1;
    if(error_flag || help_flag){
      fprintf(stderr, "usage: -s arg -E arg -b arg -t arg [-h] [-v]\n"
                      "-s: set   bit size\n"
                      "-E: line  size\n"
                      "-b: block bit size\n"
                      "-t: file  path\n");
      exit(error_flag);
    }
    // if(verbose){printf("%s\n", "eeeeeh,I am so drunk");}
    cache_t * cache = initialize_cache(line_arg, set_arg, block_arg);
    //read from file and process
    FILE * fp;
    if((fp = fopen(trace_file, "r")) == NULL){
      printf("Error! opening file\n");
      exit(1);
    }

    char mode[20];
    u_int64_t addr;
    unsigned int size;
    report_t report;
    report.hits = 0;
    report.evictions = 0;
    report.misses    = 0;
    while(fscanf(fp, "%s %lx,%i\n", mode, &addr, &size) != EOF){
      // printf("%s %lu,%i \n", mode, addr, size);
      access_memory(mode, addr, size, &report, cache);
    }

    free_cache(cache);
    printSummary(report.hits, report.misses, report.evictions);

    return 0;
}
