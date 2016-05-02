package main.language;

import java.io.File;

	public enum LANGUAGES{
		GERMAN_DE(new File("Lang/de.lang")),
		ENGLISH_EN(new File("Lang/en.lang")),
		PIRATE_PI(new File("Lang/pi.lang"));
		
		private File file;
		private boolean notified;
		private LANGUAGES(File loc) {
			file = loc;
		}
		
		public File getFile(){
			if(!file.exists() && !notified){
				System.out.println("Couldn't find Language File: '" + file.getAbsolutePath() + "'");
				notified = true;
			}
			return file;
		}
	}