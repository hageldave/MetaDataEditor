package gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;

import gui.util.RelativeLayoutPanel;

@SuppressWarnings("serial")
public class PlayerToolbar extends RelativeLayoutPanel {
	
	JButton play = new JButton("play");
	JSlider slider = new JSlider(JSlider.HORIZONTAL);
	JLabel label = new JLabel("test");
	
	public PlayerToolbar(){
		this.add(play, 		0, 		0.4f, 	false, 0.2f, 	0.6f);
		this.add(slider, 	0.2f, 	0.4f, 	false, 0.7f, 	0.6f);
		this.add(label, 	0, 		0, 		false, 1, 		0.4f);
		this.label.setVerticalAlignment(JLabel.CENTER);
		this.setOpaque(false);
	}

}
