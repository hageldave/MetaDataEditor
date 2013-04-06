package gui.util;

import java.awt.Component;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 * JList fuer Files.
 */
@SuppressWarnings("serial")
public class FileList extends JList<File> {
	
	File[] entries;
	
	public FileList() {
		super();
		setModel(new FileListModel(this));
		setCellRenderer(new FileCellRenderer());
	}
	
	@Override
	public void setListData(File[] listData) {
		this.entries = listData;
		super.setListData(listData);
	}
	
	private File[] getListData() {
		return entries;
	}
	
	
	/** CellRenderer der FileList */
	private static class FileCellRenderer extends JLabel implements ListCellRenderer<File> {
		
		{
			setOpaque(true);
		}

		@Override
		public Component getListCellRendererComponent(
				JList<? extends File> list, File file, int index, boolean isSelected,
				boolean cellHasFocus) {
			
			this.setText((file.getParentFile() == null ? file.getAbsolutePath() : file.getName()));
			if(isSelected){
				this.setBackground(list.getBackground().darker());
			} else {
				this.setBackground(list.getBackground());
			}
			return this;
		}
		
	}
	
	/** FileListModel der FileList */
	private static class FileListModel implements ListModel<File> {
		
		FileList list;
		
		public FileListModel(FileList list) {
			this.list = list;
		}
		
		@Override
		public void addListDataListener(ListDataListener l) {
		}

		@Override
		public File getElementAt(int index) {
			return list.getListData()[index];
		}

		@Override
		public int getSize() {
			return list.getListData() != null ? list.getListData().length : 0;
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
		}
		
	}
}