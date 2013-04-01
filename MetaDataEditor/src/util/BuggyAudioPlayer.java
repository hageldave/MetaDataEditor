package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import static javazoom.jlgui.basicplayer.BasicPlayer.*;

public class BuggyAudioPlayer {

	File audioFile;
	
	BasicPlayer player;
	
	BasicController control;
	
	boolean isPaused;
	
	boolean isPlaying;
	
	volatile long currentPosition = 0;
	
	long filelength = 0;
	
	public BuggyAudioPlayer() {
		this.player = new BasicPlayer();
		this.control = (BasicController)player;
		player.addBasicPlayerListener(new BasicPlayerListener() {
			
			@Override
			public void stateUpdated(BasicPlayerEvent event) {
				System.out.println(event.toString());
				switch (event.toString().split(":")[0]) {
				case "PAUSED":
					isPaused = true;
					isPlaying = false;
					break;
				case "PLAYING":
					isPaused = false;
					isPlaying = true;
					break;
				case "STOPPED":
					isPaused = false;
					isPlaying = false;
					break;
				case "OPENED":
					isPaused = false;
					isPlaying = false;
					break;
				case "OPENING":
					isPaused = false;
					isPlaying = false;
					break;

				default:
					break;
				}
				
			}
			
			@Override
			public void setController(BasicController controller) {
			}
			
			@Override
			public void progress(int bytesread, long microseconds, byte[] pcmdata,
					Map properties) {
				currentPosition += bytesread;
				
			}
			
			@Override
			public void opened(Object stream, Map properties) {
			}
		});
	}
	
	public void openAudioFile(File audioFile) throws BasicPlayerException, FileNotFoundException{
		this.audioFile = audioFile;
		filelength = audioFile.length();
		control.open(audioFile);
	}
	
	public void play() {
		try {
			if (isPaused) {
				control.resume();
			} else {
				control.play();
			}
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void stop(){
		try {
			control.stop();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pause(){
		try {
			control.pause();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void setStreamPosition(long pos){
		long newPosition = (long)((pos/100.0)*filelength);
		System.out.println("newpos= " + newPosition);
		System.out.println("curpos= " + currentPosition);
		boolean wasPlaying = isPlaying;
		try {
//			control.stop();
//			control.open(audioFile);
			currentPosition += control.seek(newPosition-currentPosition);
//			if(wasPlaying){
//				control.play();
//			}
		} catch (BasicPlayerException e) {
			
		}

	}
	
}
