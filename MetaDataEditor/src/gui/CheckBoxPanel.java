package gui;

import gui.util.NiceCheckboxList;
import gui.util.FieldKeyCheckBox;
import gui.util.RelativeLayoutPanel;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.jaudiotagger.tag.FieldKey;

import main.Overview;

/**
 * Panel das eine Liste der auswaehlbaren {@linkplain FieldKeyCheckBox}es
 * enthaelt.
 */
@SuppressWarnings("serial")
public class CheckBoxPanel extends RelativeLayoutPanel {
	
	FieldKeyCheckBox titel = new FieldKeyCheckBox("Titel", FieldKey.TITLE);
	FieldKeyCheckBox interpret = new FieldKeyCheckBox("Interpret", FieldKey.ARTIST);
	FieldKeyCheckBox album = new FieldKeyCheckBox("Album", FieldKey.ALBUM);
	FieldKeyCheckBox jahr = new FieldKeyCheckBox("Jahr", FieldKey.YEAR);
	FieldKeyCheckBox titelnummer = new FieldKeyCheckBox("Titelnummer", FieldKey.TRACK);
	FieldKeyCheckBox genre = new FieldKeyCheckBox("Genre", FieldKey.GENRE);
	// TODO: noch mehr Checkboxes
	
	/** Array mit allen Checkboxes der Liste */
	private FieldKeyCheckBox[] allCheckBoxes;
	
	/** Array fuer die Eintraege der Liste <br>
	 * (ueberwiegend Checkboxes aber auch zb. JSeperator) 
	 */
	private Component[] listData;

	/** Konstruktor fuer {@link CheckBoxPanel}
	 */
	public CheckBoxPanel() {
		Overview.setCheckboxPanel(this);
		initListData();
		NiceCheckboxList complist = new NiceCheckboxList(listData);
		JScrollPane scrollpane = new JScrollPane(complist);
		this.add(scrollpane, 0.5f, 0.5f, true, 1, 1);
	}

	
	/** 
	 * initialisiert {@link #allCheckBoxes} und {@link #listData}
	 */
	private void initListData() {
		allCheckBoxes = new FieldKeyCheckBox[]{titel,interpret,album,jahr,titelnummer,genre};
		addCheckedListener();
		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		listData = new Component[]{titel, interpret, album, sep, genre, jahr, titelnummer};
	}

	/**
	 * fuegt allen Checkboxes einen {@link ItemListener} hinzu.
	 * Der Listener sendet bei statusaenderung einer Checkbox
	 * alle ausgewahlten Fieldkeys an den {@link MetaDataView}
	 */
	private void addCheckedListener() {
		ItemListener listener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println("CheckBoxPanel: checkbox changed");
				Overview.getMetaView().setAvailableFieldKeys(getCheckedFieldKeys());
			}
		};

		for(FieldKeyCheckBox chckBox: allCheckBoxes){
			chckBox.addItemListener(listener);
		}
	}
	
	/**
	 * @return eine ArrayList aller ausgewaehlter FieldKeys
	 */
	private ArrayList<FieldKey> getCheckedFieldKeys() {
		ArrayList<FieldKey> toReturn = new ArrayList<FieldKey>();
		for(FieldKeyCheckBox checkbox: allCheckBoxes){
			if(checkbox.isSelected()){
				System.out.println("add " + checkbox.getFieldKey());
				toReturn.add(checkbox.getFieldKey());
			}
		}
		return toReturn;
	}
	

//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		frame.add(new CheckBoxPanel());
//		frame.setSize(400, 400);
//		frame.setVisible(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}

}
