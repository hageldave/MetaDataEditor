package gui;

import gui.util.RelativeLayoutPanel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import main.Overview;

public class MainFrame extends JFrame {
	
	public MainFrame () {
		//TODO:...
		setDefaultLookAndFeelDecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setJMenuBar(new MetaMenuBar());
//		this.setPreferredSize(new Dimension(650, 450));
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(600, 400));
		this.pack();
		
		this.setContentPane(new RelativeLayoutPanel());
		((RelativeLayoutPanel)this.getContentPane()).add(new Browser(), 0.375f, 0.25f, true, 0.75f, 0.5f);
		((RelativeLayoutPanel)this.getContentPane()).add(new CheckBoxPanel(), 0.875f, 0.25f, true, 0.25f, 0.5f);
		((RelativeLayoutPanel)this.getContentPane()).add(new MetaDataView(), 0.375f, 0.75f, true, 0.75f, 0.5f);
		
		this.setVisible(true);
		Overview.setMainFrame(this);
	}
	
	
	

}
