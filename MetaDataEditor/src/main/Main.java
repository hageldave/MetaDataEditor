package main;

import gui.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jaudiotagger.tag.FieldKey;

public class Main {

	/**
	 * @param args
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) {
//		lookandfeel: do {
//		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//		} catch (ClassNotFoundException | InstantiationException
//				| IllegalAccessException | UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//			continue lookandfeel;
//		}
//		} while (false);
		for(FieldKey k : FieldKey.values()){
			System.out.println(k);
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new MainFrame();				
			}
		});

	}

}
