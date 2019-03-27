package javaThreads;
/*stack class from thread turtorail online  */
public class StackClass {
	private Object[] stackArray;
	private volatile int topOfStack;
	StackClass(int capacity) {
		stackArray = new Object[capacity];
		topOfStack = -1;
	}
	public synchronized Object pop() {
		
		while (isEmpty()) {
			try {
				System.out.println(Thread.currentThread()
						+ ": waiting to pop");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Object obj = stackArray[topOfStack];
		stackArray[topOfStack--] = null;
		System.out.println(Thread.currentThread() + ": leaving the line to be served");
		notify();
		return obj;
	}
	public synchronized void push(Object element) {
		//System.out.println(Thread.currentThread() + ": pushing");
		while (isFull()) {
			try {
				System.out.println(Thread.currentThread()
						+ ": waiting to push");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		stackArray[++topOfStack] = element;
		//System.out.println(Thread.currentThread()
			//	+ ": is on the clerk line");
		notify();
	}
	public boolean isFull() {
		return topOfStack >= stackArray.length - 1;
	}
	public boolean isEmpty() {
		return topOfStack < 0;
	}
}
