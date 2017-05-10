package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

@SuppressWarnings("serial")
public class CustomTab extends JTabbedPane {


	public CustomTab() {
		UIManager.put("TabbedPane.tabAreaBackground",new Color(0,0,0,0));
		setTabPlacement(SwingConstants.TOP);
		CustomUI ui = new CustomUI();
		setUI(ui);
		ui.overrideContentBorderInsetsOfUI();
	}
	
	private class CustomUI extends MetalTabbedPaneUI {
		private Font f = Utils.getFont("res\\titlefont.ttf", 25f);
		private Color selectedColor = new Color(250,107,91);
		private Color bg = new Color(40,49,57);
		
		public void overrideContentBorderInsetsOfUI() {
			this.contentBorderInsets.top = -1;
			this.contentBorderInsets.left = -1;
			this.contentBorderInsets.right = -1;
			this.contentBorderInsets.bottom = -1;
		}
		
		@Override
		protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, 
				int tabIndex, Rectangle iconRect, Rectangle textRect) {
			if (tabIndex==getSelectedIndex() && isEnabledAt(tabIndex))
				g.setColor(selectedColor);
			else 
				g.setColor(bg);
			g.fillRect(rects[tabIndex].x+4, rects[tabIndex].y,
					rects[tabIndex].width, rects[tabIndex].height);
			g.setColor(Color.WHITE);
			if(!isEnabledAt(tabIndex))
				g.setColor(Color.GRAY);
			g.setFont(f);
			String title = getTitleAt(tabIndex);
			FontMetrics metrics = g.getFontMetrics(f);
			int x = metrics.stringWidth(title);
			g.drawString(title, rects[tabIndex].x+((rects[tabIndex].width/2)-(x/2))-2, 37);
		}
		   
		@Override
		protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
			return (getWidth()/2)-6;
		}
		   
		@Override
		protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
			return 30+super.calculateTabHeight(tabPlacement, tabIndex, fontHeight);
		}
	}
}