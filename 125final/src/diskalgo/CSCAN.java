package diskalgo;

import java.util.ArrayList;
import java.util.Collections;

import gui.Chart;
import model.DiskAlgo;
import model.Process;

public class CSCAN extends DiskAlgo {

	private Chart chart;
	private int curr=-1;
	private boolean isRight=false;
	
	public CSCAN(int head,int max,Chart chart) {
		super(max,head);
		this.chart=chart;
	}
	
	public void addList(ArrayList<Process> p) {
		for (Process i: p) {
			list.put(i.getId(), new ArrayList<Integer>(i.getCylinder()));
		}
		p.addAll(new ArrayList<Process>(p));
		
		for (int i=0; i<list.size(); i++) {
			if (list.containsKey(i)) {
				list.get(i).add(0);
				list.get(i).add(max);
			}
		}
	}
	
	private int getNext(int index) {
		ArrayList<Integer> tmp=list.get(index);
		if ((curr==-1 || curr!=index) && tmp.indexOf(head)!=-1)
			return head;
		else {
			tmp.add(head);
			Collections.sort(tmp);
		}
		
		int i=tmp.indexOf(head);
		if (isRight && head!=max) return tmp.get(i+1);
		return tmp.get(i-1);
	}
	
	private void setDirection(int index) {
		ArrayList<Integer> tmp=list.get(index);
		if ((curr==-1 || curr!=index) && tmp.indexOf(head)==-1) {
			tmp.add(head);
			Collections.sort(tmp);
		}
		
		int i=tmp.indexOf(head);
		int right,left;
		
		if (i!=0) left=tmp.get(i-1);
		else left=tmp.get(tmp.size()-1);
		
		if (i!=tmp.size()-1) right=tmp.get(i+1);
		else right=tmp.get(0);
		
		tmp.remove(i);
		if (getDifference(head,left)>getDifference(right,head))
			isRight=false;
		isRight=true;
	}
	
	public void execute(int t, int index) {
		//index is process id
		if (index!=-1) {
			if (curr!=index) {
				setDirection(index);
				p.put(curr, proctotal);
			}
			
			int prev=head;
			int next=getNext(index);
			int count=getDifference(prev,next);
			
			proctotal+=count;
			total+=count;
			
			list.get(index).remove(list.get(index).indexOf(head));
			list.get(index).remove(list.get(index).indexOf(next));
			chart.drawGraph(t,next);
			if (next==0) {
				next=max;
				chart.drawGraph(t, next);
				list.get(index).remove(list.get(index).indexOf(max));
			}
			else if (next==max) {
				next=0;
				chart.drawGraph(t,next);
				list.get(index).remove(list.get(index).indexOf(0));
			}
			head=next;
			curr=index;
		}
	}
}