package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class MetadataSuggestionizer {

	public static ArrayList<String> getSuggestions(File f){
		ArrayList<String> suggestions = new ArrayList<String>();
		
		// add already present Metadata
		Tag tag = null;
		try {
			AudioFile audFile = AudioFileIO.read(f);
			tag = audFile.getTagOrCreateDefault();
		} catch (CannotReadException | IOException | TagException
				| ReadOnlyFileException | InvalidAudioFrameException e) {
		}
		if(tag != null){
			for(FieldKey fieldkey: FieldKey.values()){
				try {
					String sugg = tag.getFirst(fieldkey);
					if(!sugg.isEmpty()){
						if(!suggestions.contains(sugg))
							suggestions.add(sugg);
					}
				} catch (KeyNotFoundException e){
				}
			}
		}
		
		// guess suggestions from Filename
		for(String sugg : guessSuggestions(f)){
			if(!suggestions.contains(sugg))
				suggestions.add(sugg);
		}
		// guess suggestions from containing folders name
		if(f.getParentFile() != null){
			for(String sugg : guessSuggestions(f.getParentFile())){
				if(!suggestions.contains(sugg))
					suggestions.add(sugg);
			}
		}
		
		return suggestions;
	}
	
	
	private static ArrayList<String> guessSuggestions(File f){
		ArrayList<String> suggestions = new ArrayList<String>();
		
		char[] specialChars = new char[]{'.',',','-','_','#','$','%','(',')','|','@','&','='};
		String name = f.getName();
		
		// remove fileextension
		int lastdot = name.lastIndexOf('.');
		if(lastdot != -1){
			name = name.substring(0, lastdot);
		}
		// replace specialChars with dots
		for(char c: specialChars){
			name = name.replace(c, ':');
		}
		// split String in parts
		for(String substring: name.split(":")){
			substring = substring.trim();
			if(!substring.isEmpty()){
				if(!suggestions.contains(substring))
					suggestions.add(substring);
			}
		}
		
		return suggestions;
	}
	
	
	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		for(String s : getSuggestions(new File("C:/Users/David/Music/Farin Urlaub/Endlich Urlaub/01 - Titel 1.mp3"))){
			System.out.println(s);
		}
		System.out.println(System.currentTimeMillis() - t);
	}
}
