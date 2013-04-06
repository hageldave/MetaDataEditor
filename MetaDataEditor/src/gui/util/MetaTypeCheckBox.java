package gui.util;

import javax.swing.JCheckBox;

import org.jaudiotagger.tag.FieldKey;

/**
 * JCheckbox die zusaetzlich noch einen MetaType parameter besitzt.
 */
@SuppressWarnings("serial")
public class MetaTypeCheckBox extends JCheckBox {

	private FieldKey metaType;
	
	/**
	 * Konstruktor.
	 * @param title label text
	 * @param metaType der mit dieser checkbox assoziiert ist
	 */
	public MetaTypeCheckBox(String title, FieldKey metaType) {
		super(title);
		setMetaType(metaType);
	}

	
	public FieldKey getMetaType() {
		return metaType;
	}

	public void setMetaType(FieldKey metaType) {
		this.metaType = metaType;
	}
	
}
