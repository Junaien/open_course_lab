package javaThreads;
abstract public class StackUser extends Thread {
	protected StackClass stack;
	StackUser(String threadName, StackClass stack) {
		super(threadName);
		this.stack = stack;
		setDaemon(true);
	}

}
