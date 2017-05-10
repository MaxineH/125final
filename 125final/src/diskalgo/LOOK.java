package diskalgo;

import java.util.ArrayList;
import java.util.Collections;

import gui.Chart;
import model.DiskAlgo;

public class LOOK extends DiskAlgo {

	private Chart chart;
	private int curr=-1;
	private boolean isRight=false;
	
	public LOOK(int head,int max,Chart chart) {
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
		if (isRight && head!=max)
			return tmp.get(i+1);
		return tmp.get(i-1);
	}
	
	public void execute(int t, int index) {
		if (index!=-1) {
			if (curr!=index) {
				p.put(curr,proctotal);
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