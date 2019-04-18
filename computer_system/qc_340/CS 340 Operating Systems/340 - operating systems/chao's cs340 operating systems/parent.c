#include <sys/wait.h>
#define NULL 0

int main (void) {
	if (fork() ==0){ /*This is the child process*/
		execve("child", NULL, NULL);
	exit(0);  /* Should never get here, terminate*/
	}
/* Parent code here*/
printf("Process[%d]: Parent in execution ...\n", getpid());
sleep(5);
if(wait(NULL) > 0)  /* Child terminating*/
	printf("Process[%d]: Parent detects terminating child \n",
getpid());
printf("Process[%d]: Parent terminating ...\n", getpid());
}
