package util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class MetadataIO {
	
	public static String getMetaValue(File file, FieldKey fieldKey){
		if(file == null){
			return "";
		}
		try {
			AudioFile audFile = AudioFileIO.read(file);
			String value = audFile.getTagOrCreateAndSetDefault().getFirst(fieldKey);
			return value;
		} catch (CannotReadException e) {
			return "";
		} catch (IOException e) {
			return "";
		} catch (TagException e) {
			return "";
		} catch (ReadOnlyFileException e) {
			return "";
		} catch (InvalidAudioFrameException e) {
			return "";
		}
	}
	
	
	public static boolean writeMetaValues(File file, Map<FieldKey, String> values){
		if(file == null){
			return false;
		}
		try {
			AudioFile audFile = AudioFileIO.read(file);
			Tag tag = audFile.getTagOrCreateAndSetDefault();
			Iterator<Entry<FieldKey, String>> mapEntries = values.entrySet().iterator();
			while(mapEntries.hasNext()){
				Entry<FieldKey, String> entry = mapEntries.next();
				FieldKey key = entry.getKey();
				String value = entry.getValue();
				
				if(tag.hasField(key)){
					String backupValue = tag.getFirst(key);
					tag.deleteField(key);
					try {
						tag.setField(key, value);
					} catch (FieldDataInvalidException e) {
						tag.setField(key, backupValue);
					}
				} else {
					try {
						tag.setField(key, value);
					} catch (FieldDataInvalidException e) {
					} catch (KeyNotFoundException e) {
					}
				}
			}
			
			audFile.commit();
			return true;
		} catch (CannotReadException e) {
			return false;
		} catch (IOException e) {
			return false;
		} catch (TagException e) {
			return false;
		} catch (ReadOnlyFileException e) {
			return false;
		} catch (InvalidAudioFrameException e) {
			return false;
		} catch (CannotWriteException e) {
			return false;
		}
	}
	
}
