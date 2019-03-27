package javaThreads;

import java.util.concurrent.Semaphore;

//import java.lang.Runnable;
public class Adventurer extends StackUser{
	private String name;						//thread name
	private TheAdventure adventure;				//the adventurer communicate with both clerk and dragon
	private Clerk service;						
	private Dragon battle;
	private Thread player;
	private long time=0;
	private int count;
	private int item[]= new int[3];				//three type of items
	private int magicalItem[]= new int[2];		//two type of magical items
	private String helperArray[]= new String[]{"stone", "ring","necklaces", "magical ring","magical necklace" };
	private int amountMagicalItem=0;
	private int fortuneSize=4;					//the amount of magical items needed
	private static Semaphore mutex= new Semaphore(1);

	public Adventurer(String name, TheAdventure advent, Clerk service, Dragon battle, StackClass stack){
		super(name, stack);
		player = new Thread(this, name);
		this.name = name;
		this.adventure=advent;
		this.service=service;
		this.battle=battle;
		time=System.currentTimeMillis();	//get the birth time
	}
	
	//setters
	public void setItem(int index){item[index]++;}
	
	public void setMagicalItem(int index){magicalItem[index]++;}
	
	public void setAmountMagicalItem(){amountMagicalItem++;}
	
	public void setCounter(int c){
		if(c==0)count=0;
		else count++;
	}
	
	//getters
	public int getAmountMagicalItem(){return amountMagicalItem;}
	
	public int getAmountOfItem(int index){return item[index];}
	
	public int getAmountOfMagicalItem(int index){return magicalItem[index];}
	
	public int getfortuneSize(){return fortuneSize;}
	
	public int getCounter(){return count;}

	public String getNameOfItem(int index){return helperArray[index];}
	
	//public String getName(){return this.name;}
	
	public Thread getThread(){return player;}
	
	public void run(){
		try{
			//all the adventures go off
			msg(" has started his adventure");
			//System.out.println(this.name+ );
			
			while(amountMagicalItem<=fortuneSize){
				
				if(this.isReadyToShop()!=-1){
					System.out.println("do i nmake hewre");
				    //synchronized(adventure){
					mutex.acquire();
						msg(" is walking to magic shop");
						service.callForClerk(this);	//call for clerk
						stack.push(this);		//push adventure in the stack
						//adventure.setNeed_assistance(true);//clerk needed
						adventure.getDragonSemaphore().release();
				    	//adventure.wait();		//they wait before they get an item from dragon
						this.upDateItems(this.isReadyToShop());
						this.amountMagicalItem++;
						adventure.setNeed_assistance(false);//clerk is not needed
				    //}
					mutex.release();

					adventure.setNeed_assistance(false);
					if(this.amountMagicalItem==4)break;	//break if all item are received
					
				}
				//battle the dragon until have enough items
				else{
			
					while(this.isReadyToShop()==-1){
					    	msg("walk to the Dragon cave!");
						mutex.acquire();
						    	//adventure.setAvailable(true);	//signal the dragon
						    	battle.goToCave(this);			//go to cave
						    	//adventure.getdragonResponseMutex().acquire();

						    	adventure.getDragonSemaphore().release();//signal dragon
						    	
						    	//adventure.getadventMutex().acquire();	//the advent wait for the dragon

						    	if(adventure.isItheWinner()){ 
						    		System.out.println(this.player.getName()+" "+ item[0]+ " " + item[1]+ " "+item[2]);
						    		
						    		break;}//go to see the clerk
						    
						    	adventure.setIsInBattle(false);
					    mutex.release();
				    }//end of while loop	
				}//end of else 
			}//end of while loop
			
			int place = this.adventure.numberOfThreads();
			msg(">>> "+ name+ " has collected all required items and finish in place "+ place);
			
			
			if(place==8){
				adventure.setIsGameOver(true);
				msg(" is going to tell dragon and clerk the game is over. "+adventure.isGameOver());
			}
			
			player.join();
			if(this.player.isAlive())System.out.println(name+" not alive");
				
		}catch(InterruptedException ie){
			msg(name+"the adventure had died. maybe the the dragon");
		}
	}//end of run function
	
	public void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
	
	private int isReadyToShop(){
		if(item[0]!=0 && item[1]!=0)return 0;
		else if(item[0]!=0 && item[2]!= 0)return 1;
		else if(item[1]!=0 && item[2]!=0)return 2;
		else return -1;
	}
	
	private void upDateItems(int caseNum){
		if(caseNum ==0){
			magicalItem[0]++;
			item[0]--;
			item[1]--;
			service.msg(" "+name+ " gives "+helperArray[0]+" and "+
					helperArray[1]+" to the clerk to receive a create magical item " +helperArray[3]);
		}
		else if(caseNum==1){
			magicalItem[1]++;
			item[0]--;
			item[2]--;
			service.msg(" "+name+ " gives "+helperArray[0]+" and "+
					helperArray[2]+" to the clerk to receive a create magical item " +helperArray[4]);
		}
		else{
			magicalItem[1]++;
			item[1]--;
			item[2]--;
			service.msg(" "+name+ " gives "+helperArray[1]+" and "+
					helperArray[2]+" to the clerk to receive a create magical item " +helperArray[4]);
		}
	}//end upDate 
}
