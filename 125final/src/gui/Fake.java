package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Fake extends JFrame {

	public Fake() {
		setName("PSEUDO OS");
		setMinimumSize(new Dimension(1300,700));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		init();
	}
	
	private void init() {
		add(new SimulationPanel());
	}
}