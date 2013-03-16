package gui;

import gui.util.MetaTypeCheckBox;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jaudiotagger.tag.FieldKey;

import main.Overview;

public class CheckBoxPanel extends JPanel {

	MetaTypeCheckBox titel;
	MetaTypeCheckBox interpret;
	MetaTypeCheckBox album;
	MetaTypeCheckBox jahr;
	MetaTypeCheckBox titelnummer;
	MetaTypeCheckBox genre;
	
	private MetaTypeCheckBox[] allCheckBoxes;

	public CheckBoxPanel() {
		this.setVisible(true);
		this.setLayout(new GridLayout(6, 1));

		initCheckBoxes();
		addCheckedListener();

		
		this.add(titel);
		this.add(interpret);
		this.add(album);
		this.add(genre);
		this.add(jahr);
		this.add(titelnummer);

		Overview.setCheckboxPanel(this);
	}

	private void initCheckBoxes() {
		titel = new MetaTypeCheckBox("Titel", FieldKey.TITLE);
		interpret = new MetaTypeCheckBox("Interpret", FieldKey.ARTIST);
		album = new MetaTypeCheckBox("Album", FieldKey.ALBUM);
		jahr = new MetaTypeCheckBox("Jahr", FieldKey.YEAR);
		titelnummer = new MetaTypeCheckBox("Titelnummer", FieldKey.TRACK);
		genre = new MetaTypeCheckBox("Genre", FieldKey.GENRE);
		
		allCheckBoxes = new MetaTypeCheckBox[]{titel,interpret,album,jahr,titelnummer,genre};
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
	

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new CheckBoxPanel());
		frame.setSize(400, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
