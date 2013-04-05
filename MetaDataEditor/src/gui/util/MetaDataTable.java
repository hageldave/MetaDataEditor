package gui.util;

import java.awt.SecondaryLoop;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.jaudiotagger.tag.FieldKey;

public class MetaDataTable extends JTable {
	
	/** Keys */
	char[] firstColumn;
	
	/** FieldKeys (metatypes) */
	FieldKey[] secondColumn;
	
	/** Values (corresponding value to metatype) */
	String[] thirdColumn;
	
	
	public MetaDataTable() {
		super(new Object[0][3], new Object[]{"Key", "Type", "Value"});
	}
	
	
	
	
	
	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0: 
			return firstColumn[row];
		case 1:
			return secondColumn[row];
		case 2:
			return thirdColumn[row];
		default:
			return super.getValueAt(row, column);
		}
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		switch (column) {
		case 0: 
			firstColumn[row] = (Character) aValue;
			break;
		case 1:
			secondColumn[row] = (FieldKey) aValue;
			break;
		case 2:
			thirdColumn[row] = (String) aValue;
			break;
		default:
			break;
		}
		super.setValueAt(aValue, row, column);
	}





	private static class MetaDataTableModel extends AbstractTableModel {

		MetaDataTable owner;
		
		public MetaDataTableModel(MetaDataTable owner){
			this.owner = owner;
		}
		
		@Override
		public int getColumnCount() {
			return owner.secondColumn.length;
		}

		@Override
		public int getRowCount() {
			return 3;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return owner.firstColumn[rowIndex];
			case 1:
				return owner.secondColumn[rowIndex];
			case 2:
				return owner.thirdColumn[rowIndex];
			default:
				return null;
			}
		}
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if(columnIndex == 1) {
				return false;
			}
			return super.isCellEditable(rowIndex, columnIndex);
		}
		
	}
	
	private static class MetaDataTableCellEditor extends AbstractCellEditor {

		@Override
		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}
