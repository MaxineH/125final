package utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class CustomField extends JTextField {

	private Color color;
	private Color prev;
	private int prevX=5, currX=11;
	
	public CustomField(Color color,Color prev, String s) {
		this.color=color;
		this.prev=prev;
		Border b=BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5,5,5,5),
				s,TitledBorder.RIGHT,TitledBorder.BELOW_BOTTOM,Utils.getFont("res\\STREET.ttf", 15f),Color.BLACK);
		setBorder(BorderFactory.createCompoundBorder(new BorderLine(),b));
		setEditable(false);
		setOpaque(false);
		setPreferredSize(new Dimension(65,50));
	}
	
	public CustomField(Color color,Color prev, String s, int i) {
		this.color=color;
		this.prev=prev;
		Border b=BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5,5,5,5),
				s,i,TitledBorder.BELOW_BOTTOM,Utils.getFont("res\\STREET.ttf", 15f),Color.BLACK);
		setBorder(BorderFactory.createCompoundBorder(new BorderLine(),b));
		setEditable(false);
		setOpaque(false);
		setPreferredSize(new Dimension(65,50));
	}
	
	private class BorderLine extends AbstractBorder {
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			((Graphics2D)g).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(prev);
			g.drawRect(x-prevX, y, width-5, height-17);
			g.fillRect(x-prevX, y, width-5, height-17);
			g.setColor(color);
			g.drawRect(x+currX, y, width-5, height-17);
			g.fillRect(x+currX, y, width-5, height-17);
		}
	}
	
	public void resetPrev(Color color) {
		prev=color;
		prevX=-5;
		currX=-5;
	}
}