package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import model.Algo;
import utils.Utils;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {

	private SpinnerPanel available;
	private Preset preset;
	private JPanel panel;

	public JButton button;
	public JComboBox<String> mode;
	public ArrayList<AlgoPanel> algo=new ArrayList<AlgoPanel>();
	
	public MenuPanel(Preset preset) {
		this.preset=preset;
		String[] modes={"Single","Comparative"};
		Font f=Utils.getFont("res\\STREET.ttf",15f);
		
		mode= new JComboBox<String>(modes);
		mode.setBackground(Color.WHITE);
		mode.setFont(f);
		
		button= new JButton("<html><U>OK</U></html>");
		button.setForeground(Color.WHITE);
		button.setFont(f.deriveFont(Font.BOLD));
		button.setBackground(new Color(33,38,43));
		
		setBackground(new Color(33,39,44));
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(350,700));
		add(mode,BorderLayout.NORTH);
		add(button,BorderLayout.SOUTH);
		init();
	}
	
	private void init() {
		panel=new JPanel();
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
	
		available=new SpinnerPanel("Available Vector",Color.WHITE,350,150);
		updateAvailable(1);
		available.disableSpinner(0);
		algo.add(new AlgoPanel(1));
		
		panel.add(algo.get(0));
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		panel.add(preset);
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		panel.add(available);
		add(panel,BorderLayout.CENTER);
	}

	public void addListener(ItemListener listener) {
		mode.addItemListener(listener);
	}
	
	public void addBListener(ActionListener listener) {
		button.addActionListener(listener);
	}
	
	public void updateAvailable(int i) {
		available.setSpinner(i, 0, 20);
		revalidate();
		repaint();
	}
	
	public void comparative() {
		panel.add(algo.get(1),2);
		panel.repaint();
		panel.revalidate();
	}
	
	public void single() {
		panel.remove(2);
		panel.add(preset,2);
		panel.add(available);
		panel.repaint();
		panel.revalidate();
	}
	
	public void changeButton(String txt) {
		button.setText("<html><U>"+txt+"</U></html>");
		button.setFont((button.getFont()).deriveFont(Font.BOLD));
	}

	public Algo[] getAlgo() {
		Algo[] a=new Algo[algo.size()];
		for (int i=0; i<algo.size(); i++) {
			a[i]=algo.get(i).getInput();
		}
		return a;
	}
	
	public int[] getQuantum() {
		int[] n=new int[algo.size()];
		for (int i=0; i<algo.size(); i++) {
			n[i]=algo.get(i).getQ();
		}
		return n;
	}

	public int getAlgoSize() {
		return algo.size();
	}
	
	public int getResourceNum() {
		return (int)preset.spinner[0].getValue();
	}

	public int getMax() {
		return (int)preset.spinner[1].getValue();
	}
	
	public int getHead() {
		return (int)preset.spinner[2].getValue();
	}
	
	public ArrayList<Integer> getAvailable() {
		return Utils.convertList(available.getInput());
	}
}