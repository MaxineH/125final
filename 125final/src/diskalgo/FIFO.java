package diskalgo;

import gui.Chart;
import model.DiskAlgo;

public class FIFO extends DiskAlgo {

	private Chart chart;
	private int curr=-1;
	
	public FIFO(int head,int max,Chart chart) {
		super(max,head);
		this.chart=chart;
	}
	
	public void execute(int t, int index) {
		//index is process id
		if (index>0 && list.get(index).size()>0) {
			if (curr!=index && curr!=-1) {
				p.put(curr, proctotal);
			}
			
			int prev=head;
			int next=list.get(index).get(0);
			int count=getDifference(prev,next);
		
			proctotal+=count;
			total+=count;
			
			chart.drawGraph(t, next);
			head=next;
			list.get(index).remove(0);
		}
	}
}