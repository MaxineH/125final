package diskalgo;

import gui.Chart;

import java.util.ArrayList;
import java.util.Collections;

import model.DiskAlgo;
import model.Process;

public class SCAN extends DiskAlgo {

	private Chart chart;
	private int curr=-1;
	private boolean isRight=false;
	
	public SCAN(int head,int max,Chart chart) {
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
		if (isRight && head!=max)
			return tmp.get(i+1);
		return tmp.get(i-1);
	}
	
	public void execute(int t, int index) {
		//index is process id
		if (index!=-1) {
			if (curr!=index) {
				p.put(curr, proctotal);
				if (getDifference(head,0)>getDifference(head,max))
					isRight=true;
				else isRight=false;
			}
			
			int prev=head;
			int next=getNext(index);
			int count=getDifference(prev,next);
			
			proctotal+=count;
			total+=count;
			
			list.get(index).remove(list.get(index).indexOf(head));
			list.get(index).remove(list.get(index).indexOf(next));
			chart.drawGraph(t,next);
			head=next;
			curr=index;
		}
	}
}