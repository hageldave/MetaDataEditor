package gui.util;

import javax.swing.JCheckBox;

import modell.MetaType;

/**
 * JCheckbox die zusätzlich noch einen MetaType parameter besitzt.
 */
public class MetaTypeCheckBox extends JCheckBox {

	private MetaType metaType;
	
	/**
	 * Konstruktor.
	 * @param title label text
	 * @param metaType der mit dieser checkbox assoziiert ist
	 */
	public MetaTypeCheckBox(String title, MetaType metaType) {
		super(title);
		setMetaType(metaType);
	}

	
	public MetaType getMetaType() {
		return metaType;
	}

	public void setMetaType(MetaType metaType) {
		this.metaType = metaType;
	}
	
}
