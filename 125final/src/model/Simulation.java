package model;

import gui.Chart;
import gui.SimulationPanel;

import java.util.ArrayList;

import bankersalgo.BankersAlgorithm;
import bankersalgo.SafeState;

public class Simulation extends Thread {

	private ArrayList<Process> process;
	private ArrayList<Integer> available;
	
	private int resourceNum;
	private int simCount;
	private int delay=1000;
	private boolean running=true;
	private int maxIteration=0;
	
	private SchedulingAlgo[] cpu;
	private BankersAlgorithm[] banker;
	private DiskAlgo[] disk;
	private Chart[] chart;
	
	public Simulation(Input input,SimulationPanel simPanel) {
		simCount=input.getSize();
		cpu=new SchedulingAlgo[simCount];
		banker=new BankersAlgorithm[simCount];
		disk=new DiskAlgo[simCount];
		chart=new Chart[simCount];

		resourceNum=input.getResourceNum();
		available=input.getAvailable();
		process=input.getProcess();
		simPanel.setLegend(process.size());
		for (int i=0; i<simCount; i++) {
			chart[i]=new Chart(i+1,input.getHeadCylinder(),input.getMaxCylinder());
			banker[i]=input.getBankers(i,chart[i]);
			cpu[i]=input.getCPUSched(i,chart[i]);
			disk[i]=input.getDiskAlgo(i,chart[i]);
			simPanel.addChart(chart[i]);
		}
		init();
	}
	
	private void init() {
		SafeState deadlock=new SafeState(available);
		
		while (deadlock.hasDeadlock(process, resourceNum)) {
			deadlock.pushSafeState(available);
			available=deadlock.getAvailable();
		}
		maxIteration = deadlock.getMaxIteration();
		start();
	}
	
	public void pause() {
		if (running) {
			delay=0;
			interrupt();
		}
		else {
			running=true;
		}
	}
	
	public void run() {
		ArrayList<Process> tempProc;
		int t=0;
		int done=0;

		while (running) {
			if (!isInterrupted()) {
				for (int i=0; i<simCount; i++) {
					tempProc = banker[i].getProcess(t, maxIteration);
					
					if (tempProc.size()>0) {
						cpu[i].addNewProc(tempProc);
						disk[i].addList(tempProc);
					}
					
					cpu[i].execute(t);
					disk[i].execute(t+1, cpu[i].getCurrProc());
					
					if (cpu[i].hasReleased()) {
						banker[i].releaseRes(cpu[i].getReleased());
					}
					if  (cpu[i].isDone()) {
						cpu[i].set();
						done++;
					}
					
					if (done==simCount) {
						running=false;
						break;
					}
					banker[i].resetAllocated();
				}
				t++;
			}
			
			try {
				Thread.sleep(delay);
				while (!running) {
					interrupt();
				}
			} catch(Exception e) {}
		}
	}
}