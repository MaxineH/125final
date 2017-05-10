package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class GlassPane extends JComponent implements MouseListener {
	
	private int width, height;
	private int x;
	private boolean isGreater=false;
	private Container contentPane;
	private boolean isValid=true;
	
	public GlassPane() {
		addMouseListener(this);
	}
	
	public void setValid(boolean state) {
		isValid=state;
	}
	
	public void setPane(Container contentPane) {
		this.contentPane=contentPane;
	}
	
	public void setBoundaries(int x) {
		this.x=x;
	}

	public void setState(boolean state) {
		isGreater=state;
	}
	
	public void setPaneSize(int width,int height,boolean isGreater) {
		this.height=height;
		this.width=width;
		this.isGreater=isGreater;
		if (isValid) {
			repaint();
			revalidate();
			setVisible(true);
		}
		else {
			setVisible(false);
		}
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(new Color(0,0,0,130));
		if (isGreater)
			g.fillRect(0, 0, width, height);
		else
			g.fillRect(x, 0, width, height);
	}

	private void redispatchMouseEvent(MouseEvent e) {
		Point glassPoint=e.getPoint();
		Container container=contentPane;
		Component component=null;
		Point containerPoint=SwingUtilities.convertPoint(this,glassPoint,container);
		
		//edit here
		
		if ((e.getX()<x && !isGreater) || (e.getX()>=x && isGreater) || (e.getY()>=height)) {
			component=SwingUtilities.getDeepestComponentAt(container, containerPoint.x, containerPoint.y);
			if (component!=null) {
				Point componentPoint=SwingUtilities.convertPoint(this, glassPoint, component);
				component.dispatchEvent(new MouseEvent(component,e.getID(),e.getWhen(),
									e.getModifiers(),componentPoint.x,componentPoint.y,
									e.getClickCount(),e.isPopupTrigger()));
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		redispatchMouseEvent(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		redispatchMouseEvent(e);
	}
}