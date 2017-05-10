package diskalgo;

import gui.Chart;

import java.util.ArrayList;
import java.util.Collections;

import model.DiskAlgo;

public class SSTF extends DiskAlgo {

	private Chart chart;
	private int curr=-1;
	
	public SSTF(int head,int max,Chart chart) {
		super(max,head);
		this.chart=chart;
	}
	
	private int getNext(int index) {
		ArrayList<Integer> tmp=list.get(index);
		if ((curr==-1 || curr!=index) && tmp.indexOf(head)!=-1) {
			return head;
		}
		else {
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
			return right;
		return left;
	}

	public void execute(int t, int index) {
		//index is process id
		if (index!=-1) {
			if (curr!=index && curr!=-1) {
				p.put(curr, proctotal);
			}
			
			int prev=head;
			int next=getNext(index);
			int count=getDifference(prev,next);

			proctotal+=count;
			total+=count;
			
			list.get(index).remove(list.get(index).indexOf(next));
			chart.drawGraph(t, next);
			head=next;
			curr=index;
		}
	}
}