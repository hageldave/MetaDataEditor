package gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MetaMenuBar extends JMenuBar {

	JMenu data;
	JMenu profil;
	JMenu option;
	JMenu music;

	JMenuItem openFile;
	JMenuItem removeFile;
	JMenuItem removeAll;
	JMenuItem exit;

	public MetaMenuBar() {
		initMenu();
		initMenuItem();
		addItemToMenu();
		
		this.add(data);
		this.add(profil);
		this.add(option);
		this.add(music);
	}

	private void initMenu() {
		data = new JMenu("Datei");
		profil = new JMenu("Profil");
		option = new JMenu("Optionen");
		music = new JMenu("Musik");

	}

	private void initMenuItem() {
		openFile = new JMenuItem("MP3-Datei öffnen");
		removeFile = new JMenuItem("Datei entfernen");
		removeAll = new JMenuItem("Alle entfernen");
		exit = new JMenuItem("Schließen");
	}

	private void addItemToMenu() {
		data.add(openFile);
		data.add(removeFile);
		data.add(removeAll);
		data.add(exit);
	}
}
