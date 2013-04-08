package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import javazoom.jlgui.basicplayer.BasicPlayerException;

import org.jaudiotagger.tag.FieldKey;

import util.BuggyAudioPlayer;
import util.BuggyAudioPlayer.BuggyPlayerProgressListener;
import util.MetadataIO;




public class test {

	public static void main(String[] args) throws FileNotFoundException,
			InterruptedException {
		// file einlesen
		File mp3 = new File("testfile/test.mp3"); // <- muss ggf in ordner testfile getan werden
		
		// metadata interpret auslesen
		System.out.println(MetadataIO.getMetaValue(mp3, FieldKey.TITLE));
		
		// player bauen
		BuggyAudioPlayer player = new BuggyAudioPlayer();
		player.addProgressListener(new BuggyPlayerProgressListener() {
			
			@Override
			public void madeProgress(int percent) {
				System.out.println("PROGRESS: " + percent + "%");
				
			}
		});
		
		// frame mit slider
		// -----------------------
		TestFrame frame = new TestFrame();
		final JSlider slider = new JSlider(SwingConstants.HORIZONTAL);
		slider.setMinimum(0);
		slider.setMaximum(100);
		frame.add(slider);
		player.addProgressListener(new BuggyPlayerProgressListener() {
			
			@Override
			public void madeProgress(int percent) {
				slider.setValue(percent);
			}
		});
		frame.setVisible(true);
		// -----------------------
		
		System.out.println("open");
		try {
			player.openAudioFile(mp3);
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
		System.out.println("play - playing 10seconds");
		player.play();
		Thread.sleep(10000);
		System.out.println("pause - pausing 1second");
		player.pause();
		Thread.sleep(1000);
		System.out.println("resume - playing 1second");
		player.play();
		Thread.sleep(1000);
		System.out.println("skip to 50 - playing 1second");
		player.setStreamPosition(50);
		Thread.sleep(1000);
		System.out.println("skip to 30 - playing 1second");
		player.setStreamPosition(30);
		Thread.sleep(1000);
		System.out.println("skip to 100 - (end of file) waiting 1second anyway");
		player.setStreamPosition(100);
		Thread.sleep(1000);
		System.out.println("reset/skip to 70");
		player.reset();
		player.setStreamPosition(70);
		System.out.println("play - playing 2seconds");
		player.play();
		Thread.sleep(2000);
		System.out.println("stop");
		player.stop();
		
		
		// metadaten 'mood' schreiben
		Map<FieldKey, String> metavalues = new HashMap<>();
		metavalues.put(FieldKey.TITLE, "I want you so hard");
		MetadataIO.writeMetaValues(mp3, metavalues);
		System.out.println(MetadataIO.getMetaValue(mp3, FieldKey.MOOD));
		
		frame.dispose();
	}
	
	
	@SuppressWarnings("serial")
	public static class TestFrame extends JFrame {
		
		public TestFrame() {
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setSize(400, 400);
		}
	}
}
