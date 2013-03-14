package gui.util;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * JPanel that allows placing components relative to its size.
 * Use the <br>{@link #add(Component, float, float, boolean, float, float)} or<br>
 * {@link #setComponentPlacement(Component, float, float, boolean, float, float)}
 * method for a relative placement. On resizing, components with placement will
 * be rearranged. <p>
 * 
 * E.g.: <code>myPanel.add(myButton, 0.5f, 0.3f, true, 0.25f, 0.1f);</code><br>
 * this adds myButton to myPanel, placing it centered horizontally, in the upper 
 * half. myButton will be 1/4 as wide and 1/10 as high as myPanel.
 * 
 * @author David Haegele
 */
public class RelativeLayoutPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	/** holds key value pairs. (component, corresponding placement) */
	private Map<Component, Placement> placements = new HashMap<Component, Placement>();
	
	
	/** example */
	public static void main(String[] args) {
		JFrame myFrame = new JFrame("resize example");
		RelativeLayoutPanel myPanel = new RelativeLayoutPanel();
		myFrame.setContentPane(myPanel);
		myFrame.setPreferredSize(new Dimension(400, 300));
		myFrame.pack();
		
		//panel.add(	component		, 	x, 		y, 		center?, 	width, 	height)
		myPanel.add(new JButton("button"), 	0.5f, 	0.7f, 	true, 		0.3f, 	0.1f);
		myPanel.add(new JButton("sameSize"), 0.5f, 	0.4f, 	true, 		120, 	30);
		myPanel.add(new JButton("sameHeight"), 0.5f, 0.55f, true, 		0.3f, 	30);
		myPanel.add(new JButton("no change"), 60, 	15, 	true, 		100, 	30);
		myPanel.add(new JButton("samePosition"), 10, 30, 	false, 		0.3f, 	0.1f);
		myFrame.setVisible(true);
	}
	
	
	/** 
	 * Constructs a JPanel with absolute Layout and a ComponentListener
	 * that listens to resizing and rearranges the components 
	 */
	public RelativeLayoutPanel() {
		super();
		super.setLayout(null);
		this.addComponentListener(new ComponentAdapter(){
			
			@Override
			public void componentResized(ComponentEvent e) {
				// rearrange components when panel gets resized
				adaptToSize();
				validate();
			}
			
		});
	}
	
	
	/**
	 * DONT USE! 
	 * setting a LayoutManager is not allowed for this kind of panel
	 */
	public void setLayout(LayoutManager mgr) {
		// do nothing
		System.out.println
		("changing layout of RelativeLayoutPanel is not allowed.");
	}
	
	
	/** 
	 * Adds a component with specified placement (location and size) to this panel
	 * @param comp component to add
	 * @param relXPos @see {@link Placement#relXPos}
	 * @param relYPos @see {@link Placement#relYPos}
	 * @param center @see {@link Placement#center}
	 * @param relWidth @see {@link Placement#relWidth}
	 * @param relHeight @see {@link Placement#relHeight}
	 * @return added component
	 */
	public Component add(Component comp, float relXPos, float relYPos, 
			boolean center, float relWidth, float relHeight) {
		
		setCompPlacement(comp, relXPos, relYPos, center, relWidth, relHeight);
		return super.add(comp);
	}
	
	
	@Override
	public void remove(Component comp) {
		super.remove(comp);
		placements.remove(comp);
	}
	
	
	/** 
	 * Sets the Placement (location and size) of the specified component 
	 * on this panel.
	 * @param comp component the specified placement applies to
	 * @param relXPos @see {@link Placement#relXPos}
	 * @param relYPos @see {@link Placement#relYPos}
	 * @param center @see {@link Placement#center}
	 * @param relWidth @see {@link Placement#relWidth}
	 * @param relHeight @see {@link Placement#relHeight}
	 */
	protected void setCompPlacement(Component comp, float relXPos, float relYPos,
			boolean center, float relWidth, float relHeight){
		
		placements.put(comp, 
				new Placement(relXPos, relYPos, center, relWidth, relHeight));
		placeComponent(comp, 
				new Placement(relXPos, relYPos, center, relWidth, relHeight));
	}
	
	
	/**
	 * when panel gets resized, this rearranges all components that have
	 * a placement object mapped to it
	 */
	protected void adaptToSize() {
		Iterator<Entry<Component, Placement>> iterator = 
				placements.entrySet().iterator();
		Entry<Component, Placement> entry;
		while(iterator.hasNext()){
			 entry = iterator.next();
			 placeComponent(entry.getKey(), entry.getValue());
		}
	}
	
	
	/**
	 * Sets the center of the specified component to the specified coordinates.
	 * <br><b> no relative positioning! </b>
	 * @param comp to be positioned
	 * @param CX center x coordinate
	 * @param CY center y coordinate
	 */
	protected static final void setCenter(Component comp, float CX, float CY){
		comp.setLocation(	(int)(CX - comp.getWidth()/2.0),
							(int)(CY - comp.getHeight()/2.0));
	}
	
	
	/**
	 * Sets the position of the specified component relative to this
	 * panels size. <br>
	 * (position of upper left corner of the component)
	 * <br><br>
	 * If relativeX (or relativeY) is greater 1, then relativeX 
	 * (or relativeY) is interpreted as absolute (not relative) and comp 
	 * will be placed at relativeX (or relativeY).
	 * 
	 * @param comp to be positioned
	 * @param relativeX float in range 0..1. 
	 * 		is multiplied by panels width
	 * @param relativeY float in range 0..1. 
	 * 		is multiplied by panels height
	 */
	private final void setRelativePos(Component comp, 
			float relativeX, float relativeY) {
		
		int width = this.getWidth();
		int height = this.getHeight();
		
		comp.setLocation((int)(relativeX > 1 ? relativeX : (width * relativeX)), 
				(int)(relativeY > 1 ? relativeY : (height * relativeY)));
	}
	
	
	/**
	 * Sets the center of the specified component relative to this panels size.
	 * <br><br>
	 * If relativeCX (or relativeCY) is greater 1, then relativeCX 
	 * (or relativeCY) is interpreted as absolute (not relative) and comp 
	 * will be centered at relativeCX (or relativeCY).
	 * 
	 * @param comp to be positioned
	 * @param relativeCX center x float in range 0..1. 
	 * 		is multiplied by panels width
	 * @param relativeCY center y float in range 0..1. 
	 * 		is multiplied by panels height
	 */
	private final void setRelativeCenter(Component comp, 
			float relativeCX, float relativeCY){
		
		int width = this.getWidth();
		int height = this.getHeight();
		
		setCenter(comp, 
				relativeCX > 1 ? relativeCX : width * relativeCX, 
				relativeCY > 1 ? relativeCY : height * relativeCY);
	}
	
	
	/**
	 * Sets the specified components size relative to this panels
	 * size. <br><br>
	 * If relativeW (or relativeH) is greater 1, then relativeW 
	 * (or relativeH) is interpreted as absolute (not relative) and width 
	 * will be set to relativeW (or relativeH).
	 * @param comp to be sized
	 * @param relativeW width float in range 0..1. 
	 * 		is multiplied by panels width
	 * @param relativeH height float in range 0..1. 
	 * 		is multiplied by panels height
	 */
	private final void setRelativeSize(Component comp, 
			float relativeW, float relativeH){
		
		int width = this.getWidth();
		int height = this.getHeight();
		
		comp.setSize((int)(relativeW > 1 ? relativeW : (width * relativeW)), 
				(int)(relativeH > 1 ? relativeH : (height * relativeH)));
	}
	
	
	/**
	 * places the specified component using the specified placement object
	 * @param comp to be placed
	 * @param placement to be applied
	 */
	private final void placeComponent(Component comp, Placement placement){
		setRelativeSize(comp, placement.relWidth, placement.relHeight);
		if(placement.center){
			setRelativeCenter(comp, placement.relXPos, placement.relYPos);
		} else {
			setRelativePos(comp, placement.relXPos, placement.relYPos);
		}
	}
	
	
	/**
	 * Class for a placement object. This stores a components relative width 
	 * and height and the relative location of the component.
	 * The Placement object is used by the {@link RelativeLayoutPanel} to
	 * calculate the actual size and location of a component depending on the
	 * panels current size.
	 * <br><b>Important:</b><br>
	 * when using values > 1 those are interpreted as absolute, not relative.
	 * <br>
	 * E.g.: new Placement(0.5f, 0.5f, true, 0.3, 30) will place a
	 * component centered right in the middle of the panel, the width will be
	 * 1/3 of the panels width and the height will allways be 30.
	 * 
	 * @author David Haegele
	 */
	private static final class Placement {
		/** relative x position (value between 0.0 and 1.0 recommended) */
		final float relXPos; 
		
		/** relative y position (value between 0.0 and 1.0 recommended)*/
		final float relYPos; 
		
		/** 
		 * when true, position coordinates describe center position, 
		 * else upper left corner
		 */
		final boolean center;
		
		/** relative width (value between 0.0 and 1.0 recommended) */
		final float relWidth;
		
		/** relative height (value between 0.0 and 1.0 recommended) */
		final float relHeight;

		
		/**
		 * Constructor.
		 * @param relXPos relative x position (0.0 .. 1.0)
		 * @param relYPos relative y position (0.0 .. 1.0)
		 * @param center when true, position coordinates describe center position, 
		 * 		else upper left corner
		 * @param relWidth relative width (0.0 .. 1.0)
		 * @param relHeight relative height (0.0 .. 1.0)
		 */
		public Placement(Float relXPos, Float relYPos, boolean center, 
				Float relWidth, Float relHeight) {
			this.center = center;
			this.relHeight = relHeight;
			this.relWidth = relWidth;
			this.relXPos = relXPos;
			this.relYPos = relYPos;
		}
		
	}
	
}