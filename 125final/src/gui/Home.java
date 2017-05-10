package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

import utils.CustomTab;
import controller.ActionController;
import controller.ChangeController;
import controller.ItemController;

@SuppressWarnings("serial")
public class Home extends JFrame {

	private JPanel panel,home;
	private GlassPane glass;
	private Container c;
	
	public RandPanel randPanel;
	public ListPanel listPanel;
	public CustomTab tab;
	public UserPanel userPanel;
	public Preset preset;
	public MenuPanel menuPanel;
	public SimulationPanel simPanel;
	
	private boolean right=true;
	
	public Home() {
		BufferedImage icon=null;
		try {
			icon= ImageIO.read(new File("res/eshilogo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		home=new JPanel();
		home.setOpaque(false);
		home.setLayout(new BorderLayout());

		c=getContentPane();
		c.setLayout(new CardLayout());
		
		glass=new GlassPane();
		setName("PSEUDO OS");
		setIconImage(icon);
		setMinimumSize(new Dimension(1300,700));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setGlassPane(glass);
		init();
		
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (right) glassRight();
				else glassLeft();
			}});
		
		new ItemController(this);
		new ChangeController(this);
		new ActionController(this);
	}
	
	private void init() {
		panel=new JPanel();
		panel.setLayout(new GridLayout(1,2,15,0));
		panel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		panel.setBackground(new Color(38,45,52));
		
		userPanel=new UserPanel();
		randPanel=new RandPanel();
		
		tab=new CustomTab();
		tab.add(userPanel,"User-Defined");
		tab.add(randPanel,"Randomize");
		
		listPanel=new ListPanel();
		panel.add(tab);
		panel.add(listPanel);

		preset=new Preset();
		menuPanel=new MenuPanel(preset);
		home.add(menuPanel,BorderLayout.WEST);
		home.add(panel,BorderLayout.CENTER);

		simPanel=new SimulationPanel();
		//add stats panel here
		c.add(home,"Home");
		c.add(simPanel,"Simulation");
		
		repaint();
		revalidate();
		glassRight();
		glass.setPane(getContentPane());
	}
	
	public void addListener(ChangeListener listener) {
		tab.addChangeListener(listener);
	}
	
	public void glassRight() {
		right=true;
		glass.setBoundaries(panel.getX());
		glass.setPaneSize(panel.getWidth(), panel.getHeight(),false);
	}
	
	public void glassLeft() {
		right=false;
		glass.setPaneSize(menuPanel.getWidth(), menuPanel.getHeight()-menuPanel.button.getHeight(),true);
	}

	public void showNext() {
		CardLayout card=(CardLayout)c.getLayout();
		card.next(c);
		glass.setValid(false);
		glass.setVisible(false);
	}
	
	public void showPrev() {
		CardLayout card=(CardLayout)c.getLayout();
		card.previous(c);
		glass.setValid(true);
		glass.setVisible(true);
	}
}