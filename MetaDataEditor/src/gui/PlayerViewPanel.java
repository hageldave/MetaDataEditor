package gui;

import javax.swing.JPanel;

import main.Overview;

@SuppressWarnings("serial")
public class PlayerViewPanel extends JPanel {
	
	public PlayerViewPanel() {
		// TODO Auto-generated constructor stub
		
		Overview.setPlayerViewPanel(this);
	}

}
