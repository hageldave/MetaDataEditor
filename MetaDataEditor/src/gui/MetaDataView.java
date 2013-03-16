package gui;

import gui.util.RelativeLayoutPanel;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jaudiotagger.tag.FieldKey;

import main.Overview;

public class MetaDataView extends RelativeLayoutPanel {
	
	JTable metaTable;
	
	JScrollPane scrollpane;
	
	public MetaDataView(){
		initTable();
		scrollpane = new JScrollPane(metaTable);
		this.add(scrollpane, 0.5f, 0.5f, true, 1, 1);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		Overview.setMetaView(this);
	}
	
	private void initTable() {
		metaTable = new JTable(new Object[99][2], new Object[]{"Type","Value"});
	}
	
	
	public void setAvailableMetaTypes(ArrayList<FieldKey> metaTypes){
		System.out.println("MetaDataView: changing metafields");
		//TODO: gewuenschte metatypefelder zur verf�gung stellen
	}
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.add(new MetaDataView());
		frame.setSize(100, 100);
	}
	
	

}
