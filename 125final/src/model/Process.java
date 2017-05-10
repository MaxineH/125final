package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;

public class Process {
	private int[] responseTime = new int[2]; 
	private int[] waitingTime = new int [2];
	private int[] turnAroundTime = new int[2];
	private int[] completionTime = new int[2];
	private int[] remainingTime = new int[2];
	private int[] readyTime = new int[2]; //time it arrived at ready queue
	private boolean[] isAllocated = {false, false};
	
	private ArrayList<Integer> max;
	private ArrayList<Integer> need;
	private ArrayList<Integer> alloc;
	private ArrayList<Integer> cylinder;
	private Color color;

	private int id, arrivalTime, priorityNum;
	
	public Process(int id, int arrivalTime, int priorityNum, ArrayList<Integer> max, ArrayList<Integer> alloc, ArrayList<Integer> cylinder){
		this.id= id;
		this.arrivalTime = arrivalTime;
		this.priorityNum= priorityNum;
		this.max= max;
		this.alloc = alloc;
		this.cylinder = cylinder;
		
		need = new ArrayList<Integer>();
		
		for (int i=0; i<max	.size(); i++){
			need.add(max.get(i) - alloc.get(i));
		}
	}
	
	public Process (int id, int arrivalTime, ArrayList<Integer> max, ArrayList<Integer> alloc, ArrayList<Integer> cylinder){
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.max= max;
		this.alloc = alloc;
		this.cylinder = cylinder;
		
		need = new ArrayList<Integer>();

		for (int i=0; i<max	.size(); i++){
			need.add(max.get(i) - alloc.get(i));
		}
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setCylinder(ArrayList<Integer> cylinder) {
		this.cylinder = cylinder;
	}
	
	public Color getColor () {
		return color;
	}
	
	public int getId () {
		return id;
	}
	
	public int getArrivalTime () {
		return arrivalTime;
	}
	
	public int getBurstTime () {
		return max.get(0);
	}

	public int getPriorityNum () {
		return priorityNum;
	}
	
	public ArrayList<Integer> getCylinder() {
		return cylinder;
	}
	
	public int getResponseTime(int i){
		return responseTime[i];
	}
	
	public int getWaitingTime(int i){
		return waitingTime[i];
	}
	
	public int getTurnaroundTime(int i){
		return turnAroundTime[i];
	}

	public void setResponseTime(int responseTime, int i){
		this.responseTime[i] = responseTime;
	}
	
	public void setWaitingTime(int waitingTime, int i){
		this.waitingTime[i] = waitingTime;
	}
	
	public void setTurnaroundTime(int turnAroundTime, int i){
		this.turnAroundTime[i] = turnAroundTime;
	}
	
	public void setCompletionTime(int completionTime, int i){
		this.completionTime[i] = completionTime;
	}
	
	public int getCompletionTime(int i){
		return completionTime[i];
	}
	
	public int getRemainingTime(int i){
		return remainingTime[i];
	}
	
	public void setRemainingTime(int remainingTime, int i){
		this.remainingTime[i] = remainingTime;
	}
	
	public void setReadyTime(int readyTime, int i){
		this.readyTime[i]= readyTime;
	}
	
	public int getReadyTime(int i){
		return readyTime[i];
	}
	
	public ArrayList<Integer> getAlloc(){
		return alloc;
	}
	
	public ArrayList<Integer> getMax(){
		return max;
	}
	
	public ArrayList<Integer> getNeed(){
		return need;
	}
	
	public void setIsAllocated(int i, boolean isAllocated){
		this.isAllocated[i] = isAllocated;
	}
	
	public boolean isAllocated(int i){
		return isAllocated[i];
	}

	/*Comparator for sorting the list by start time*/
    public static Comparator<Process> idComparator = new Comparator<Process>() {

    	public int compare(Process p1, Process p2) {
    		
    		int process1 = p1.getId();
    		int process2 = p2.getId();

    		/*For ascending order*/
    		return process1-process2;
    }};
    
	/*Comparator for sorting the list by start time*/
    public static Comparator<Process> arrivalTimeComparator = new Comparator<Process>() {

    	public int compare(Process p1, Process p2) {
    		
    		int process1 = p1.getArrivalTime();
    		int process2 = p2.getArrivalTime();

    		/*For ascending order*/
    		return process1-process2;
    }};
    
    /*Comparator for sorting the list by burst time*/
    public static Comparator<Process> burstTimeComparator = new Comparator<Process>() {

    	public int compare(Process p1, Process p2) {
    		
    		int process1 = p1.getBurstTime();
    		int process2 = p2.getBurstTime();

    		/*For ascending order*/
    		return process1-process2;
    }};
   
    /*Comparator for sorting the list by priority*/
    public static Comparator<Process> priorityNumComparator = new Comparator<Process>() {

    	public int compare(Process p1, Process p2) {
    		
    		int process1 = p1.getPriorityNum();
    		int process2 = p2.getPriorityNum();

    		/*For ascending order*/
    		return process1-process2;
    }};
}