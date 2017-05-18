package bankersalgo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import model.Process;

public class SafeState {
	private ArrayList<Integer> avail;
	int maxIteration =0;
	
	public SafeState(ArrayList<Integer> avail){
		this.avail = new ArrayList<Integer>(avail);
		
		System.out.println("Available @ safeState: "  );
	}

	public boolean hasDeadlock( ArrayList<Process> procList , int resSize){
		int counter=0; 
		int procSize = procList.size();
		maxIteration = setMaxIteration(procSize);
		ArrayList<Integer> need;
		ArrayList<Integer> tempAvailable = new ArrayList<>(avail);
		Queue<Process> q = new LinkedList<>(procList);
		
		for (int j=1, check=0; !q.isEmpty() && check<=maxIteration; j=1, check++){
					
			need = q.element().getNeed();
			
			for (; j<resSize; j++){
				
				if (need.get(j) > tempAvailable.get(j)){
					Process temp = q.remove();
					q.add(temp);
					break;
				}
			}
		
			if (j == resSize){
				counter++;
				avail = updateTempAvail(tempAvailable, q.element().getAlloc());
				q.remove();				
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
	
	public int setMaxIteration(int size){
		if (size>0)
			return size+setMaxIteration(--size);
		else
			return size;
	}
	
	public int getMaxIteration(){
		return maxIteration;
	}
}
