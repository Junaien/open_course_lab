/*
 * Student Thread simulates the behavior of the students.
 */
public class studentThread extends Thread{
	
	private timerThread timer;
	private teacherThread teacher;
	
	private static int questionsA = 4;
	private static int questionsB = 2;
	private int willSleep =0 ;
	
	private  boolean isAnswered;
	
	private static int random;
	private static long time = System.currentTimeMillis();
	
	studentThread(int id, timerThread timer, teacherThread teacher){
		
		setName("Student-" + id);
		this.timer = timer;
		this.teacher = teacher;
		
	}//constructor
	
	//Setters
	public void setWillSleep(int willSleep){ this.willSleep = willSleep; }
	
	public void setQuestionsA(int questionsA){ studentThread.questionsA = questionsA; }
	
	public void setQuestionsB(int questionsB){ studentThread.questionsB = questionsB; }
	
	public synchronized void setIsAnswered(boolean isAnswered){ this.isAnswered = isAnswered; }
	
	public void msg(String m){
		
		System.out.println(getName()+" [" + (System.currentTimeMillis()-time)+"] "+": ");
		System.out.println(m);
		
	}
	/*
	 * Terminates the process using stop()
	 * Deprecated(Disable when debugging)
	 */
	@SuppressWarnings("deprecation")
	public void goHome(){
		
		stop();
		
	}//gohome	
	/*
	 * Flow chart of the methods run
	 * in sequence for students.
	 */
	public void run(){
		
		arrivesInSchool();
		askQuestionsA();
		askQuestionsB();
		leavesSchool();
	}	
	/*
	 * Simulates students to arrive in 
	 * different times to school.
	 */
	private void arrivesInSchool(){
		
		random = (int) (Math.random()*500);
		try{
			sleep(random);
		} 
		catch (InterruptedException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		msg("has arrived in school.");
		
		while(!timer.isLabOpen()){
			//Busy waits until lab is open.	
		}
		//Kicks students that are over the capacity
		if(!teacher.isSeatsAvailable()){
			msg("lab is full. Goes home");
			goHome();
		}
	}
	/*
	 * Students starts writing E-mails for questionsA
	 * and will simulate thinking time and if done 
	 * thinking send right away added in queue(FCFS).
	 */
	private void askQuestionsA(){
		
		random = (int) ((Math.random()*questionsA)+1);
		int max = random;
			
		for(int i=1; i<=max; i++){
				//To give chance to the other process
				yield();
				random = (int) ((Math.random()*300)+10);						
				try{
					sleep(random);
				} 
				catch (InterruptedException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//breaks the loop if email session is done, no need to E-mail.	
				if(!timer.emailSession()){
					break;
				}
				//Priority is changed here and question added in queue here.
				this.setPriority(getPriority()+1);
				msg("thought for " + random + "ms for questionA #"+ i + ". E-mail sent!");	
				teacher.addQueueA(getName() + ", question#" + (i));	
				this.setPriority(NORM_PRIORITY);
								
		}	
		
	}
	/*
	 * Students will proceed to wait and chat for 
	 * questionsB. Simulate writing time of question
	 * Until teacher replies, student cannot move
	 * to their next question. If done surf the net
	 */
	private void askQuestionsB() {
		
		while(!timer.getChatKey()){
			//Busy waits until chat is open.
		}
		
		random = (int) ((Math.random()*questionsB)+1);
		int max = random;
		for(int i=1; i<=max; i++){
			
			random = (int) ((Math.random()*300)+10);						
			try{
				sleep(random);
			} 
			catch (InterruptedException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Breaks the loop if teacher leaves. No point to chat if there is no teacher
			if(!timer.chatSession()){
				break;
			}	
			//If else to give teacher heads up that its his last question.
			if(i==max){
				msg("is asking last question. Waiting for chat...");	
			}
			else{
				msg("is asking a question. Waiting for chat...");
			}
						
			teacher.addQueueB(currentThread());
			while(!isAnswered && timer.chatSession()){
				//Busy waiting until teacher answers the question
				//or time for chat has ended and teacher leaves
			}
			
			//Debug delete later. False must not reached here while teacher is chatting.
			if(isAnswered==false && timer.chatSession()){
				throw new IllegalArgumentException("Must be true"); 
			}
			//Prompt that if student has no more question
			//he will be just surfing the Internet.
			if(i==max && timer.chatSession()){
				msg("has no more questions. Browsing the net!");
				try{
					sleep(5000);
				} 
				catch (InterruptedException e){
					// TODO Auto-generated catch block
					msg("Browsing interrupted.");
				}
			}
			//resets to false so it goes to busy wait for next questionB.
			isAnswered = false;
				
		}
	
	}

	/*
	 * Simulates students leaving school
	 * and in order.
	 */
	private void leavesSchool() {
		while(timer.isAlive()){
		
		}
		try{
			sleep(willSleep);
		} 
		catch (InterruptedException e){
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		msg("is leaving.");
	}
		
}
