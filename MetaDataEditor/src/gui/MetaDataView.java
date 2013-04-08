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
	
	// TODO: mehrere Tables fuer mehrere Dateien gleichzeitig. evtl 
	// wechseln des angezeigten Tables mit buttons.
	// TODO: Label fuer Dateinamen der dargestellten Datei
	// TODO: Button fuer uebernehmen der geaenderten Metadaten
	
	MetaDataTable metaTable;
	
	JScrollPane scrollpane;
	
	public MetaDataView(){
		Overview.setMetaView(this);
		
		initTable();
		scrollpane = new JScrollPane(metaTable);
		this.add(scrollpane, 0.5f, 0.5f, true, 1, 1);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}
	
	private void initTable() {
		metaTable = new MetaDataTable();
	}
	
	
	public void setAvailableFieldKeys(ArrayList<FieldKey> fieldkeys){
		System.out.println("MetaDataView: changing metafields");
		metaTable.setFieldKeys(fieldkeys);
	}
	
	
//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		frame.setVisible(true);
//		frame.add(new MetaDataView());
//		frame.setSize(100, 100);
//	}
	
}
