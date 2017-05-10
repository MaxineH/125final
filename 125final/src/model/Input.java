package model;

import gui.Chart;

import java.util.ArrayList;

import bankersalgo.BankersAlgorithm;
import cpuscheduling.FCFS;
import cpuscheduling.NPPrio;
import cpuscheduling.PreemptPrio;
import cpuscheduling.RR;
import cpuscheduling.SJF;
import cpuscheduling.SRTF;
import diskalgo.CLOOK;
import diskalgo.CSCAN;
import diskalgo.FIFO;
import diskalgo.LOOK;
import diskalgo.SCAN;
import diskalgo.SSTF;

public class Input {

	private int resourceNum;
	private int maxCylinder;
	private int headCylinder;
	private int[] quantum;
	private Algo[] algo;
	private ArrayList<Integer> available;
	private ArrayList<Process> p;
	
	public void setSize(int size) {
		quantum=new int[size];
		algo=new Algo[size];
	}

	public void setAvailable(ArrayList<Integer> available) {
		this.available = available;
	}

	public void setAlgo(Algo[] algo) {
		this.algo = algo;
	}
	
	public void setQuantum(int[] quantum) {
		this.quantum = quantum;
	}
	
	public void setHeadCylinder(int headCylinder) {
		this.headCylinder = headCylinder;
	}

	public void setMaxCylinder(int maxCylinder) {
		this.maxCylinder = maxCylinder;
	}

	public void setResourceNum(int resourceNum) {
		this.resourceNum = resourceNum;
	}
	
	public void setProcess(ArrayList<Process> p) {
		this.p=p;
	}
	
	public int getSize() {
		return quantum.length;
	}
	
	public int[] getQuantum() {
		return quantum;
	}

	public int getHeadCylinder() {
		return headCylinder;
	}

	public int getMaxCylinder() {
		return maxCylinder;
	}

	public int getResourceNum() {
		return resourceNum;
	}
	
	public ArrayList<Process> getProcess() {
		return p;
	}
	
	public ArrayList<Integer> getAvailable() {
		return available;
	}
	
	public SchedulingAlgo getCPUSched(int index,Chart chart) {
		String tmp=algo[index].getCPU();
		if (tmp.contains("Come"))
			return new FCFS(index,chart,p.size());
		else if (tmp.contains("Job"))
			return new SJF(index,chart,p.size());
		else if (tmp.contains("Remaining"))
			return new SRTF(index,chart,p.size());
		else if (tmp.contains("Preemptive"))
			return new PreemptPrio(index,chart,p.size());
		else if (tmp.contains("Priority"))
			return new NPPrio(index,chart,p.size());
		return new RR(index,chart,p.size(),quantum[index]);
	}
	
	public DiskAlgo getDiskAlgo(int index,Chart chart) {
		String tmp=algo[index].getDisk();
		if (tmp.contains("FCFS"))
			return new FIFO(headCylinder,maxCylinder,chart);
		else if (tmp.contains("SSTF"))
			return new SSTF(headCylinder,maxCylinder,chart);
		else if (tmp.contains("C-SCAN"))
			return new CSCAN(headCylinder,maxCylinder,chart);
		else if (tmp.contains("C-LOOK"))
			return new CLOOK(headCylinder,maxCylinder,chart);
		else if (tmp.contains("SCAN"))
			return new SCAN(headCylinder,maxCylinder,chart);
		return new LOOK(headCylinder,maxCylinder,chart);
	}
	
	public BankersAlgorithm getBankers(int index,Chart chart) {
		return new BankersAlgorithm(index,chart,p,available,resourceNum
				,algo[index].getBankers());
	}
}