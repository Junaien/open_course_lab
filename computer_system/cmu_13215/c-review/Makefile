CC=clang
CFLAGS=-Wall -Werror
CLIBS=-lm

all: getopt_example missing_side

getopt_example: getopt_example.c
	$(CC) $(CFLAGS) getopt_example.c -o getopt_example

missing_side: missing_side.c
	$(CC) $(CFLAGS) $(CLIBS) missing_side.c -o missing_side

clean:
	rm -f getopt_example missing_side