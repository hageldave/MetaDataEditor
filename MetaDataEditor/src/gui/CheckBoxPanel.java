package gui;

import gui.util.NiceCheckboxList;
import gui.util.MetaTypeCheckBox;
import gui.util.RelativeLayoutPanel;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import org.jaudiotagger.tag.FieldKey;

import main.Overview;

@SuppressWarnings("serial")
public class CheckBoxPanel extends RelativeLayoutPanel {
	
	MetaTypeCheckBox titel = new MetaTypeCheckBox("Titel", FieldKey.TITLE);
	MetaTypeCheckBox interpret = new MetaTypeCheckBox("Interpret", FieldKey.ARTIST);
	MetaTypeCheckBox album = new MetaTypeCheckBox("Album", FieldKey.ALBUM);
	MetaTypeCheckBox jahr = new MetaTypeCheckBox("Jahr", FieldKey.YEAR);
	MetaTypeCheckBox titelnummer = new MetaTypeCheckBox("Titelnummer", FieldKey.TRACK);
	MetaTypeCheckBox genre = new MetaTypeCheckBox("Genre", FieldKey.GENRE);
	// TODO: noch mehr Checkboxes
	
	private MetaTypeCheckBox[] allCheckBoxes;
	private Component[] listData;

	public CheckBoxPanel() {
		this.setVisible(true);

		initCheckBoxes();
		
		NiceCheckboxList complist = new NiceCheckboxList(listData);
		JScrollPane scrollpane = new JScrollPane(complist);
		this.add(scrollpane, 0.5f, 0.5f, true, 1, 1);

		Overview.setCheckboxPanel(this);
	}

	private void initCheckBoxes() {
		allCheckBoxes = new MetaTypeCheckBox[]{titel,interpret,album,jahr,titelnummer,genre};
		addCheckedListener();
		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		listData = new Component[]{titel, interpret, album, sep, genre, jahr, titelnummer};
	}

	private void addCheckedListener() {
		ItemListener listener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println("CheckBoxPanel: checkbox changed");
				Overview.getMetaView().setAvailableMetaTypes(getCheckedTypes());
			}
		};

		titel.addItemListener(listener);
		interpret.addItemListener(listener);
		album.addItemListener(listener);
		jahr.addItemListener(listener);
		titelnummer.addItemListener(listener);
		genre.addItemListener(listener);

	}
	
	private ArrayList<FieldKey> getCheckedTypes() {
		ArrayList<FieldKey> toReturn = new ArrayList<FieldKey>();
		for(MetaTypeCheckBox checkbox: allCheckBoxes){
			if(checkbox.isSelected()){
				toReturn.add(checkbox.getMetaType());
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
