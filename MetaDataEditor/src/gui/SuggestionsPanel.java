package gui;

import javax.swing.JPanel;

//Panel für MetaData Vorschläge zur Vereinfachung der Benutzung

import main.Overview;

@SuppressWarnings("serial")
public class SuggestionsPanel extends JPanel {

	public SuggestionsPanel() {
		// TODO Auto-generated constructor stub
		
		Overview.setSuggestionsPanel(this);
	}
}
