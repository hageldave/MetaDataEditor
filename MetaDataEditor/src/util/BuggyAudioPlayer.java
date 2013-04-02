package util;

import java.io.File;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

/** 
 * Class implementing an audio player providing simplified methods
 * for playing audio files. <br>
 * 
 * @author David Haegele
 *
 */
public class BuggyAudioPlayer {

	private File audioFile;
	
	private BasicPlayer player;
	
	private BasicController control;
	
	private volatile long currentPosition = 0;
	
	private long filelength = 0;
	
	/** Constructor */
	public BuggyAudioPlayer() {
		this.player = new BasicPlayer();
		this.player.setSleepTime(0);
		this.control = (BasicController)player;
		player.addBasicPlayerListener(new BasicPlayerListener() {
			
			@Override
			public void stateUpdated(BasicPlayerEvent event) {
			}
			
			@Override
			public void setController(BasicController controller) {
			}
			
			@Override
			public void progress(int bytesread, long microseconds, byte[] pcmdata,
					Map properties) {
				currentPosition = bytesread;
//				System.out.println(bytesread);
			}
			
			@Override
			public void opened(Object stream, Map properties) {
			}
		});
	}
	
	/**
	 * Opens specified audio file
	 * @param audioFile to be opened
	 * @throws BasicPlayerException when file could not be found not 
	 * 		be read or is not an audio file
	 */
	public void openAudioFile(File audioFile) throws BasicPlayerException{
		this.audioFile = audioFile;
		filelength = audioFile.length();
		stop();
		control.open(audioFile);
	}
	
	/**
	 * Plays opened audio file
	 */
	public void play() {
		if(this.audioFile == null){
			return; // nothing to be done without file
		}
		try {
			if (player.getStatus() == BasicPlayer.PAUSED) {
				control.resume();
			} else {
				control.play();
			}
		} catch (BasicPlayerException e) {
		}

	}
	
	/**
	 * stops currently playing audio. <br>
	 * ( use reset() before replay )
	 */
	public void stop(){
		Thread currentthread = player.getCurrentThread();
		try {
			control.stop();
		} catch (BasicPlayerException e) {
		}
		try {
			if(currentthread != null){
				currentthread.join(1000);
			}
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * Resets currently opened audio. <br>
	 * (stops if not stopped and reopens file)
	 */
	public void reset() {
		if(player.getStatus() != BasicPlayer.STOPPED){
			stop();
		}
		try {
			player.open(audioFile);
		} catch (BasicPlayerException e) {
		}
		currentPosition = 0;
	}
	
	/**
	 * pauses currently playing audio
	 */
	public void pause(){
		try {
			control.pause();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * sets the Position of the currently streaming audio. <br>
	 * (values between 0 and 100)
	 * @param pos desired position (0 - 100)
	 */
	public void setStreamPosition(long pos){
		if(audioFile == null || pos > 100 || pos < 0){
			return; // do nothing
		}
		long newPosition = (long)((pos/100.0)*filelength);
		boolean wasPlaying = player.getStatus() == BasicPlayer.PLAYING;
		try {
			System.out.println("stopping");
			stop();
			control.open(audioFile);
			currentPosition = control.seek(newPosition);
			System.out.println("newpos= " + newPosition);
			if(wasPlaying){
				control.play();
			}
		} catch (BasicPlayerException e) {
		}

	}
	
}
