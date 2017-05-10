package cpuscheduling;

import gui.Chart;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import utils.Utils;
import model.SchedulingAlgo;
import model.Process;

public class NPPrio extends SchedulingAlgo{
	
	int choice;
	private Chart chart;
	int curr = -1;
	int counter = 0;
	int readyQSize=0;
	boolean addedNewProc=false;
	
	public NPPrio(int choice, Chart chart, int size){
		super(size);
		this.chart = chart;
		this.choice= choice;
		readyQ = new ArrayList<Process>();
	}

	public void execute(int t){
		int burstTime, remTime, responseTime;
//		String currProcess;
		
		System.out.println("@ execution");

		for (int i=readyQSize; i<readyQ.size(); i++){
				chart.addReadyQueue(Utils.getColor(readyQ.get(i).getId()), t);
				burstTime = readyQ.get(i).getBurstTime();
				readyQ.get(i).setRemainingTime(burstTime,choice);
				readyQ.get(i).setResponseTime(-1, choice);
				readyQ.get(i).setReadyTime(t,choice);
		}
		
		readyQSize = readyQ.size();
		if (curr==-1) curr = getMostPriority();
		
		if (curr<readyQSize && curr!=-1){ 
			if (readyQ.get(curr).getResponseTime(choice)==-1){ 	//response time
				responseTime = t-readyQ.get(curr).getReadyTime(choice);
				readyQ.get(curr).setResponseTime(responseTime, choice);
				readyQ.get(curr).setWaitingTime(responseTime, choice);
			}
		
//			currProcess = "p"  + readyQ.get(curr).getId();
			if (currProc!=curr)
				chart.addBox(t, readyQ.get(curr).getColor(),true); //readyQ.get(curr).getColor());
			else
				chart.addBox(t, readyQ.get(curr).getColor(),false);
			setCurrProc(curr);
			
			remTime = readyQ.get(curr).getRemainingTime(choice);
			readyQ.get(curr).setRemainingTime(--remTime,choice);

			if (readyQ.get(curr).getRemainingTime(choice)==0){
				readyQ.get(curr).setCompletionTime(t+1,choice);
				setReleased(readyQ.get(curr));
				curr = -1;
				counter++;
			}
		}else{ //idle time
			chart.addBox(t,Color.LIGHT_GRAY,true);//Color.WHITE);
			curr=-1;
			setCurrProc(-1);
		}
		
		if (counter==size)	done=true;
	}
	
	private int getMostPriority () {
		int i;
		Collections.sort(readyQ, Process.priorityNumComparator);
		for ( i = 0 ; i < readyQ.size() ; i++ ) {
			if ( readyQ.get(i).getRemainingTime(choice) != 0) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean hasReleased(){
		return hasReleased;
	}
	
	public Process getReleased(){
		hasReleased = false;
		return holdProc;
	}
	
	private void setReleased(Process p){
		System.out.println("HAS RELEASED IS TRUE  from" +p.getId());
		hasReleased = true;
		holdProc= p;
	}
	
	public void set(){
		setTurnaroundTime(choice);
		setAVGResponseTime(choice);
		setAVGWaitingTime(choice);
		setAVGTurnaroundTime(choice);
		getSummary();
	}
	
	public void getSummary(){
		System.out.println("First Come First Serve Scheduling Summary: ");
	
		Collections.sort(readyQ, Process.idComparator);
		
		for (int i=0; i<readyQ.size(); i++){
			System.out.println("Process" +(i+1));
			System.out.println("Response time: "+ readyQ.get(i).getResponseTime(choice));
			System.out.println("Wait time: "+ readyQ.get(i).getWaitingTime(choice));
			System.out.println("Turnaround time: "+ readyQ.get(i).getTurnaroundTime(choice));
			System.out.println("---");
		}
		
		System.out.println("AVG Response Time: "+getAVGResponseTime());
		System.out.println("AVG Waiting Time: "+getAVGWaitTime());
		System.out.println("AVG Turnaround Time: "+getAVGTurnaroundTime());
		System.out.println("Context Switch: "+getContextSwitch());
	}
}
