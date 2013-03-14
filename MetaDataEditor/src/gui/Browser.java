package gui;

import gui.util.FileTreePanel;
import gui.util.RelativeLayoutPanel;
import gui.util.FileTreePanel.FileTreeNode;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import main.Overview;

/**
 * Klasse fuer den FileBrowser
 */
public class Browser extends RelativeLayoutPanel {
	
	private static final long serialVersionUID = 1L;
	
	/** JList die Musikdateien anzeigt */
	FileList musicFileList;
	
	public Browser() {
		loadFileView();
		Overview.setBrowser(this);
	}
	
	
	private void buildRefreshBttn(){
		JButton refreshButton = new JButton(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reloadFileView();
			}
		});
		refreshButton.setText("refresh");
		this.add(refreshButton, 0f, 0f, false, 0.5f, 0.1f);
	}
	
	
	/**
	 * startet das Laden des FileTreePanels.
	 * dazu wird ein seperater thread erstellt der das uebernimmt,
	 * da dies evtl viel Zeit in anspruch nimmt.
	 */
	private void loadFileView() {
		final JLabel loadingLabel = new JLabel("loading Filesystem...", JLabel.CENTER);
		this.add(loadingLabel, 0.5f,0.5f,true,0.8f,0.8f);
		buildRefreshBttn();
		Thread loader = new Thread(new Runnable() {
			
			@Override
			public void run() {
				musicFileList = new FileList();
				final FileTreePanel folderPane = new FileTreePanel();
				final JScrollPane filePane = new JScrollPane(musicFileList);
				
				folderPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				filePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				
				folderPane.setTreeSelectionListener(new TreeSelectionListener() {
					
					@Override
					public void valueChanged(TreeSelectionEvent e) {
						FileTreeNode node = (FileTreeNode)
								folderPane.getTree().getLastSelectedPathComponent();
						if(node != null){
							System.out.println(node.getFile());
							if(node.getFile().exists()){
								musicFileList.setListData(node.getFile().listFiles());
							} else {
								folderPane.reloadTree();
							}
						}
						
					}
				});
				
				remove(loadingLabel);
				add(folderPane, 0f, 0.1f, false, 0.5f, 0.9f);
				add(filePane, 0.5f, 0f, false, 0.5f, 1);
				Container container = getParent();
				while(container.getParent() != null){
					container = container.getParent();
				}
				JFrame frame = (JFrame) container;
				int tempState = frame.getExtendedState();
				frame.setExtendedState(JFrame.ICONIFIED);
				frame.setExtendedState(tempState);
			}
		});
		
		loader.start();
	}
	
	
	private void reloadFileView(){
		for(Component comp :this.getComponents()){
			this.remove(comp);
		}
		loadFileView();
	}
	
	
	public static void main(String[] args) {
		JFrame myframe = new JFrame();
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myframe.add(new Browser());
		myframe.setSize(500, 400);
		myframe.setVisible(true);
	}
	
	
	
	private static class FileList extends JList<File> {
		
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
	
	
}
