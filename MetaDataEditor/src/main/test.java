package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JSlider;

import javazoom.jlgui.basicplayer.BasicPlayerException;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;

import util.BuggyAudioPlayer;
import util.BuggyAudioPlayer.BuggyPlayerProgressListener;
import util.MetadataIO;




public class test {

	public static void main(String[] args) throws FileNotFoundException,
			InterruptedException {
		// file einlesen
		File mp3 = new File("testfile/test.mp3"); // <- muss ggf in ordner testfile getan werden
		
		// metadata interpret auslesen
		System.out.println(MetadataIO.getMetaValue(mp3, FieldKey.ARTIST));
		
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
		JFrame frame = new JFrame();
		frame.setSize(400, 200);
		final JSlider slider = new JSlider(JSlider.HORIZONTAL);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("play");
		player.play();
		Thread.sleep(10000);
		System.out.println("pause");
		player.pause();
		Thread.sleep(1000);
		System.out.println("resume");
		player.play();
		Thread.sleep(1000);
		System.out.println("skip");
		player.setStreamPosition(50);
		Thread.sleep(1000);
		System.out.println("skip");
		player.setStreamPosition(30);
		Thread.sleep(1000);
		System.out.println("skip");
		player.setStreamPosition(100);
		Thread.sleep(3000);
		System.out.println("stop");
		player.stop();
		
		
		// metadaten 'mood' schreiben
		Map<FieldKey, String> metavalues = new HashMap<>();
		metavalues.put(FieldKey.MOOD, "awesome");
		MetadataIO.writeMetaValues(mp3, metavalues);
		System.out.println(MetadataIO.getMetaValue(mp3, FieldKey.MOOD));
		
		frame.dispose();
	}
}
