package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;

import model.Algo;
import utils.Utils;

@SuppressWarnings("serial")
public class AlgoPanel extends JPanel {

	@SuppressWarnings("unchecked")
	public JComboBox<String>[] box=new JComboBox[3];
	private JLabel[] label=new JLabel[4];
	private JSpinner spinner;
	private Algo algo;
	
	public AlgoPanel(int id) {
		Font font=Utils.getFont("res\\STREET.ttf", 15f);
		Border border=BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
						"Simulation "+id,TitledBorder.CENTER,TitledBorder.TOP,
						font.deriveFont(Font.BOLD),Color.BLACK);
		setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5), border));
		setLayout(new GridLayout(3,2,5,5));
		setBackground(Color.WHITE);
		setMaximumSize(new Dimension(350,160));
		init(font);
	}
	
	private void init(Font font) {
		algo=new Algo();
		String[][] list={{"First Come First Serve", "Shortest Job First",
							"Shortest Remaining Time First", "Priority Sched",
							"Preemptive-Priority Sched", "Round Robin"},
							{"TDDT", "TDTD(continuous)", "TDTD(reset)"},
							{"FCFS", "SSTF", "SCAN", "C-SCAN", "LOOK", "C-LOOK"},
							{"CPU Algorithm", "Banker's Algorithm", "Disk Algorithm"}};

		for (int i=0; i<3; i++) {
			label[i]=new JLabel(list[3][i]);
			label[i].setOpaque(false);
			label[i].setFont(font.deriveFont(15f));
			add(label[i]);
			
			box[i]=new JComboBox<String>(list[i]);
			box[i].setBackground(Color.WHITE);
			box[i].setFont(font.deriveFont(15f));
			add(box[i]);
		}
		
		label[3]=new JLabel("time quantum:");
		label[3].setOpaque(false);
		label[3].setFont(font.deriveFont(15f));
		
		spinner=new JSpinner(new SpinnerNumberModel(1,1,20,1));
		JFormattedTextField txt= ((JSpinner.NumberEditor)spinner.getEditor()).getTextField();
		((NumberFormatter)txt.getFormatter()).setAllowsInvalid(false);
	}
	
	public void addListener(ItemListener listener) {
		box[0].addItemListener(listener);
	}
	
	public void showQ(boolean state) {
		GridLayout layout=(GridLayout) getLayout(); 
		if (state) {
			layout.setRows(4);
			add(label[3]);
			add(spinner);
		}
		else {
			layout.setRows(3);
			remove(label[3]);
			remove(spinner);
		}
		repaint();
		revalidate();
	}

	public int getQ() {
		return (int)spinner.getValue();
	}
	
	public boolean isQvisible() {
		return spinner.isVisible();
	}
	
	public Algo getInput() {
		algo.setCPU((String)box[0].getSelectedItem());
		algo.setBankers((String)box[1].getSelectedItem());
		algo.setDisk((String)box[2].getSelectedItem());
		return algo;
	}
}