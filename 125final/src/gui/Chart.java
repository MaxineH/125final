package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import utils.CustomField;
import utils.Utils;

@SuppressWarnings("serial")
public class Chart extends JPanel {
	
	private JPanel top,avail,queue,center;
	private DiskPanel bottom;
	private JLabel[] label;
	
	private Font f;
	private Color prevColor=Color.WHITE, rColor=Color.WHITE;
	private CustomField prev=null;
	
	public Chart(int id,int head,int max) {
		f=Utils.getFont("res\\STREET.ttf",15f);
		Border b=BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5,5,5,5),
				"Simulation "+id,TitledBorder.CENTER,TitledBorder.TOP,
				f.deriveFont(30f).deriveFont(Font.BOLD),Color.BLACK);
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		setBorder(b);
		initTop();
		queue=new JPanel();
		queue.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		queue.setOpaque(false);
		queue.add(new CustomField(Color.WHITE,rColor,""));
		top.add(queue);
		add(top);
		
		initCenter();
		bottom=new DiskPanel(max,head);
		add(bottom);
	}
	
	private void initTop() {
		top=new JPanel();
		top.setBorder(null);
		top.setOpaque(false);
		top.setPreferredSize(new Dimension(460,200));
		top.setLayout(new BoxLayout(top,BoxLayout.Y_AXIS));
		
		JLabel label=new JLabel("Current Available:");
		label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		label.setFont(f);
		top.add(label);
		top.add(Box.createRigidArea(new Dimension(10,5)));
		
		avail=new JPanel();
		avail.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
		avail.setOpaque(false);
		top.add(avail);
		top.add(Box.createRigidArea(new Dimension(10,5)));
		
		label=new JLabel("Ready Queue:");
		label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		label.setFont(f);
		top.add(label);
		top.add(Box.createRigidArea(new Dimension(10,5)));
	}

	private void initCenter() {
		center=new JPanel();
		center.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		center.setOpaque(false);
		center.add(prev=new CustomField(Color.WHITE,prevColor,"0"));
		
		JScrollPane pane=new JScrollPane(center,JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setBorder(null);
		pane.setOpaque(false);
		pane.getViewport().setBorder(null);
		pane.getViewport().setOpaque(false);
		pane.setMinimumSize(new Dimension(450,100));
		add(pane);
		add(Box.createRigidArea(new Dimension(10,20)));
	}
	
	@SuppressWarnings("deprecation")
	public void addReadyQueue(Color color, int t) {
		if (queue.countComponents()==0 && t!=0) {
			queue.add(new CustomField(Color.LIGHT_GRAY,rColor,"0",TitledBorder.LEFT));
			rColor=Color.LIGHT_GRAY;
		}
		queue.add(new CustomField(color,rColor,Integer.toString(t),TitledBorder.LEFT));
		rColor=color;
		queue.repaint();
	}

	public void initAvailable(ArrayList<Integer> set) {
		label=new JLabel[set.size()];
		for (int i=1; i<set.size(); i++) {
			label[i]=new JLabel(Integer.toString(set.get(i)));
			label[i].setFont(f);
			avail.add(label[i]);
		}
	}

	public void showAvailable(int i, String s) {
		label[i].setText(s);
		avail.repaint();
		repaint();
		revalidate();
	}

	public void addBox(int t, Color color, boolean hasLabel) {
		prev.resetPrev(color);
		
		if (hasLabel) center.add(prev=new CustomField(color,Color.WHITE,Integer.toString(t)));
		else center.add(prev=new CustomField(color,Color.WHITE,""));
		prevColor=color;
		
		prev.resetPrev(Color.WHITE);
		center.repaint();
		repaint();
		revalidate();
	}

	public void drawGraph(int n, int t) {
		bottom.updateChart(n, t);
	}
}