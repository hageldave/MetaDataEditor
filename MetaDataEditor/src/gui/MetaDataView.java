package gui;

import gui.util.MetaDataTable;
import gui.util.RelativeLayoutPanel;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jaudiotagger.tag.FieldKey;

import main.Overview;

@SuppressWarnings("serial")
public class MetaDataView extends RelativeLayoutPanel {
	
	MetaDataTable metaTable;
	
	JScrollPane scrollpane;
	
	public MetaDataView(){
		initTable();
		scrollpane = new JScrollPane(metaTable);
		this.add(scrollpane, 0.5f, 0.5f, true, 1, 1);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		Overview.setMetaView(this);
	}
	
	private void initTable() {
		metaTable = new MetaDataTable();
	}
	
	
	public void setAvailableMetaTypes(ArrayList<FieldKey> metaTypes){
		System.out.println("MetaDataView: changing metafields");
		metaTable.setFieldKeys(metaTypes);
	}
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.add(new MetaDataView());
		frame.setSize(100, 100);
	}
	
	

}
