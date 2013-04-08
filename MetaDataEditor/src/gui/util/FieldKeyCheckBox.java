package gui.util;

import javax.swing.JCheckBox;

import org.jaudiotagger.tag.FieldKey;

/**
 * JCheckbox die zusaetzlich noch einen {@link FieldKey} parameter besitzt.
 */
@SuppressWarnings("serial")
public class FieldKeyCheckBox extends JCheckBox {

	private FieldKey fieldKey;
	
	/**
	 * Konstruktor.
	 * @param title label text
	 * @param fieldkey der mit dieser checkbox assoziiert ist
	 */
	public FieldKeyCheckBox(String title, FieldKey fieldkey) {
		super(title);
		setFieldKey(fieldKey);
	}

	/**
	 * @return {@link FieldKey} dieser Checkbox
	 */
	public FieldKey getFieldKey() {
		return fieldKey;
	}

	/**
	 * Settet den {@link FieldKey} dieser Checkbox
	 * @param fieldKey
	 */
	public void setFieldKey(FieldKey fieldKey) {
		this.fieldKey = fieldKey;
	}
	
}
