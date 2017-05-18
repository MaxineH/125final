package bankersalgo;

import gui.Chart;

import java.util.ArrayList;
import java.util.Collections;

import model.Process;

public class BankersAlgorithm {
	private ArrayList<Process> jobQ;
	private ArrayList<Integer> available;
	private ArrayList<Process> availableProc;
	
	private Chart chart;
	 
	private String algoType; //if continuous or what
	private int resSize;
	private int choice;
	
	private static int curr=0;
	
	public BankersAlgorithm(int choice, Chart chart, ArrayList<Process> jobQ, ArrayList<Integer> available, int resSize,
			String algoType ){

		this.choice = choice;
		this.chart = chart;
		this.jobQ = new ArrayList<Process>(jobQ);
		this.available=new ArrayList<Integer>(available);
		this.algoType = algoType;
		this.resSize = resSize;
		availableProc=new ArrayList<Process>();
		
		System.out.println("Init algo type: "+algoType);
		
		chart.initAvailable(available);
		Collections.sort(jobQ, Process.arrivalTimeComparator);
	}

	public ArrayList<Process> getProcess(int t, int max) {
		System.out.println("@ banker's algo");

		if (algoType.contains("TDTD")){
			startTDTD(t, max);
		}

		else if (algoType.contains("TDDT")){
			startTDDT(t, max);
		}

		for (int i=0; i<availableProc.size(); i++){
			System.out.println("Return proc id: "+availableProc.get(i).getId() );
		}
		
		return availableProc;
	}

	public void startTDTD(int t, int max){
	
		ArrayList<Integer> need;
		int temp= (algoType.equalsIgnoreCase("reset"))? 0:curr;
		int count=0;
		
		for (int k=1; temp<jobQ.size() && t>=jobQ.get(temp).getArrivalTime() && count<=max; 
				k=1, temp++, count++){
			
			for (; k<resSize && (!jobQ.get(temp).isAllocated(choice)); k++){
				need = jobQ.get(temp).getNeed();
				if (need.get(k) > available.get(k))
					break;
			}
		
			if (k==resSize && !jobQ.get(temp).isAllocated(choice)){
				jobQ.get(temp).setIsAllocated(choice, true);
				allocateRes(jobQ.get(temp));
				availableProc.add(jobQ.get(temp));
				curr=temp;
				temp = algoType.equalsIgnoreCase("reset")? -1:temp;
			}
			if (temp==jobQ.size()-1) temp = -1;
		}
	}
	
	public void startTDDT(int t, int max) {
		int temp = curr;
		boolean down=true;
		ArrayList<Integer> need;
		int counter=0;
		
		try{
		for ( int k=1; temp<jobQ.size() && t>=jobQ.get(temp).getArrivalTime() && counter<=max; k=1, counter++){
			
			for ( ; k<resSize && !jobQ.get(temp).isAllocated(choice); k++){
				need = jobQ.get(temp).getNeed();
				
				System.out.println("NOW: "+need.get(k) + " avail: " +available.get(k) );
				if (need.get(k) > available.get(k)) {
					break;
				}
			}
		
			if (k==resSize && !jobQ.get(temp).isAllocated(choice)){
				jobQ.get(temp).setIsAllocated(choice, true);
				allocateRes(jobQ.get(temp));
				availableProc.add(jobQ.get(temp));
				curr=temp;
			}
		
			if (temp == jobQ.size()-1){ //if last element na
				down=false;
			}
			else if (temp==0 && down==false){
				down=true;
			}
			temp = (down)? ++temp:--temp; 
		}
		
		}catch(ArrayIndexOutOfBoundsException e){	}
	}
	
	public void resetAllocated(){
		availableProc.removeAll(availableProc);
	}
	
	//release resources, a process is done
	public void releaseRes(Process process){
		//update available
		ArrayList<Integer> max;
		max = process.getMax();
		int temp;
		
		for (int j=1; j<resSize; j++){
			temp= available.get(j);
			available.set(j, temp+max.get(j));
		
			if (temp!=available.get(j))
				System.out.println("j="+available.get(j));
				chart.showAvailable(j, Integer.toString(available.get(j)));
			System.out.println("");
		}
	}
	
	//subtract resources, it is being used
	public void allocateRes(Process process){
		//show max for need column
		//show all 0 for need

		ArrayList<Integer> need;
		int temp;
		need = process.getNeed();
		
		//System.out.print("Used proc: "+ jobQ.get(i).getId() + "|| "+id+"Available now: ");
		//System.out.println("[used] Available now: ");
		for (int j=1; j<resSize; j++){
			temp= available.get(j);
			available.set(j, temp - need.get(j));
			//System.out.print(available.get(j));
		
			if (temp!=available.get(j)){
				System.out.println("j="+available.get(j));
				chart.showAvailable(j, Integer.toString(available.get(j)));
			}
		}
		//System.out.println("");
	}
}
