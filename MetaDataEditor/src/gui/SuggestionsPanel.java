package gui;

import javax.swing.JPanel;

//Panel f�r MetaData Vorschl�ge zur Vereinfachung der Benutzung

import main.Overview;

@SuppressWarnings("serial")
public class SuggestionsPanel extends JPanel {

	public SuggestionsPanel() {
		// TODO Auto-generated constructor stub
		
		Overview.setSuggestionsPanel(this);
	}
}
