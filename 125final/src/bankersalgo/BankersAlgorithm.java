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

	public ArrayList<Process> getProcess(int t) {
		System.out.println("@ banker's algo");

		if (algoType.contains("TDTD")){
			startTDTD(t, false);
		}

		else if (algoType.contains("TDDT")){
			startTDDT(t);
		}

		for (int i=0; i<availableProc.size(); i++){
			System.out.println("Return proc id: "+availableProc.get(i).getId() );
		}
		
		return availableProc;
	}

	public void startTDTD(int t, boolean recur){
	
		ArrayList<Integer> need;
		int temp= (recur || algoType.contains("reset"))? 0:curr;
		
		for (int count=0; temp<jobQ.size() && t>=jobQ.get(temp).getArrivalTime(); count=0, temp++){
			
			for (int k=1; k<resSize && 	(!jobQ.get(temp).isAllocated(choice)); k++){
				need = jobQ.get(temp).getNeed();
				
				if (need.get(k) <= available.get(k)){
					count++;
				}
				else
					break;
			}
			
			if (count==resSize-1 && !jobQ.get(temp).isAllocated(choice)){
				
				jobQ.get(temp).setIsAllocated(choice, true);
				allocateRes(jobQ.get(temp));
		
				availableProc.add(jobQ.get(temp));
				curr=temp;
				
				temp = algoType.contains("reset")? -1:temp;
			}
		
			if (temp == jobQ.size()-1 && algoType.contains("continuous") && recur==false){
				startTDTD(t, true);
			}
		}
	}
	
	public void startTDDT(int t) {
		int temp = curr;
		boolean down=true, flag=false;
		ArrayList<Integer> need;
		
		try{
		for (int count=0; temp<jobQ.size() && t>=jobQ.get(temp).getArrivalTime(); count=0, temp++){
			
			for (int k=1; k<resSize && !jobQ.get(temp).isAllocated(choice); k++){
				need = jobQ.get(temp).getNeed();
				if (need.get(k) <= available.get(k)) {
					count++;
				}
				else
					break;
			}
		
			if (count==resSize-1 && !jobQ.get(temp).isAllocated(choice)){
				jobQ.get(temp).setIsAllocated(choice, true);
				allocateRes(jobQ.get(temp));
				availableProc.add(jobQ.get(temp));
				curr=temp;
			}
		
			if (temp == jobQ.size()-1 && !flag){ //if last element na
				down=false;
				flag = true;
			}
			else if (temp==0 && down==false && !flag){
				down=true;
				flag=true;
			}
			temp = (down)? temp:temp-2; 
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
