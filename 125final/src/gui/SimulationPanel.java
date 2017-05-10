package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.Utils;

@SuppressWarnings("serial")
public class SimulationPanel extends JPanel {

	private JPanel colorPanel, right;
	private JButton step, exit, stat;
	
	private Font f;
	
	public SimulationPanel() {
		f=Utils.getFont("res\\STREET.ttf", 15f).deriveFont(Font.BOLD);
		setLayout(new BorderLayout());
		initLeft();
		initRight();
	}
	
	private void initLeft() {
		JPanel panel=new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(new Color(33,39,44));
		panel.setPreferredSize(new Dimension(350,700));
		
		step=new JButton("PAUSE");
		step.setFont(f);
		step.setForeground(Color.WHITE);
		step.setBackground(new Color(33,38,43));
		
		exit=new JButton("<html><U>BACK</U></html>");
		exit.setFont(f);
		exit.setForeground(Color.WHITE);
		exit.setBackground(new Color(33,38,43));
		
		JPanel center=new JPanel();
		center.setOpaque(false);
		center.setLayout(new GridBagLayout());
		
		colorPanel=new JPanel();
		colorPanel.setOpaque(false);
		colorPanel.setLayout(new BoxLayout(colorPanel,BoxLayout.Y_AXIS));
		center.add(colorPanel);
		
		panel.add(step,BorderLayout.NORTH);
		panel.add(exit,BorderLayout.SOUTH);
		panel.add(center,BorderLayout.CENTER);
		add(panel,BorderLayout.WEST);
	}
	
	private void initRight() {
		right=new JPanel();
		right.setBackground(new Color(38,45,52));
		right.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		right.setLayout(new GridLayout(1,2,10,0));
		
		stat=new JButton("<html><U>SEE STATISTICS</U></html>");
		stat.setFont(f);
		stat.setForeground(Color.WHITE);
		stat.setVisible(false);
		stat.setBackground(new Color(33,38,43));
		
		JPanel panel=new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.add(stat,BorderLayout.SOUTH);
		panel.add(right,BorderLayout.CENTER);
		
		add(panel,BorderLayout.CENTER);
	}
	
	public void addListener(ActionListener listener) {
		step.addActionListener(listener);
		exit.addActionListener(listener);
	}
	
	public void setLegend(int n) {
		for (int i=0; i<n; i++) {
			JLabel label=new JLabel("■ PROCESS "+(i+1));
			if(i<9) label.setText("■ PROCESS   "+(i+1));
			label.setFont(label.getFont().deriveFont(15f));
			label.setOpaque(false);
			label.setForeground(Utils.getColor(i));
			label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			colorPanel.add(label);
		}
	}

	public void setStep(boolean isStep) {
		step.setEnabled(isStep);
	}

	public void addChart(Chart chart) {
		right.add(chart);
	}
	
	public void removeChart() {
		right.removeAll();
		right.repaint();
		right.revalidate();
	}
}