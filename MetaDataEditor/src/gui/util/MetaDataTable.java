package gui.util;

import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import main.Overview;
import main.test.TestFrame;

import org.jaudiotagger.tag.FieldKey;

import sun.swing.table.DefaultTableCellHeaderRenderer;
import util.MetadataIO;
import util.RenameKey;

@SuppressWarnings("serial")
public class MetaDataTable extends JTable {
	
	/** Rename Keys (e.g. $t )*/
	RenameKey[] firstColumn = new RenameKey[0];
	
	/** FieldKeys (metatypes) */
	FieldKey[] secondColumn = new FieldKey[0];
	
	/** Values (corresponding value to metatype) */
	String[] thirdColumn = new String[0];
	
	/** names for columns */
	String[] columnNames = new String[]{"Key", "Field", "Value"};
	
	/** file of which metavalues are displayed */
	File displayedFile;
	
	/** Custom CellEditor for the renameKeys in first column */
	RenameFieldCellEditor renameFieldCellEditor = new RenameFieldCellEditor();
	
	/** TableModel of this Table */
	MyTableModel tableModel;
	
	
	
	/** 
	 * Constructor. <br>
	 * Sets Model and Column Names
	 */
	public MetaDataTable() {
		super(new Object[0][3],new Object[]{"","",""});
		this.setModel(tableModel = new MyTableModel(this));
		this.getColumnModel().getColumn(0).setMaxWidth(50);
//		setupColumnAlignments();
	}
	
	
//	private void setupColumnAlignments(){
//		DefaultTableCellRenderer secondColRenderer = new DefaultTableCellRenderer();
//		DefaultTableCellHeaderRenderer secondHeaderRenderer = new DefaultTableCellHeaderRenderer();
//		DefaultTableCellRenderer thirdColRenderer = new DefaultTableCellRenderer();
//		DefaultTableCellHeaderRenderer thirdHeaderRenderer = new DefaultTableCellHeaderRenderer();
//		
//		secondColRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
//		secondHeaderRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
//		thirdColRenderer.setHorizontalAlignment(SwingConstants.LEFT);
//		thirdHeaderRenderer.setHorizontalAlignment(SwingConstants.LEFT);
//		
//		this.getColumnModel().getColumn(1).setCellRenderer(secondColRenderer);
//		this.getColumnModel().getColumn(2).setCellRenderer(thirdColRenderer);
//		this.getColumnModel().getColumn(1).setHeaderRenderer(secondHeaderRenderer);
//		this.getColumnModel().getColumn(2).setHeaderRenderer(thirdHeaderRenderer);
//	}
	
	
	/**
	 * Sets the File to be displayed by this Table.
	 * <p></p>
	 * Table gets updated to display the file immediately 
	 * @param file to be displayed
	 */
	public void setDisplayedFile(File file){
		this.displayedFile = file;
		fillValues();
	}
	
	
	/**
	 * Sets the available FieldKeys for display and editing.
	 * @param fieldkeys ArrayList of desired {@link FieldKey}s
	 */
	public void setFieldKeys(ArrayList<FieldKey> fieldkeys){
		FieldKey[] arr = new FieldKey[fieldkeys.size()];
		this.secondColumn = fieldkeys.toArray(arr);
		fillValues();
		fitRenameKeysToCurrentTable();
		tableModel.fireTableDataChanged();
	}
	
	
	/**
	 * fills the third column with the right values corresponding to
	 * the FieldKeys in the second column
	 */
	private void fillValues(){
		this.thirdColumn = new String[secondColumn.length];
		for(int i = 0; i < secondColumn.length; i++){
			this.thirdColumn[i] = MetadataIO.getMetaValue(displayedFile, secondColumn[i]);
		}
		redrawColumn(2);
	}
	
	
	/**
	 * fills the first column with the right RenameKeys corresponding
	 * to the FieldKeys in the second column.
	 * <p></p>
	 * those Key pairs can be obtained by Overview.getRenameKeyMapping()
	 */
	private void fitRenameKeysToCurrentTable(){
		this.firstColumn = new RenameKey[secondColumn.length];
		for(int i = 0; i < secondColumn.length; i++){
			if(Overview.getRenameKeyMapping().containsValue(secondColumn[i])){
				this.firstColumn[i] = Overview.getRenameKeyMapping().reverseGet(secondColumn[i]);
			}
		}
		redrawColumn(0);
	}
	
	
	/** redraws the specified Column */
	private void redrawColumn(int col){
		for(int i = 0; i < secondColumn.length; i++){
			tableModel.fireTableCellUpdated(i, col);
		}
	}
	
	
	@Override
	public TableCellEditor getCellEditor(int row, int col) {
		if(col == 0){ // first column has special editor
			return this.renameFieldCellEditor;
		}
		return super.getCellEditor(row, col);
	}
	
	
	/**
	 * TableModel for {@link MetaDataTable}
	 * @author David Haegele
	 */
	private static class MyTableModel extends AbstractTableModel {
	    /** Table that owns this Model */
		MetaDataTable owner;
		
