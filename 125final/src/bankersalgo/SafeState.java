package bankersalgo;

import java.util.ArrayList;
import java.util.Random;

import model.Process;

public class SafeState {
	private ArrayList<Integer> avail;

	public SafeState(ArrayList<Integer> avail){
		this.avail = new ArrayList<Integer>(avail);
		
		System.out.println("Available @ safeState: "  );
		for (int i=0; i<avail.size(); i++){
			System.out.print(avail.get(i) + " ");
		}
	}

	public boolean hasDeadlock( ArrayList<Process> procList , int resSize){
		int counter=0; 
		int procSize = procList.size();
		int flag = 0;
		boolean[] allocated = new boolean[procSize];
		ArrayList<Integer> need;
		ArrayList<Integer> tempAvailable = avail;
		
		System.out.print("Avail @ hasdeadlock ");
		for (int i=0; i<avail.size(); i++){
			System.out.print(avail.get(i) + " ");
		}
		
		System.out.println("Checking state...");
		
		for (int i=0; i<procSize; i++){
			allocated[i]=false;
		}	
	
		for (int i=0, check=0; counter<procSize && flag<2; i++, check=0){
			
			if (i>=procSize){ 
				i=0;
				flag++;
			}
			
			for (int j=1; j<resSize && allocated[i]== false; j++){
				need=procList.get(i).getNeed();
				
				System.out.println("need "+ i+ ": "+need + " Available: "+tempAvailable.get(j));
				if (need.get(j) > tempAvailable.get(j))
					break;
				check++;
			}

			if (check == resSize-1){
				counter++;
				avail = updateTempAvail(tempAvailable, procList.get(i).getAlloc());
				allocated[i] = true;				
			}
		}
		return (counter<procSize)? true:false;
	}

	public ArrayList<Integer> updateTempAvail(ArrayList<Integer> avail, ArrayList<Integer> alloc){
		for (int i=1; i<avail.size(); i++){
			avail.set(i, avail.get(i)+ alloc.get(i));
		}
		return avail;
	}	

	public void pushSafeState(ArrayList<Integer> available){
		ArrayList<Integer> avail = available;
		Random rand = new Random();
		int r;

		for (int i=1; i<available.size(); i++){
			r = rand.nextInt(4)+2; //minimum is 2, maximum = 5
			avail.set(i, r+avail.get(i));
		}
		setAvailable(avail);
	}
	
	public ArrayList<Integer> getAvailable(){
		return avail;
	}
	
	private void setAvailable(ArrayList<Integer> avail){
		this.avail = avail;
	
	}
}
