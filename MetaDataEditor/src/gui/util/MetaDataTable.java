package gui.util;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class MetaDataTable extends JTable {
	
	public MetaDataTable() {
		super(new Object[0][3], new Object[]{"Key", "Type", "Value"});
	}
	
	
	private static class MetaDataTableModel extends AbstractTableModel {

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
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
