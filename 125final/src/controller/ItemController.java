package controller;

import gui.AlgoPanel;
import gui.Home;
import gui.MenuPanel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class ItemController implements ItemListener {

	private Home home;
	private MenuPanel menuPanel;
	private ArrayList<AlgoPanel> algo;
	
	public ItemController(Home home) {
		this.home=home;
		this.menuPanel=home.menuPanel;
		this.algo=menuPanel.algo;
		menuPanel.addListener(this);
		algo.get(0).addListener(this);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource()==menuPanel.mode) {
			if (menuPanel.mode.getSelectedIndex()==1) {
				if (algo.size()<2) {
					algo.add(new AlgoPanel(2));
				}
				menuPanel.comparative();
				algo.get(1).addListener(this);
			}
			else if (menuPanel.mode.getSelectedIndex()==0) {
				if (algo.size()>1) {
					menuPanel.single();
					algo.remove(1);
					algo.get(1).box[0].removeItemListener(this);
				}
			}
		}
		else {
			for (int i=0; i<algo.size(); i++) {
				if (e.getSource()==algo.get(i).box[0]) {
					String s=(String) algo.get(i).box[0].getSelectedItem();
					if (s.equals("Round Robin")) {
						algo.get(i).showQ(true);
					}
					else if (s.contains("Priority")) {
						home.userPanel.showPrio(true);
						home.listPanel.showPrio(6);
						home.randPanel.setAlgoState(true);
					}
					if (algo.get(i).isQvisible() && !s.contains("Round")) {
						algo.get(i).showQ(false);
					}
				}
			}
			if (home.userPanel.isPrioVisible()) {
				boolean set=false;
				if (((String)algo.get(0).box[0].getSelectedItem()).contains("Prio") ||
					algo.size()>1 && ((String)algo.get(1).box[0].getSelectedItem()).contains("Prio")) {
						set=true;
				}
				if (!set) {
					home.userPanel.showPrio(false);
					home.listPanel.showPrio(5);
					home.randPanel.setAlgoState(false);
				}
			}
		}
	}

}