package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import utils.Utils;

@SuppressWarnings("serial")
public class UserPanel extends JPanel {

	private JLabel[] label=new JLabel[2];
	private JSpinner[] spinner=new JSpinner[2];
	private JLabel error;

	public SpinnerPanel[] sp=new SpinnerPanel[3];
	public JButton addButton, resetButton;

	private Border border;
	private int max=1;
	private int count=1;
	private int time=0;
	
	public UserPanel() {
		Font f=Utils.getFont("res\\STREET.ttf",30f);
		border=BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5,5,5,5),
				"PROCESS 1",TitledBorder.CENTER,TitledBorder.TOP,
				f.deriveFont(Font.BOLD),Color.BLACK);
		setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(10,10,10,10),border));
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setBackground(new Color(/*143,164,183*/164,151,169));
		init(f);
	}
	
	private void init(Font f) {
		String[][] str = {{"arrival time:", "priority number:"},
				   {"Max Vector: ", "Allocated Vector: ", "Cylinders: "}};
		SpinnerNumberModel[] m = {new SpinnerNumberModel(0,0,null,1), new SpinnerNumberModel(1,1,20,1)};

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,2,10,10));
		panel.setMaximumSize(new Dimension(400,120));
		panel.setOpaque(false);
		
		for (int i=0; i<label.length; i++) {
			label[i] = new JLabel(str[0][i]);
			label[i].setOpaque(false);
			label[i].setForeground(Color.BLACK);
			label[i].setFont(f.deriveFont(15f).deriveFont(Font.BOLD));
			panel.add(label[i]);
			
			spinner[i] = new JSpinner(m[i]);
			spinner[i].setBorder(null);
			JFormattedTextField txt = ((JSpinner.NumberEditor)spinner[i].getEditor()).getTextField();
			((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
			panel.add(spinner[i]);
		}
		
		add(Box.createRigidArea(new Dimension(10,20)));
		add(panel);
		add(Box.createRigidArea(new Dimension(10,20)));
		for (int i=0; i<3 ; i++) {
			sp[i] = new SpinnerPanel(str[1][i],Color.BLACK);
			add(sp[i]);
			add(Box.createRigidArea(new Dimension(10,20)));
		}
		
		error=new JLabel("Input is invalid.");
		error.setForeground(Color.RED);
		error.setFont(f.deriveFont(15f).deriveFont(Font.BOLD));
		error.setOpaque(false);
		error.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		error.setVisible(false);
		add(error);
		add(Box.createRigidArea(new Dimension(10,20)));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		buttonPanel.setOpaque(false);
		addButton = new JButton("Add");
		addButton.setFont(f.deriveFont(20f));
		addButton.setBackground(Color.WHITE);
		buttonPanel.add(addButton);
	
		resetButton = new JButton("Reset");
		resetButton.setFont(f.deriveFont(20f));
		resetButton.setBackground(Color.WHITE);
		buttonPanel.add(resetButton);
		
		add(buttonPanel);
		label[1].setVisible(false);
		spinner[1].setVisible(false);
		sp[0].setSpinner(1, 1, 20);
		updateSpinnerPanel(1);
		sp[1].disableSpinner(0);
		sp[2].setSpinner(1,0,1);
		sp[0].setValue(0, 1);
	}

	public void addListener(ChangeListener listener) {
		sp[0].addListener(listener, 0);
	}
	
	public void addBListener(ActionListener listener) {
		addButton.addActionListener(listener);
		resetButton.addActionListener(listener);
	}

	public void updateSpinnerPanel(int i) {
		sp[0].setSpinner(i, 0, 20);
		sp[1].setSpinner(i, 0, 20);
		revalidate();
		repaint();
	}
	
	public void showPrio(boolean state) {
		label[1].setVisible(state);
		spinner[1].setVisible(state);
	}
	
	public void updateMaxV() {
		sp[2].setSpinner(sp[0].getSpinnerValue(0), 0, max);
		repaint();
		revalidate();
	}
	
	public void updateMax(int max) {
		this.max=max;
		sp[2].setSpinnerMax(max);
	}

	public void updateCount() {
		if (count+1==16) {
			addButton.setEnabled(false);
			resetButton.setEnabled(false);
			return;
		}
		count++;
		time+=sp[0].getSpinnerValue(0);
		((TitledBorder)border).setTitle("PROCESS "+count);
		repaint();
	}
	
	public void showError(boolean state) {
		error.setVisible(state);
	}
	
	public void reset() {
		spinner[0].setValue(0);
		spinner[1].setValue(1);
		
		for (int i=0; i<3; i++)
			sp[i].resetValue();
		sp[0].setValue(0, 1);
		repaint();
	}
	
	public void resetAll() {
		count=1;
		((TitledBorder)border).setTitle("PROCESS "+count);
		repaint();
	}
	
	public boolean isPrioVisible() {
		return label[1].isVisible();
	}

	public boolean isInputValid() {
		for(int i=0; i<3; i++) {
			if (sp[i].getInput().equals(""))
				return false;
		}
		return true;
	}
	
	public int getTime() {
		return time;
	}
	
	public Object[] getInput() {
		if (spinner[1].isVisible()) {
			return new Object[]{
				count,(int)spinner[0].getValue(),
				"<html><div style='text-align: center;'>"+sp[0].getInput()+"</div></html>",
				"<html><div style='text-align: center;'>"+sp[1].getInput()+"</div></html>",
				"<html><div style='text-align: center;'>"+sp[2].getInput()+"</div></html>",
				(int)spinner[1].getValue()};
		}
		return new Object[]{count,(int)spinner[0].getValue(),
				"<html><div style='text-align: center;'>"+sp[0].getInput()+"</div></html>",
				"<html><div style='text-align: center;'>"+sp[1].getInput()+"</div></html>",
				"<html><div style='text-align: center;'>"+sp[2].getInput()+"</div></html>"};
	}

}