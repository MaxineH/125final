package gui;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.Utils;

@SuppressWarnings("serial")
public class RandPanel extends JPanel {

	private boolean hasPrio=false;
	private int max=1, resourceNum=1, time=1;
	private static String header="<html><div style='text-align: center;'>";
	private static String end="</div></html>";
	
	public RandPanel() {

		JLabel label=new JLabel("Generating random list of processes");
		label.setFont(Utils.getFont("res\\STREET.ttf", 25f));
		setLayout(new GridBagLayout());
		setBackground(new Color(/*143,164,183*/164,151,169));
		add(label);
	}
	
	private String getVector(int max,int size) {
		String str = "";
		Random r = new Random();
		for (int i=0; i<size; i++) {
			if (i!=0 && i%5==0) {
				str+="<br>";
			}
			str+=r.nextInt(max)+",";
		}
		if (str.endsWith(",")) {
			str=str.substring(0, str.length()-1);
		}
		return str;
	}
	
	public void setAlgoState(boolean state) {
		this.hasPrio=state;
	}
	
	public void setRCount(int resourceNum) {
		this.resourceNum=resourceNum;
	}
	
	public void setMax(int max) {
		this.max=max;
	}
	
	public int getRand(String max){
		Random r = new Random();
		try{	
			return r.nextInt(Integer.parseInt(max)) ;	
		}catch(IllegalArgumentException e){
			return 0;
		}
	}
	
	public String getAlloc(String max[]){
		String str="";
		
		for (int i=0; i<max.length; i++){
			if (i!=0 && i%5==0) {
				str+="<br>";
			}

			str+=Integer.toString(getRand(max[i]))+",";
		}
		if (str.endsWith(",")) {
			str=str.substring(0, str.length()-1);
		}
		return str;
			
	}
	
	public void init(ListPanel lp) {
		Random r=new Random();
		int procCount=r.nextInt(15)+1;
		
		for (int i=0; i<procCount; i++) {
			int arrivalTime=r.nextInt(21)/3;
			
			String ceiling = getVector(20, resourceNum-1);
			String m=header+(r.nextInt(20)+1)+","+ ceiling +end;
				
			String alloc=header+"0,"+getAlloc(ceiling.split(","));
			
			String tmp=m.replace(header, "");
			tmp=tmp.substring(0, tmp.indexOf(","));
			
			time=Integer.parseInt(tmp);
			String c=header+getVector(max,Integer.parseInt(tmp))+end;
			
			if (resourceNum==1) {
				m=m.replace(",", "");
				alloc=alloc.replace(",", "");
			}
			
			if (hasPrio) {
				int prio=r.nextInt(20)+1;
				lp.addRow(new Object[]{i+1,arrivalTime,m,alloc,c,prio});
			}
			else {
				lp.addRow(new Object[]{i+1,arrivalTime,m,alloc,c});
			}
		}
	}
	
	public int getTime() {
		return time;
	}
}