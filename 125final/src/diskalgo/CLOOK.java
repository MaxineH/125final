package diskalgo;

import gui.Chart;

import java.util.ArrayList;
import java.util.Collections;

import model.DiskAlgo;

public class CLOOK extends DiskAlgo {

	private Chart chart;
	private int curr=-1;
	private boolean isRight=false;
	
	public CLOOK(int head,int max,Chart chart) {
		super(max,head);
		this.chart=chart;
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
		if (curr!=index) {
			if (getDifference(head,tmp.get(0))>getDifference(head,tmp.get(tmp.size()-1)))
				isRight=true;
			else isRight=false;
		}
		if (isRight && head!=max)
			return tmp.get(i+1);
		return tmp.get(i-1);
	}
	
	public void execute(int t, int index) {
		if (index!=-1) {
			if (curr!=index) {
				p.put(curr,proctotal);
			}
			
			int prev=head;
			int next=getNext(index);
			int count=getDifference(prev,next);
			
			proctotal+=count;
			total+=count;
			
			list.get(index).remove(list.get(index).indexOf(head));
			list.get(index).remove(list.get(index).indexOf(next));
			chart.drawGraph(t,next);
			if (next==list.get(index).get(0)) {
				next=list.get(index).get(list.get(index).size()-1);
				chart.drawGraph(t,next);
				list.get(index).remove(list.get(index).indexOf(next));
			}
			else if (next==list.get(index).get(list.get(index).size()-1)) {
				next=list.get(index).get(0);
				chart.drawGraph(t,next);
				list.get(index).remove(list.get(index).indexOf(next));
			}
			head=next;
			curr=index;
		}
	}
}