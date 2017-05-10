package model;

import java.util.ArrayList;
import model.Process;

public class SchedulingAlgo {
	
	protected ArrayList<Process> readyQ;
	private float avgResponseTime=0, avgWaitingTime=0, avgTurnaroundTime=0;	
	protected int size;
	protected boolean hasReleased;
	protected Process holdProc;
	protected boolean done= false;
	protected int cs =0 ;
	protected int currProc=-1;
	
	public SchedulingAlgo(int size){
		this.size = size;
	}
	
	public void setWaitingTime(int algo){
		int turnaroundTime;
		
		for (int i=0; i<readyQ.size(); i++){
			turnaroundTime = readyQ.get(i).getCompletionTime(algo)- readyQ.get(i).getReadyTime(algo);
			readyQ.get(i).setTurnaroundTime(turnaroundTime, algo);
		}
	}
	
	public void setTurnaroundTime(int algo){
		int turnaroundTime;
		
		for (int i=0; i<readyQ.size(); i++){
			turnaroundTime = readyQ.get(i).getCompletionTime(algo)- readyQ.get(i).getReadyTime(algo);
			readyQ.get(i).setTurnaroundTime(turnaroundTime, algo);
		}
	}
	
	public void setAVGResponseTime(int algo){
		for (int i=0; i<readyQ.size(); i++){
			avgResponseTime+=readyQ.get(i).getResponseTime(algo);
		}
		avgResponseTime/=readyQ.size();
	}
	
	public void setAVGTurnaroundTime(int algo){
		for (int i=0; i<readyQ.size(); i++){
			avgTurnaroundTime+=readyQ.get(i).getTurnaroundTime(algo);
		}
		avgTurnaroundTime/=readyQ.size();
	}

	public void setAVGWaitingTime(int algo){
		for (int i=0; i<readyQ.size(); i++){
			avgWaitingTime+=readyQ.get(i).getWaitingTime(algo);
		}
		avgWaitingTime/=readyQ.size();
	}
	
	public void incCS(){
		cs++;
	}
	
	public void addNewProc(ArrayList<Process> proc){
		readyQ.addAll(new ArrayList<Process>(proc));
	}

	public void execute(int t){
	}
	
	public boolean hasReleased(){
		return hasReleased;
	}
	
	public float getAVGResponseTime(){
		return avgResponseTime;
	}
	
	public Process getReleased(){
		return holdProc;
	}
	
	public float getAVGWaitTime(){
		return avgWaitingTime;
	}
	
	public float getAVGTurnaroundTime(){
		return avgTurnaroundTime;
	}
	
	public int getContextSwitch(){
		return cs;
	}
	
	public boolean isDone(){
		return done;
	}
	
	public void set(){
	}
	
	public void setCurrProc(int procID) {
		currProc = procID;
		if (currProc>0)
			System.out.println("CURR="+currProc+" ID="+readyQ.get(currProc).getId());
	}
	
	public int getCurrProc() {
		if (currProc>-1) {
			System.out.println("IDDD="+readyQ.get(currProc).getId());
			return readyQ.get(currProc).getId();
		}
		return currProc;
	}
}
