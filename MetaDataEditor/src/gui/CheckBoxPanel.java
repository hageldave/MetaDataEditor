package gui;

import gui.util.MetaTypeCheckBox;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Overview;
import model.MetaType;

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
		titel = new MetaTypeCheckBox("Titel", MetaType.title);
		interpret = new MetaTypeCheckBox("Interpret", MetaType.interpret);
		album = new MetaTypeCheckBox("Album", MetaType.album);
		jahr = new MetaTypeCheckBox("Jahr", MetaType.year);
		titelnummer = new MetaTypeCheckBox("Titelnummer", MetaType.number);
		genre = new MetaTypeCheckBox("Genre", MetaType.genre);
		
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
	
	private ArrayList<MetaType> getCheckedTypes() {
		ArrayList<MetaType> toReturn = new ArrayList<MetaType>();
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
