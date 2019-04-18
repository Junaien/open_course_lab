package javaThreads;
import java.util.Random;
import java.lang.Runnable;
public class Adventurer implements Runnable {
	private String name;						//thread name
	private int item[]= new int[3];				//three type of items
	private int magicalItem[]= new int[2];		//two type of magical items
	private String helperArray[]= new String[]{"stone", "ring","necklaces", "magical ring","magical necklace" };
	private int amountMagicalItem=0;
	private int fortuneSize=4;					//the amount of magical items needed
	private TheAdventure adventure;
	private Clerk service;
	private Random rand = new Random(System.currentTimeMillis());
	
	public Adventurer(String name, TheAdventure adventure, Clerk service){
		this.name = name;
		this.adventure=adventure;
		this.service=service;
	}
	
	//their is a very small delay to get an item
	public int findItem() throws InterruptedException
	{
		int ranItem = Math.abs(this.rand.nextInt(3));
		item[ranItem]++;		//he get his item
		Thread.sleep(1000);
		return ranItem;
		
	}
	
	public void run(){
		try{
			//all the adventures go off
			this.adventure.getReadToStart();
			System.out.println(this.name+ " has started his adventure");
			
			while(amountMagicalItem<=fortuneSize){
				
				//first search for item
				int randItem = this.findItem();
				System.out.println(this.name+ " has found a item "+this.helperArray[randItem]);
				
				if(this.isReadyToShop()==0 || this.isReadyToShop()==1 || this.isReadyToShop()==2){
					//System.out.println(this.name+" has enough to create magical item go to the shop");
					long time = this.service.getItem();
					System.out.println(this.name+" has enough to create item go to shop and wait for the clerk "+ (double)time/1000+ " seconds " );
					this.upDateItems(this.isReadyToShop());
					this.amountMagicalItem++;
				}
				else{
					/*
					//go to battle the dragon
				    synchronized (this) {
				    	System.out.println(this.name+" go to battle the dragon");
				    	this.wait();
				    }
				    */
				}
				//has enough item
			}
			
			
			int place = this.adventure.RequiredItem();
			System.out.println(">>> "+ name+ " finsih in position "+ place);
				
		}catch(InterruptedException ie){
			System.out.println(name+"the adventure had died. maybe the the dragon");
		}
	}//end of run function
	
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
			System.out.println(name+ " gives "+helperArray[0]+" and "+
					helperArray[1]+" to create magical item " +helperArray[3]);
		}
		else if(caseNum==1){
			magicalItem[1]++;
			item[0]--;
			item[2]--;
			System.out.println(name+ " gives "+helperArray[0]+" and "+
					helperArray[2]+" to create magical item " +helperArray[4]);
		}
		else{
			magicalItem[1]++;
			item[1]--;
			item[2]--;
			System.out.println(name+ " gives "+helperArray[1]+" and "+
					helperArray[2]+" to create magical item " +helperArray[4]);
		}
	}//end upDate 
	
	public synchronized void ReadToStart()throws InterruptedException
	{
		this.wait();
		System.out.println("your finish");
		
	
	}
	
}
