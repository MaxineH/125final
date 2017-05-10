package controller;

import gui.Home;
import gui.Preset;
import gui.UserPanel;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ChangeController implements ChangeListener {

	private Home home;
	private Preset preset;
	private UserPanel userPanel;
	
	public ChangeController(Home home) {
		this.home=home;
		this.preset=home.preset;
		this.userPanel=home.userPanel;
		home.addListener(this);
		preset.addListener(this);
		userPanel.addListener(this);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource()==home.tab) {
			if (home.tab.getSelectedIndex()==1) {
				home.tab.setEnabledAt(0,false);
				home.randPanel.init(home.listPanel);
			}
			else
				home.tab.setEnabledAt(1,false);
		}
		else if (e.getSource()==preset.spinner[0]) {
			int i=(int)preset.spinner[0].getValue();
			home.menuPanel.updateAvailable(i);
			userPanel.updateSpinnerPanel(i);
			home.randPanel.setRCount(i);
		}
		else if (e.getSource()==preset.spinner[1]) {
			int i=(int)preset.spinner[1].getValue();
			((SpinnerNumberModel)preset.spinner[2].getModel()).setMaximum(i);
			if (!userPanel.sp[2].isListEmpty())
				userPanel.updateMax(i);
			home.randPanel.setMax(i);
		}
		else {
			userPanel.updateMaxV();
		}
	}
}