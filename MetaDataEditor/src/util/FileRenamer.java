package util;

import java.io.File;

public class FileRenamer {
	
	public static boolean rename(File file, String nameFormat) {
		String fileextension = "";
		try {
			fileextension = file.getName().substring(file.getName().lastIndexOf('.'));
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		String newFilename = getFormattedString(file, nameFormat) + fileextension;
		if(file.getParentFile() == null) {
			return false;
		}
		File renamedFile = new File(file.getParentFile(), newFilename);
		return file.renameTo(renamedFile);
	}
	
	
	private static String getFormattedString(File f, String nameFormat) {
		String name = "";
		String[] parts = nameFormat.split("\\$");
		name = parts[0];
		for(int i = 1; i < parts.length; i ++){
			if(parts[i].length() == 0){
				name += "$";
			} else {
				name += getStringForKey(f, parts[i].charAt(0));
				if(parts[i].length() > 1){
					name += parts[i].substring(1);
				}
			}
		}
		return name;
	}
	
	private static String getStringForKey(File f, char keyChar) {
		// TODO Auto-generated method stub
		return keyChar + "---";
	}

	public static void main(String[] args) {
		File f = null;
		File f2 = new File(f, "fds");
		System.out.println();
	}
}
