package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
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
public class SpinnerPanel extends JPanel {

	private ArrayList<JSpinner> spinner= new ArrayList<JSpinner>();
	
	public SpinnerPanel(String title, Color color, int x, int y) {
		Font f= Utils.getFont("res\\STREET.ttf", 15f);
		Border b = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5,5,5,5),title,
				   TitledBorder.CENTER,TitledBorder.TOP,f.deriveFont(Font.BOLD),color);
		setBorder(new CompoundBorder(b,BorderFactory.createEmptyBorder(5,5,5,5)));
		setOpaque(false);
		setMaximumSize(new Dimension(x,y));
		setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
	}
	
	public SpinnerPanel(String title, Color color) {
		Font f= Utils.getFont("res\\STREET.ttf", 15f);
		Border b = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5,5,5,5),title,
				   TitledBorder.CENTER,TitledBorder.TOP,f.deriveFont(Font.BOLD),color);
		setBorder(new CompoundBorder(b,BorderFactory.createEmptyBorder(5,5,5,5)));
		setOpaque(false);
		setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
	}
	
	public void setSpinner(int n, int min, int max) {
		int i= spinner.size();
		Font f= Utils.getFont("res\\STREET.ttf", 16f);
		for(; i<n; i++) {
			spinner.add(new JSpinner(new SpinnerNumberModel(min,min,max,1)));
			spinner.get(i).setBorder(null);
			JFormattedTextField txt= ((JSpinner.NumberEditor)spinner.get(i).getEditor()).getTextField();
			txt.setFont(f);
			((NumberFormatter)txt.getFormatter()).setAllowsInvalid(false);
			add(spinner.get(i));
		}
		for (; i>n; i--) {
			remove(spinner.get(n));
			spinner.remove(n);
		}
	}
	
	public void disableSpinner(int index) {
		spinner.get(index).setEnabled(false);
	}
	
	public void setSpinnerMax(int max) {
		for (int i=0; i<spinner.size(); i++) {
			((SpinnerNumberModel) spinner.get(i).getModel()).setMaximum(max);
			revalidate();
		}
	}
	
	public void setValue(int index,int value) {
		spinner.get(index).setValue(value);
	}
	
	public void resetValue() {
		for (int i=0; i<spinner.size(); i++) {
			spinner.get(i).setValue((Integer) ((SpinnerNumberModel) spinner.get(i).getModel()).getMinimum());
		}
	}
	
	public void addListener(ChangeListener listener, int index) {
		spinner.get(index).addChangeListener(listener);
	}
	
	public int getSpinnerValue(int index) {
		return (int)spinner.get(index).getValue();
	}
	
	public boolean isListEmpty() {
		return spinner.isEmpty();
	}
	
	public String getInput() {
		String s="";
		for (int i=0; i<spinner.size(); i++) {
			if(i%5==0 && i!=0) s+="<br>";
			s+=spinner.get(i).getValue()+",";
		}
		if(s.endsWith(","))
			s=s.substring(0, s.length()-1);
		return s;
	}
}