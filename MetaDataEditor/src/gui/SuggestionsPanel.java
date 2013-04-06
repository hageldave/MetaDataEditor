package gui;

import javax.swing.JPanel;

import main.Overview;

@SuppressWarnings("serial")
public class SuggestionsPanel extends JPanel {

	public SuggestionsPanel() {
		// TODO Auto-generated constructor stub
		
		Overview.setSuggestionsPanel(this);
	}
}
