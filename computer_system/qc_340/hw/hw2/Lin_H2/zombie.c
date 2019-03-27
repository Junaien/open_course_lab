#include <stdio.h>
#include <unistd.h>

main()
{
	
	if (fork() != 0) {	/* Brach based on return value from fork() */
		/* pid is non-zero, so must be the parent */
		printf ("I'm the parent process my id is %d.\n", getpid());
		
		//parent process sleep 30 seconds
		sleep(30);
		//after parent process sleep 30 second, the parent process terminates.
		exit(0);
	}
	else {
		/* pid is zero, so must be the child */
		printf ("I'm the child process with pid %d and my parent has ppid %d.\n",
			getpid(), getppid());
        sleep(1);
        exit(0);
	}
	printf ("PID %d terminates.\n", getpid());
	
}


	
