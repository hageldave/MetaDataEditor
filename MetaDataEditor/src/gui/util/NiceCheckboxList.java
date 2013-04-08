package gui.util;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;


@SuppressWarnings("serial")
public class NiceCheckboxList extends JList<Component> {
	
	public NiceCheckboxList(Component[] listData) {
		super(listData);
		setCellRenderer(new CompCellRenderer());
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = locationToIndex(e.getPoint());

	               if (index != -1) {
	                  Component comp = getModel().getElementAt(index);
	                  comp.dispatchEvent(e);
	                  if(comp instanceof JCheckBox){
	                	  JCheckBox checkbox = (JCheckBox) comp;
	                	  if(checkbox.isEnabled()){
	                		  checkbox.setSelected(!checkbox.isSelected());
	                	  }
	                  }
	                  repaint();
	               }
			}
		});
	}
	
	
	private static class CompCellRenderer implements ListCellRenderer<Component> {
		@Override
		public Component getListCellRendererComponent(
				JList<? extends Component> list, Component value, int index,
				boolean isSelected, boolean cellHasFocus) {
			
			return value;
		}
	}
		
}
