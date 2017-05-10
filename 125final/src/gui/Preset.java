package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import utils.Utils;

@SuppressWarnings("serial")
public class Preset extends JPanel {

	private JLabel[] label= new JLabel[3];
	public JSpinner[] spinner= new JSpinner[3];

	public Preset() {
		setOpaque(false);
		setLayout(new GridLayout(4,2,5,5));
		setMaximumSize(new Dimension(350,125));
		setBorder(BorderFactory.createMatteBorder(10,0,0,0,new Color(0,0,0,0)));
		init();
	}
	
	private void init() {
		String[] str= {"num of resources:","max cylinder:",
					   "head cylinder:"};
		SpinnerNumberModel[] m= {new SpinnerNumberModel(1,1,10,1), new SpinnerNumberModel(1,1,null,1),
						new SpinnerNumberModel(0,0,1,1)};
		Font f= Utils.getFont("res\\STREET.ttf",15f);
		
		for(int i=0; i<3; i++) {
			label[i]= new JLabel(str[i]);
			label[i].setForeground(Color.WHITE);
			label[i].setFont(f);
			add(label[i]);
			
			spinner[i]= new JSpinner(m[i]);
			spinner[i].setBorder(null);
			JFormattedTextField txt= ((JSpinner.NumberEditor)spinner[i].getEditor()).getTextField();
			((NumberFormatter)txt.getFormatter()).setAllowsInvalid(false);
			add(spinner[i]);
		}
	}
	
	public void addListener(ChangeListener listener) {
		spinner[0].addChangeListener(listener);
		spinner[1].addChangeListener(listener);
	}
}