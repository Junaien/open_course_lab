int main(void) {
/* The child process's new program
	This program replaces the parent's program */
	printf("Process[%d]: child in execution ...\n",getpid());
	sleep(1);
printf("Process[%d]: child terminating ...\n", getpid());
}
