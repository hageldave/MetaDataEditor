package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;

import util.MetadataIO;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class test {

	public static void main(String[] args) throws FileNotFoundException,
			InterruptedException, JavaLayerException {
		// player bauen
		File mp3 = new File("testfile/test.mp3");
		FileInputStream mp3stream = new FileInputStream(mp3);
		final AdvancedPlayer myplayer = new AdvancedPlayer(mp3stream);

		// thread bauen der myplayer abspielt
		Runnable playerRunnable = new Runnable() {

			@Override
			public void run() {
				try {
					myplayer.play();
				} catch(JavaLayerException e) {
					e.printStackTrace();
				}
				System.out.println("terminated");
			}
		};
		Thread t = new Thread(playerRunnable);

		// thread starten
		t.start();

		// 2sec warten
		Thread.sleep(2000);

		// anzahl der bytes der mp3datei holen
		long numOfBytes = mp3.length();

		// einige bytes weiter springen
		try {
			mp3stream.skip(numOfBytes / 2);
		} catch(IOException e) {
			e.printStackTrace();
		}

		// 2sec warten
		Thread.sleep(2000);

		// einige bytes zurueck springen
		try {
			mp3stream.skip(-numOfBytes / 2);
		} catch(IOException e) {
			e.printStackTrace();
		}

		// 2sec warten
		Thread.sleep(2000);
		
		// soviele bytes weiter springen dass das lied zu ende ist
		try {
			mp3stream.skip(numOfBytes);
			myplayer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		// metadata interpret auslesen
		System.out.println(MetadataIO.getMetaValue(mp3, FieldKey.ARTIST));
		Map<FieldKey, String> metavalues = new HashMap<>();
		metavalues.put(FieldKey.MOOD, "awesome");
		MetadataIO.writeMetaValues(mp3, metavalues);
		System.out.println(MetadataIO.getMetaValue(mp3, FieldKey.MOOD));
		

	}
}