		/**
		 * Constructor.
		 * <p></p>
		 * e.g.: <code>theTable.setModel(new MyTableModel(theTable);</code>
		 * @param owner
		 */
		public MyTableModel(MetaDataTable owner) {
			this.owner = owner;
		}
		
		@Override
		public String getColumnName(int col) {
	        return owner.columnNames[col];
	    }
		
		@Override
	    public int getRowCount() { return owner.secondColumn.length; }
	    
		@Override
	    public int getColumnCount() { return 3; }
	    
		@Override
	    public Object getValueAt(int row, int col) {
	    	switch (col) {
			case 0: 
				return owner.firstColumn[row];
			case 1:
				return owner.secondColumn[row];
			case 2:
				return owner.thirdColumn[row];
			default:
				return null;
			}
	    }
	    
		@Override
	    public boolean isCellEditable(int row, int col) { 
			if(col == 1){
				// second column is not editable
				return false;
			} else if(col == 2){
				// when no file displayed -> not editable
				return owner.displayedFile != null; 
			} else {
				return true;
			}
		}
	    
		@Override
	    public void setValueAt(Object value, int row, int col) {
			switch (col) {
			case 0: // changed RenameKey mapping
				owner.firstColumn[row] = (RenameKey) value;
				if(value != null){
					Overview.getRenameKeyMapping().put(owner.firstColumn[row], owner.secondColumn[row]);
					owner.fitRenameKeysToCurrentTable();
				} else {
					Overview.getRenameKeyMapping().reverseRemove(owner.secondColumn[row]);
				}
				break;
			case 1: // changed FieldKey (never happens)
				owner.secondColumn[row] = (FieldKey) value;
				break;
			case 2: // changed value corresponding to fieldKey in that row
				owner.thirdColumn[row] = (String) value;
				break;
			default:
				break;
			}
	        fireTableCellUpdated(row, col);
	    }
	}
	
	
	/**
	 * CellEditor for the first column of {@link MetaDataTable}.
	 * <p></p>
	 * is a JCombobox containing possible values 
	 * @author David Hägele
	 */
	private static class RenameFieldCellEditor extends DefaultCellEditor {
		
		static JComboBox<RenameKey> availableRenameKeys = new JComboBox<RenameKey>(initAvailableRenameKeys());
		
		public RenameFieldCellEditor() {
			super(availableRenameKeys);
		}
		
		/** @return an Array of all RenameKeys plus null */ 
		private static RenameKey[] initAvailableRenameKeys(){
			RenameKey[] toReturn = new RenameKey[RenameKey.values().length +1];
			RenameKey[] keys = RenameKey.values();
			toReturn[0] = null;
			for(int i = 0; i < keys.length ; i++){
				toReturn[i+1] = keys[i];
			}
			return toReturn;
		}
		
	}

	
	public static void main(String[] args) {
		// testing area
		TestFrame frame = new TestFrame();
		MetaDataTable table = new MetaDataTable();
		ArrayList<FieldKey> fieldkeys = new ArrayList<>();
		fieldkeys.add(FieldKey.ARTIST);
		fieldkeys.add(FieldKey.MOOD);
		table.displayedFile = new File("testfile/test.mp3");
		table.setFieldKeys(fieldkeys);
		
		JScrollPane pane = new JScrollPane(table);
		frame.add(pane);
		frame.setVisible(true);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fieldkeys.add(FieldKey.TITLE);
		table.setFieldKeys(fieldkeys);
		System.out.println("done");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.setDisplayedFile(null);
	}

}
