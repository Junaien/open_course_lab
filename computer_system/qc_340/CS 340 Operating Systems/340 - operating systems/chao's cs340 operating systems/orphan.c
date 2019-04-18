#include <stdio.h>
#include <unistd.h>

main()
{
	int pid;
	printf ("I'm the original process with PID %d and PPID %d.\n",
		getpid(), getppid ());
	pid = fork();
	if (pid != 0)	/* Brach based on return value from fork() */
	{
		/* pid is non-zero, so must be the parent */
		printf ("I'm the parent process with PID %d and PPID %d.\n",
			getpid(), getppid());
		printf ("my child's PID %d\n", pid);
	}
	else 
	{
		/* pid is zero, so must be the child */
		sleep(5); /* Make sure that the parent terminates first */
		printf ("I'm the child process with PID %d and PPID %d.\n",
			getpid(), getppid());
	}
	printf ("PID %d terminates.\n", getpid());
	}
	
