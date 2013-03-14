package main;

import java.io.File;

import gui.Browser;
import gui.CheckBoxPanel;
import gui.MainFrame;
import gui.MetaDataView;
import gui.PlayerViewPanel;
import gui.SuggestionsPanel;

/**
 * Klasse die die Kommunikation zwischen Panels und anderen Teilen des 
 * Programms erleichtert. Z.B. werden hier die Panels registriert, und
 * können so von allen Klassen abgerufen werden.
 * @author David
 */
public class Overview {
	
	/* GUI Komponenten */
	
	private static Browser browser;
	
	private static CheckBoxPanel checkboxPanel;
	
	private static MainFrame mainFrame;
	
	private static MetaDataView metaView;
	
	private static PlayerViewPanel playerViewPanel;
	
	private static SuggestionsPanel suggestionsPanel;

	
	/* anderes Zeug */
	
	private static File[] selectedFiles;
	
	
	/* getter & setter */
	
	public static Browser getBrowser() {
		return browser;
	}

	public static void setBrowser(Browser browser) {
		Overview.browser = browser;
	}

	public static CheckBoxPanel getCheckboxPanel() {
		return checkboxPanel;
	}

	public static void setCheckboxPanel(CheckBoxPanel checkboxPanel) {
		Overview.checkboxPanel = checkboxPanel;
	}

	public static MainFrame getMainFrame() {
		return mainFrame;
	}

	public static void setMainFrame(MainFrame mainFrame) {
		Overview.mainFrame = mainFrame;
	}

	public static MetaDataView getMetaView() {
		return metaView;
	}

	public static void setMetaView(MetaDataView metaView) {
		Overview.metaView = metaView;
	}

	public static PlayerViewPanel getPlayerViewPanel() {
		return playerViewPanel;
	}

	public static void setPlayerViewPanel(PlayerViewPanel playerViewPanel) {
		Overview.playerViewPanel = playerViewPanel;
	}

	public static SuggestionsPanel getSuggestionsPanel() {
		return suggestionsPanel;
	}

	public static void setSuggestionsPanel(SuggestionsPanel suggestionsPanel) {
		Overview.suggestionsPanel = suggestionsPanel;
	}

	public static File[] getSelectedFiles() {
		return selectedFiles;
	}

	public static void setSelectedFiles(File[] selectedFiles) {
		Overview.selectedFiles = selectedFiles;
	}
	
	
}
